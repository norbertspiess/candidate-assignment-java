package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostalCommunityImpl implements PostalCommunity {

  @Include
  private final String zipCode;

  @Include
  private final String zipCodeAddition;

  private final String name;

}
