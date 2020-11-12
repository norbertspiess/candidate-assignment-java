package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PoliticalCommunityImpl implements PoliticalCommunity {

  @EqualsAndHashCode.Include
  private final String number;

  private final String name;

  private final String shortName;

  private final LocalDate lastUpdate;

  @Setter
  private Set<PostalCommunity> postalCommunities;

}
