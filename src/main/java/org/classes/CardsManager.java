package org.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class CardsManager {
    private List<Card> cards = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM");
    CardsManager() {
        cards = readCards();
    }

    private List<Card> readCards() {
        Path filePath = Paths.get("data/bank_cards.txt");

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            return reader.lines().map(this::buildCards).toList();

        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /*Dora, Borer, 4750960927729037, visa, 04/28, 186, credit, bank*/

    private Card buildCards(String line) {
        String[] cardArray = line.split(",");
        var dataDate = cardArray[3].split("/");
        return new Card(
                cardArray[0].trim(),
                cardArray[1].trim(),
                cardArray[2].trim(),
                /*new LocalDate(dataDate[0], dataDate[1]),*/ // Need to refactor the date.
                cardArray[4].trim(),
                cardArray[5].trim(),
                cardArray[6].trim(),
                cardArray[7].trim()
                )
    }

}
