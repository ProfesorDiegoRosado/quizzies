package ies.portadaalta.webserver.deck;

//import ies.portadaalta.gameengine.model.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Random;


@RestController
public class DeckController {


    private DeckRepository deckRepository;
    //private GameEngine gameEngine;
    //private Deck deck;
    //private final Random rand = new Random();

    @Autowired
    public DeckController(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @GetMapping(path = "${apiPrefix}/decks/info")
    public List<DeckInfo> getDecks() {
        return deckRepository.getDecksInfo();
    }


    /*
    @MessageMapping("/gameevent")
    @SendTo("/topic/gameevent")
    public GameEventOutputMessage gameEvent(GameEventInputMessage gameEvent) throws Exception {
        System.out.println("GameEvent -> " + gameEvent);
        switch (gameEvent.getEvent()) {
            case "StartGame" -> {
                deck = gameEngine.getDeck();
                Set<Category> categories = deck.getCategories();
                return new GameEventOutputMessage("StartEvent", categories);
            }
            case "Question" -> {
                List<String> categoriesNames = gameEvent.getArguments();
                String categoryName = categoriesNames.get(rand.nextInt(categoriesNames.size()));
                Question nextQuestion = deck.getNextQuestion(categoryName);
                return new GameEventOutputMessage("Question", nextQuestion);
            }
            default -> throw new Exception("Event" + gameEvent.getEvent() + " not found");

        }
    }

     */


}
