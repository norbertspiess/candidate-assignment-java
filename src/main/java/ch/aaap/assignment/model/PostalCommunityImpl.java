package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class PostalCommunityImpl implements PostalCommunity {

  private final String zipCode;

  private final String zipCodeAddition;

  private final String name;

}
