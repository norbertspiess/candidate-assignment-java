package ch.aaap.assignment.model;

import java.util.Set;

public interface Canton {

  String getCode();

  String getName();

  Set<District> getDistricts();

}
