package shaart.demo.micrometer.job;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CalculationJob {

  @Timed(value = "sample_calculations", description = "Time taken to do sample calculations")
  @Scheduled(fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
  public void calcJob() {
    log.info("## Calc job start");
    long sum = 0;
    for (int i = 0; i < 1_000_000; i++) {
      sum += ThreadLocalRandom.current().nextInt(0, 1000);

      if (i % 10_000 == 0) {
        log.info("#calc{}", i);
      }
    }
    log.info("Sum: {}", sum);
    log.info("## Calc job end");
  }
}
