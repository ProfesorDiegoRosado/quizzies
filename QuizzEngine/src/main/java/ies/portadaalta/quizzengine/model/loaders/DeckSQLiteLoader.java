package ies.portadaalta.quizzengine.model.loaders;

import ies.portadaalta.quizzengine.model.Category;
import ies.portadaalta.quizzengine.model.Deck;
import ies.portadaalta.quizzengine.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckSQLiteLoader {

    public DeckSQLiteLoader() {}

    public Deck loadFromDb(String deckName, String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName;

        Map<Category, List<Question>> categoryQuestionsMap = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(url)) {

            List<Category> categories = loadCategories(conn);

            for (Category category: categories) {
                List<Question> questions = loadQuestions(conn, category);
                categoryQuestionsMap.put(category, questions);
            }
        }

        Deck deck = new Deck(deckName, categoryQuestionsMap);
        return deck;
    }

    private List<Question> loadQuestions(Connection conn, Category category) throws SQLException {
        List<Question> questions = new ArrayList<>();

        String sql = "SELECT category, question FROM Question WHERE category='" + category.getName() + "'";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String questionString = resultSet.getString("question");

            List<String> answers = loadAnswers(conn, category, questionString);

            Question question = new Question(category, questionString, answers);
            questions.add(question);
        }

        return questions;
    }

    private List<String> loadAnswers(Connection conn, Category category, String questionString) throws SQLException {
        List<String> answers = new ArrayList<>();

        String sql = "SELECT answer, question FROM Answer WHERE question='" + scapeSingleQuotes(questionString) + "'";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String answer = resultSet.getString("answer");
            answers.add(answer);
        }

        return answers;
    }

    private String scapeSingleQuotes(String s) {
        return s.replace("'", "''");
    }

    private List<Category> loadCategories(Connection conn) throws SQLException {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT name, description FROM Category";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int index = 0;
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Category category = new Category(name, description, index);

            categories.add(category);
            index++;
        }

        return categories;
    }

}
