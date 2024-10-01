package org.repository.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.model.CardRecord;
import org.model.CardTypeRecord;
import org.repository.CardRepository;

public class CardRepositoryManager implements CardRepository {
    private List<CardRecord> cards = null;

    public CardRepositoryManager() {
        cards = readCards();
    }

    private List<CardRecord> readCards() {
        Path filePath = Paths.get("data/bank_cards.txt");
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            return new ArrayList<>(reader.lines().map( this::buildCards ).toList());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CardRecord findOne(long cardNumber) {
        return cards.stream().filter(p -> p.cardNumber() == cardNumber).findFirst().orElse(null);
    }

    @Override
    public List<CardRecord> find(YearMonth expiryDate){
        List<CardRecord> result = new ArrayList<>();
        for (CardRecord card : cards) {
            if (card.expiryDate().equals(expiryDate)) {
                result.add(card);
            }
        }
        return result;
    }

    @Override
    public List<CardRecord> find(String bank){
        List<CardRecord> result = new ArrayList<>();
        for (CardRecord card : cards) {
            if (card.bank().equals(bank)) {
                result.add(card);
            }
        }
        return result;
    }

    public void add(CardRecord card){
        cards.add(card);
    }

    private CardRecord buildCards(String line) {
        String[] cardArray = line.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return new CardRecord(
            cardArray[0].trim(),
            cardArray[1].trim(),
            Long.parseLong(cardArray[2].trim()),
            cardArray[3].trim(),
            YearMonth.parse(cardArray[4].trim(), formatter),
            Integer.parseInt(cardArray[5].trim()),
            new CardTypeRecord(cardArray[6].trim()),
            cardArray[7].trim()
        );
    }

}
