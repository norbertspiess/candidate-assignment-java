package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PoliticalCommunityImpl implements PoliticalCommunity {
  @Include
  private final String number;
  private final String name;
  private final String shortName;
  private final LocalDate lastUpdate;

  private Set<PostalCommunity> postalCommunities;
}
