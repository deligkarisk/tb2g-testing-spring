package org.springframework.samples.petclinic.kosmas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"externalized", "laurel-properties"})
@Component
@Primary
public class PropertiesWordProducer implements WordProducer{
    private String word;

    @Value("${say.word}")
    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String getWord() {
        return word;
    }
}
