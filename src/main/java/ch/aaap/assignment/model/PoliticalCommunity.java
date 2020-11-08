package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.Set;

public interface PoliticalCommunity {

  String getNumber();

  String getName();

  String getShortName();

  LocalDate getLastUpdate();

  Set<PostalCommunity> getPostalCommunities();

}
