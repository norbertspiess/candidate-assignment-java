package ch.aaap.assignment.model;

import java.util.Set;

public interface District {

  String getNumber();

  String getName();

  Set<PoliticalCommunity> getPoliticalCommunities();

}
