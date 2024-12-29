package ies.portadaalta.webserver.config;

/*
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final Deck deck;

    public WebSocketConfig() throws IOException {
        String jsonDeckPath = "static/assets/decks/";
        String jsonFilename = System.getenv("JSON_DECK_FILENAME");
        String fullPathName = jsonDeckPath + jsonFilename;
        BufferedReader deckBufferedReader = getFileFromResources(fullPathName);

        DeckJsonLoader deckJsonLoader = new DeckJsonLoader();
        String fileContent = String.join("\n",deckBufferedReader.lines().toList());
        Deck deck = deckJsonLoader.loadFromString(fileContent);
        this.deck = deck;

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/quizies");
    }

    @Bean
    public Deck getDeck() {
        return deck;
    }

    /*
    @Bean
    public GameEngine getGameEngine() {
        Player player = new Player("Player1", deck.getCategories());
        GameEngine gameEngine = new GameEngine("Test Game", deck, List.of(player));
        return gameEngine;
    }
     */

/*

    private static BufferedReader getFileFromResources(String filename) {
        ClassLoader classLoader = WebServerApplication.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader;
    }

}

 */
