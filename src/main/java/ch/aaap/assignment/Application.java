package ch.aaap.assignment;

import static java.util.stream.Collectors.toSet;

import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.ModelImpl;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PoliticalCommunityImpl;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.PostalCommunityImpl;
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

    Set<PoliticalCommunity> internalPoliticalCommunities = politicalCommunities
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
        .collect(toSet());

    Set<PostalCommunity> internalPostalCommunities = postalCommunities.stream()
        .map(c -> PostalCommunityImpl
            .builder()
            .zipCode(c.getZipCode())
            .zipCodeAddition(c.getZipCodeAddition())
            .name(c.getName())
            .build())
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
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }

  /**
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
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
    // TODO implementation
    throw new RuntimeException("Not yet implemented");
  }
}
