package ch.aaap.assignment;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.CantonImpl;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.DistrictImpl;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.ModelImpl;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PoliticalCommunityImpl;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.PostalCommunityImpl;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class Application {

  private Model model = null;

  public Application() {
    initModel();
  }

  public static void main(String[] args) {
    new Application();
  }

  /**
   * Reads the CSVs and initializes a in memory model
   */
  private void initModel() {
    var politicalCommunities = CSVUtil.getPoliticalCommunities();
    var postalCommunities = CSVUtil.getPostalCommunities();

    var postalCommunitiesByPoliticalCommunity = postalCommunities
        .stream()
        .collect(groupingBy(CSVPostalCommunity::getPoliticalCommunityNumber))
        .entrySet()
        .stream()
        .collect(toMap(
            Entry::getKey,
            e -> e.getValue()
                .stream()
                .map(c -> PostalCommunityImpl.builder()
                    .zipCode(c.getZipCode())
                    .zipCodeAddition(c.getZipCodeAddition())
                    .name(c.getName())
                    .build())
                .map(PostalCommunity.class::cast)
                .collect(toSet())));

    var cantonsByCode = new HashMap<String, Canton>();
    var districtsByNumber = new HashMap<String, District>();
    for (CSVPoliticalCommunity csvPoliticalCommunity : politicalCommunities) {
      var matchingPostalCommunities = postalCommunitiesByPoliticalCommunity
          .getOrDefault(csvPoliticalCommunity.getNumber(), Collections.emptySet());

      var politicalCommunity = PoliticalCommunityImpl.builder()
          .name(csvPoliticalCommunity.getName())
          .lastUpdate(csvPoliticalCommunity.getLastUpdate())
          .number(csvPoliticalCommunity.getNumber())
          .shortName(csvPoliticalCommunity.getShortName())
          .postalCommunities(matchingPostalCommunities)
          .build();

      var district = districtsByNumber.getOrDefault(
          csvPoliticalCommunity.getDistrictNumber(),
          DistrictImpl.builder()
              .number(csvPoliticalCommunity.getDistrictNumber())
              .name(csvPoliticalCommunity.getDistrictName())
              .politicalCommunities(new HashSet<>())
              .build());
      district.getPoliticalCommunities().add(politicalCommunity);
      districtsByNumber.putIfAbsent(district.getNumber(), district);

      var canton = cantonsByCode.getOrDefault(
          csvPoliticalCommunity.getCantonCode(),
          CantonImpl.builder()
              .code(csvPoliticalCommunity.getCantonCode())
              .name(csvPoliticalCommunity.getCantonName())
              .districts(new HashSet<>())
              .build());
      canton.getDistricts().add(district);
      cantonsByCode.putIfAbsent(canton.getCode(), canton);
    }

    this.model = new ModelImpl(cantonsByCode.values());
  }

  /**
   * @return model
   */
  public Model getModel() {
    return model;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   * @throws IllegalArgumentException on unknown canton code
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    return model.getCantons()
        .stream()
        .filter(c -> c.getCode().equals(cantonCode))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("invalid canton code: " + cantonCode))
        .getDistricts()
        .stream()
        .mapToLong(d -> d.getPoliticalCommunities().size())
        .sum();
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   * @throws IllegalArgumentException on unknown canton code
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    return this.model.getCantons()
        .stream()
        .filter(c -> c.getCode().equals(cantonCode))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("invalid canton code: " + cantonCode))
        .getDistricts()
        .size();
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    return this.model.getDistricts()
        .stream()
        .filter(d -> d.getNumber().equals(districtNumber))
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException("invalid district number: " + districtNumber))
        .getPoliticalCommunities()
        .size();
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    return this.model.getDistricts()
        .stream()
        .filter(d -> d.getPoliticalCommunities().stream()
            .anyMatch(political -> political
                .getPostalCommunities().stream()
                .anyMatch(postal -> postal.getZipCode().equals(zipCode))))
        .map(District::getName)
        .collect(toSet());
  }

  /**
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    return this.model.getPoliticalCommunities()
        .stream()
        .filter(political -> political.getPostalCommunities().stream()
            .anyMatch(postal -> postal.getName().equals(postalCommunityName)))
        .map(PoliticalCommunity::getLastUpdate)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            "no matching political community found for postal community name: "
                + postalCommunityName));
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    return this.model.getPoliticalCommunities()
        .parallelStream()
        .filter(p -> p.getPostalCommunities().isEmpty())
        .count();
  }
}
