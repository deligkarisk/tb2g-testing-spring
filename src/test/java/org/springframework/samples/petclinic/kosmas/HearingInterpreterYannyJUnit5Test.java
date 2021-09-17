package org.springframework.samples.petclinic.kosmas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {BaseConfig.class, YannyConfig.class})
class HearingInterpreterYannyJUnit5Test {

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIheard() {

        String word = hearingInterpreter.whatIheard();
        assertEquals("Yanny", word);

    }
}