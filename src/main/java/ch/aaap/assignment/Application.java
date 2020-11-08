package ch.aaap.assignment;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.ModelImpl;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PoliticalCommunityImpl;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.PostalCommunityImpl;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
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

    var internalPoliticalCommunities = politicalCommunities
        .stream()
        .map(c -> PoliticalCommunityImpl
            .builder()
            .number(c.getNumber())
            .name(c.getName())
            .shortName(c.getShortName())
            .lastUpdate(c.getLastUpdate())
            .cantonCode(c.getCantonCode())
            .cantonName(c.getCantonName())
            .districtName(c.getDistrictName())
            .districtNumber(c.getDistrictNumber())
            .build())
        .map(PoliticalCommunity.class::cast)
        .collect(toSet());

    // zip codes can belong to multiple political communities
    var postalCommunitiesByZipCode = postalCommunities
        .stream().collect(groupingBy(c -> c.getZipCode() + c.getZipCodeAddition()));
    var internalPostalCommunities = postalCommunitiesByZipCode.values()
        .stream()
        .map(communities -> PostalCommunityImpl
            .builder()
            .zipCode(communities.get(0).getZipCode())
            .zipCodeAddition(communities.get(0).getZipCodeAddition())
            .name(communities.get(0).getName())
            .politicalCommunityNumbers(
                communities
                    .stream()
                    .map(CSVPostalCommunity::getPoliticalCommunityNumber)
                    .collect(toSet())
            )
            .build())
        .map(PostalCommunity.class::cast)
        .collect(toSet());

    this.model = new ModelImpl(internalPoliticalCommunities, internalPostalCommunities);
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
    rejectInvalidCantonCode(cantonCode);

    return model.getPoliticalCommunities()
        .stream()
        .filter(c -> cantonCode.equals(c.getCantonCode()))
        .count();
  }

  private void rejectInvalidCantonCode(String cantonCode) {
    var isUnknownCantonCode = model.getCantons()
        .stream()
        .map(Canton::getCode)
        .noneMatch(c -> c.equals(cantonCode));

    if (isUnknownCantonCode) {
      throw new IllegalArgumentException("invalid canton code: " + cantonCode);
    }
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   * @throws IllegalArgumentException on unknown canton code
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    rejectInvalidCantonCode(cantonCode);

    return model.getPoliticalCommunities()
        .stream()
        .filter(c -> cantonCode.equals(c.getCantonCode()))
        .map(PoliticalCommunity::getDistrictNumber)
        .distinct()
        .count();
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    var isUnknownDistrictNumber = model.getDistricts()
        .stream()
        .map(District::getNumber)
        .noneMatch(nr -> nr.equals(districtNumber));

    if (isUnknownDistrictNumber) {
      throw new IllegalArgumentException("invalid district number: " + districtNumber);
    }

    return model.getPoliticalCommunities()
        .stream()
        .filter(c -> districtNumber.equals(c.getDistrictNumber()))
        .count();
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    var communityNumbersForZipCode = this.model.getPostalCommunities()
        .stream()
        .filter(c -> c.getZipCode().equals(zipCode))
        .flatMap(c -> c.getPoliticalCommunityNumbers().stream())
        .collect(toSet());

    return this.model.getDistricts()
        .stream()
        .filter(district -> doIntersect(district.getCommunityNumbers(), communityNumbersForZipCode))
        .map(District::getName)
        .collect(toSet());
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
    var postalCommunity = this.model.getPostalCommunities()
        .stream()
        .filter(c -> c.getName().equals(postalCommunityName))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException(
            "unknown postal community name: " + postalCommunityName));

    return this.model.getPoliticalCommunities()
        .stream()
        .filter(c -> postalCommunity.getPoliticalCommunityNumbers().contains(c.getNumber()))
        .map(PoliticalCommunity::getLastUpdate)
        .findAny()
        .orElseThrow();
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
    var communityNumbersFromPostalCommunities = this.model.getPostalCommunities()
        .stream()
        .flatMap(c -> c.getPoliticalCommunityNumbers().stream())
        .collect(toSet());

    return this.model.getPoliticalCommunities()
        .stream()
        .filter(c -> !communityNumbersFromPostalCommunities.contains(c.getNumber()))
        .count();
  }
}
