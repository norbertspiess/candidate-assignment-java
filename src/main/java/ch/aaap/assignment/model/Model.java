package ch.aaap.assignment.model;

import java.util.Set;

public interface Model {

  Set<PoliticalCommunity> getPoliticalCommunities();

  Set<PostalCommunity> getPostalCommunities();

  Set<Canton> getCantons();

  Set<District> getDistricts();

}
