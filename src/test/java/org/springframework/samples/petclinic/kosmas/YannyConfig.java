package org.springframework.samples.petclinic.kosmas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YannyConfig {

    @Bean
    public YannyWordProducer yannyWordProducer() {
        return new YannyWordProducer();
    }
}
