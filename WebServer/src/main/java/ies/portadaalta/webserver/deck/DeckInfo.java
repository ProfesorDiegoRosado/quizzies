package ies.portadaalta.webserver.deck;

import ies.portadaalta.quizzengine.model.Deck;

import java.util.Map;
import java.util.stream.Collectors;

public class DeckInfo {

    private final String name;
    private final String description;
    private final Map<String, Integer> categoriesNumQuestionsMap;

    public DeckInfo(String name, String description, Map<String, Integer> categoriesNumQuestionsMap) {
        this.name = name;
        this.description = description;
        this.categoriesNumQuestionsMap = categoriesNumQuestionsMap;
    }

    public DeckInfo(Deck deck) {
        this.name = deck.getName();
        this.description = deck.getDescription();
        this.categoriesNumQuestionsMap = deck.getCategoryQuestionsMap()
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(category -> category.getKey().getName(), questions -> questions.getValue().size())
                );
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getCategoriesNumQuestionsMap() {
        return categoriesNumQuestionsMap;
    }

    public int getNumQuestions() {
        return categoriesNumQuestionsMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public String toString() {
        return "DeckInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoriesNumQuestionsMap=" + categoriesNumQuestionsMap +
                '}';
    }
}
