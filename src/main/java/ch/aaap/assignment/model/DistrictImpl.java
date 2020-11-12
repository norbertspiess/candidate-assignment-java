package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = {"number"})
public class DistrictImpl implements District {

  private final String number;

  private final String name;

  private final Set<PoliticalCommunity> politicalCommunities;

}
