package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostalCommunityImpl implements PostalCommunity {

  private final String zipCode;

  private final String zipCodeAddition;

  private final String name;

  private final Set<String> politicalCommunityNumbers;

}
