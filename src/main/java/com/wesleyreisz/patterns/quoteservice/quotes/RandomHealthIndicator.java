package com.wesleyreisz.patterns.quoteservice.quotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component("myHealthCheck")
public class RandomHealthIndicator implements HealthIndicator {
    private static final Logger LOG = LoggerFactory.getLogger(QuoteController.class);

    @Override
    public Health health() {
        LOG.info("Running healthcheck code");
        double chance = ThreadLocalRandom.current().nextDouble();
        Health.Builder status = Health.up();
        if (chance > .9){
            status = Health.down()
                    .withDetail("Error code", "broke broken broker")
                    .withDetail("Chance", chance);
        }
        return status.build();
    }
}
