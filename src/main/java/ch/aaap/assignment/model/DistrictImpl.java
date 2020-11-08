package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DistrictImpl implements District {

  @EqualsAndHashCode.Include
  private final String number;

  private final String name;

  private final Set<PoliticalCommunity> politicalCommunities;

}
