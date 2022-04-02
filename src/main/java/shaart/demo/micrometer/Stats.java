package shaart.demo.micrometer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Stats {

  private String name;
  private Integer min;
  private Integer max;
  @Builder.Default
  private List<Stats> subStats = new ArrayList<>();

  public Stats(String name) {
    this.name = name;
    this.subStats = new ArrayList<>();
  }

  public Stats(String name, Integer min, Integer max) {
    this.name = name;
    this.min = min;
    this.max = max;
    this.subStats = new ArrayList<>();
  }

  public Integer getMin() {
    if (min != null) {
      return min;
    }
    return subStats.stream()
        .mapToInt(Stats::getMin)
        .average()
        .stream()
        .mapToInt(a -> (int) Math.floor(a)).findFirst()
        .orElse(0);
  }

  public Integer getMax() {
    if (max != null) {
      return max;
    }
    return subStats.stream()
        .mapToInt(Stats::getMax)
        .average()
        .stream()
        .mapToInt(a -> (int) Math.floor(a)).findFirst()
        .orElse(0);
  }

  public void addSubstats(Stats stats) {
    if (subStats == null) {
      subStats = new ArrayList<>();
    }
    subStats.add(stats);
  }
}
