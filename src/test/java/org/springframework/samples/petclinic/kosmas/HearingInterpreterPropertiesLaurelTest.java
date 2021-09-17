package org.springframework.samples.petclinic.kosmas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = HearingInterpreterPropertiesLaurelTest.TestConfig.class)
@ActiveProfiles("laurel-properties")
@TestPropertySource("classpath:laura.properties")
class HearingInterpreterPropertiesLaurelTest {


    @Configuration
    @Profile("laurel-properties")
    @ComponentScan("org.springframework.samples.petclinic.kosmas")
    static class TestConfig {
    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIheard() {
        String word = hearingInterpreter.whatIheard();
        assertEquals("LauRRA!", word);
    }

}