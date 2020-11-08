package ch.aaap.assignment.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class CantonImpl implements Canton {

  private final String code;
  private final String name;

}
