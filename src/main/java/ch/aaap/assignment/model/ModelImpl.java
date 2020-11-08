package ch.aaap.assignment.model;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class ModelImpl implements Model {

  private Set<PoliticalCommunity> politicalCommunities;
  private Set<PostalCommunity> postalCommunities;
  private Set<Canton> cantons;
  private Set<District> districts;

  public ModelImpl(Collection<Canton> cantons) {
    this.cantons = new HashSet<>(cantons);
    this.districts = cantons.stream()
        .flatMap(c -> c.getDistricts().stream()).collect(toSet());
    this.politicalCommunities = districts.stream()
        .flatMap(d -> d.getPoliticalCommunities().stream()).collect(toSet());
    this.postalCommunities = this.politicalCommunities.stream()
        .flatMap(p -> p.getPostalCommunities().stream()).collect(toSet());
  }
}
