package ies.portadaalta.quizzengine.model.converters;

import ies.portadaalta.quizzengine.model.Deck;
import ies.portadaalta.quizzengine.model.loaders.DeckJsonLoader;

import java.io.IOException;


/*
 Create a csv file questions database from json input file.
 */
public class MainJson2CsvConverter {

    public static void main( String[] args ) throws IOException {

        String jsonFilename = "src/main/resources/ChatGPT_trivial_database.json"; //"ChatGPT_trivial_database.json";
        String csvFilename = "ChatGPT_trivial_database.csv";

        DeckJsonLoader jsonLoader = new DeckJsonLoader();
        Deck deck = jsonLoader.loadFromFilename(jsonFilename);

        DeckJsonConverter deckJsonConverter = new DeckJsonConverter();
        deckJsonConverter.writeDeck2Csv(deck, csvFilename);

    }


}
