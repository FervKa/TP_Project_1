package org.classes;

import org.interfaces.CardRepository;
import org.records.Card;
import org.records.CardType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CardsManager implements CardRepository {
    private List<Card> cards = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yy/MM");

    public CardsManager() {
        cards = readCards();
    }

    public List<org.records.Card> readCards() {
        Path filePath = Paths.get("data/bank_cards.txt");

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            cards = reader.lines().map(this::buildCards).toList();
            return cards;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public org.records.Card buildCards(String line) {
        String[] cardArray = line.split(",");
        Date date = null;
        try {
            if (sdf.parse(cardArray[4]) instanceof Date) {
                date = sdf.parse(cardArray[4]);
            }

            return new org.records.Card(
                    cardArray[0].trim(),
                    cardArray[1].trim(),
                    Long.parseLong(cardArray[2].trim()),
                    cardArray[4].trim(),
                    date,
                    Integer.parseInt(cardArray[5].trim()),
                    new CardType(cardArray[6].trim()),
                    cardArray[7].trim()
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Card findOne(long cardNumber) {
        return null;
    }

    @Override
    public List<Card> find(Date expirateDate) {
        return List.of();
    }

    @Override
    public List<Card> find(String bank) {
        return List.of();
    }

    @Override
    public void add(Card card) {

    }
}
