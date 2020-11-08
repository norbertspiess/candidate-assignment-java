package ch.aaap.assignment.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PoliticalCommunityImpl implements PoliticalCommunity {
  private final String number;
  private final String name;
  private final String shortName;
  private final LocalDate lastUpdate;
  private final String cantonCode;
  private final String cantonName;
  private final String districtName;
  private final String districtNumber;
}
