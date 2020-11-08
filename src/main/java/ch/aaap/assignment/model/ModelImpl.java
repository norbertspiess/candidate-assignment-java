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

    this.districts = extractDistricts();
  }

  private Set<District> extractDistricts() {
    var communitiesByDistrictNumber = this.politicalCommunities
        .stream().collect(groupingBy(PoliticalCommunity::getDistrictNumber));

    // districts can be part of multiple communities, so we have to gather those
    return communitiesByDistrictNumber
        .values().stream()
        .map(communities ->
            DistrictImpl.builder()
                .number(communities.get(0).getDistrictNumber())
                .name(communities.get(0).getDistrictName())
                .communityNumbers(communities
                    .stream()
                    .map(PoliticalCommunity::getNumber)
                    .collect(toSet()))
                .build()
        )
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
