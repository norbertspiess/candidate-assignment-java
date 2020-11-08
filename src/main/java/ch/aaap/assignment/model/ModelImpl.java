package ch.aaap.assignment.model;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.util.Set;
import lombok.Data;

@Data
public class ModelImpl implements Model {

  private final Set<PoliticalCommunity> politicalCommunities;
  private final Set<PostalCommunity> postalCommunities;
  private Set<Canton> cantons;
  private Set<District> districts;

  public ModelImpl(
      Set<PoliticalCommunity> politicalCommunities,
      Set<PostalCommunity> postalCommunities
  ) {
    this.politicalCommunities = politicalCommunities;
    this.postalCommunities = postalCommunities;

    this.cantons = this.politicalCommunities.stream()
        .map(c -> CantonImpl.builder()
            .code(c.getCantonCode())
            .name(c.getCantonName())
            .build())
        .collect(toSet());

    this.districts = exractDistricts();
  }

  private Set<District> exractDistricts() {
    var communitiesByDistrictNumber = this.politicalCommunities
        .stream()
        .collect(groupingBy(PoliticalCommunity::getDistrictNumber));
    return communitiesByDistrictNumber
        .entrySet().stream()
        .map(district -> {
          var districtNumber = district.getKey();
          var districtName = district.getValue().get(0).getDistrictName();
          var communityNumbers = district.getValue()
              .stream()
              .map(PoliticalCommunity::getNumber)
              .collect(toSet());

          return DistrictImpl.builder()
              .number(districtNumber)
              .name(districtName)
              .communityNumbers(communityNumbers)
              .build();
        })
        .collect(toSet());
  }

  @Override
  public Set<PoliticalCommunity> getPoliticalCommunities() {
    return politicalCommunities;
  }

  @Override
  public Set<PostalCommunity> getPostalCommunities() {
    return postalCommunities;
  }

  @Override
  public Set<Canton> getCantons() {
    return cantons;
  }

  @Override
  public Set<District> getDistricts() {
    return districts;
  }
}
