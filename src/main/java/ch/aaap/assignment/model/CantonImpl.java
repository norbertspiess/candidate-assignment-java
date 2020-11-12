package ch.aaap.assignment.model;

import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = {"code"})
public class CantonImpl implements Canton {

  private final String code;

  private final String name;

  private final Set<District> districts;

}
