package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictImpl implements District {

  private String number;
  private String name;
  private Set<PoliticalCommunity> politicalCommunities;

}
