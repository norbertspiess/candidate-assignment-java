package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DistrictImpl implements District {

  @Include
  private String number;
  private String name;
  private Set<PoliticalCommunity> politicalCommunities;

}
