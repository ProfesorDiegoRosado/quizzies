package ies.portadaalta.webserver.deck;

import ies.portadaalta.quizzengine.model.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class DeckController {


    // Attributes
    private final DeckRepository deckRepository;
    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService, DeckRepository deckRepository) {
        this.deckService = deckService;
        this.deckRepository = deckRepository;
    }

    @GetMapping(path = "${apiPrefix}/decks/info")
    public List<DeckInfo> getDecks() {
        return deckRepository.getDecksInfo();
    }

    @GetMapping(path = "${apiPrefix}/deck/{deckName}")
    public Deck getDeck(@PathVariable("deckName") String deckName) {
        Deck deck = deckRepository.getDeckByName(deckName);
        return deck;
    }



}
