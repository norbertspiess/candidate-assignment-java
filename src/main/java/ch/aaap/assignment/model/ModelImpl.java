package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModelImpl implements Model {

  private final Set<PoliticalCommunity> politicalCommunities;

  private final Set<PostalCommunity> postalCommunities;

  private final Set<Canton> cantons;

  private final Set<District> districts;

}
