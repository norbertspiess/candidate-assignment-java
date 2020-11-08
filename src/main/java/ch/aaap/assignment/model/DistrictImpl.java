package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictImpl implements District {

  private final String number;
  private final String name;

}
