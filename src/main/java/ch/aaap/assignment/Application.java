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
    rejectInvalidCantonCode(cantonCode);

    return this.model.getCantons()
        .stream()
        .filter(c -> c.getCode().equals(cantonCode))
        .mapToLong(c -> c.getDistricts().size()).sum();
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
//    return model.getDistricts()
//        .stream().filter(d -> d.getNumber().equals(districtNumber))
//        .mapToLong(d -> d.getPostalCommunities().size())
//        .sum();

//    if (isUnknownDistrictNumber) {
//      throw new IllegalArgumentException("invalid district number: " + districtNumber);
//    }
//
//    return model.getPoliticalCommunities()
//        .stream()
//        .filter(c -> districtNumber.equals(c.getDistrictNumber()))
//        .count();
    return 0;
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
//    var communityNumbersForZipCode = this.model.getPostalCommunities()
//        .stream()
//        .filter(c -> c.getZipCode().equals(zipCode))
//        .flatMap(c -> c.getPoliticalCommunityNumbers().stream())
//        .collect(toSet());
//
//    return this.model.getDistricts()
//        .stream()
//        .filter(district -> doIntersect(district.getCommunityNumbers(), communityNumbersForZipCode))
//        .map(District::getName)
//        .collect(toSet());
    return Set.of();
  }

  private boolean doIntersect(Set<String> set1, Set<String> set2) {
    return set2.stream().anyMatch(set1::contains);
  }

  /**
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
//    var postalCommunity = this.model.getPostalCommunities()
//        .stream()
//        .filter(c -> c.getName().equals(postalCommunityName))
//        .findAny()
//        .orElseThrow(() -> new IllegalArgumentException(
//            "unknown postal community name: " + postalCommunityName));
//
//    return this.model.getPoliticalCommunities()
//        .stream()
//        .filter(c -> postalCommunity.getPoliticalCommunityNumbers().contains(c.getNumber()))
//        .map(PoliticalCommunity::getLastUpdate)
//        .findAny()
//        .orElseThrow();
    return LocalDate.now();
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
//    var communityNumbersFromPostalCommunities = this.model.getPostalCommunities()
//        .stream()
//        .flatMap(c -> c.getPoliticalCommunityNumbers().stream())
//        .collect(toSet());
//
//    return this.model.getPoliticalCommunities()
//        .stream()
//        .filter(c -> !communityNumbersFromPostalCommunities.contains(c.getNumber()))
//        .count();
    return 0;
  }
}
