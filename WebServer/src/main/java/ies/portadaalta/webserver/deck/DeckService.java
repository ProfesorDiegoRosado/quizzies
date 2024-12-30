package ies.portadaalta.webserver.deck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeckService {

    @Bean
    public DeckService getDeckService() {
        return new DeckService();
    }
}
