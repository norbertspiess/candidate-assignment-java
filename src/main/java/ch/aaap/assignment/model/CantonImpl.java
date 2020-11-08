package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class CantonImpl implements Canton {

  private String code;
  private String name;
  private Set<District> districts;

}
