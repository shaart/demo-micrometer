package shaart.demo.micrometer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableJpaAuditing
@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class DemoMicrometerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoMicrometerApplication.class, args);
  }

  @Scheduled(fixedDelay = 240, timeUnit = TimeUnit.SECONDS)
  public void doSmth() throws InterruptedException {
    List<Stats> stats = new ArrayList<>();
    Stats currentStats = new Stats("stat" + 0);
    for (int i = 0; i < 1_000_000; i++) {
      if (i % 100 == 1) {
        stats.add(currentStats);
        currentStats = new Stats("stat" + i);
        continue;
      }
      currentStats.addSubstats(new Stats("substat" + i, calcMin(), calcMax()));
      if (i % 10_000 == 0) {
        log.info("#{}", i);
      }
    }
    log.info("{}", stats);
  }

  private int calcMin() {
    return ThreadLocalRandom.current().nextInt(0, 1000);
  }

  private int calcMax() throws InterruptedException {
    Thread.sleep(1);
    return ThreadLocalRandom.current().nextInt(1000, 100_000);
  }

}
