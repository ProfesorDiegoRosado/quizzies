package ies.portadaalta.webserver.deck;

import ies.portadaalta.quizzengine.model.Deck;
import ies.portadaalta.quizzengine.model.loaders.DeckJsonLoader;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ComponentScan("ies.portadaalta.webserver")
@Repository
public class DeckRepository {

    // Constants
    private static final String DECKS_PATH = "static/assets/decks/";

    // Class attributes
    private static final DeckJsonLoader deckJsonLoader = new DeckJsonLoader();

    // Attributes
    private final List<DeckInfo> decksInfo = new ArrayList<>();


    public DeckRepository() throws IOException {
        load();
    }

    private void load() throws IOException {
        Path fullResourcesPath = getResourcesFullPath();

        List<Path> decksPath = getDecksInfoPaths(fullResourcesPath);

        for (Path deckPath : decksPath) {
            DeckInfo deckInfo = loadDeckInfoFromFile(deckPath);
            decksInfo.add(deckInfo);
        }
    }

    private Path getResourcesFullPath() {
        URL resource = getClass().getClassLoader().getResource(".");
        assert resource != null;
        Path fullResourcesPath = Paths.get(resource.getPath() + DECKS_PATH);
        return fullResourcesPath;
    }

    private List<Path> getDecksInfoPaths(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        String fileExtension = ".json";
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(Files::isRegularFile)   // is a file
                    .filter(p -> p.getFileName().toString().endsWith(fileExtension))
                    .collect(Collectors.toList());
        }
        return result;
    }

    private DeckInfo loadDeckInfoFromFile(Path deckPath) throws IOException {
        Deck deck = deckJsonLoader.loadFromFile(deckPath.toFile());
        return new DeckInfo(deck);
    }

    public List<DeckInfo> getDecksInfo() {
        return decksInfo;
    }

}
