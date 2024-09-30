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
            return reader.lines().map( this::buildCards ).toList();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CardRecord findOne(long cardNumber) {
        for (CardRecord card : cards) {
            if (card.cardNumber() == cardNumber) {
                return card;
            }
        }
        return null;
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
        String name = cardArray[0].trim();
        String lastName = cardArray[1].trim();
        long cardNumber = Long.parseLong(cardArray[2].trim());
        String issuer = cardArray[3].trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expiryDate = YearMonth.parse(cardArray[4].trim(), formatter);

        Integer cvv = Integer.parseInt(cardArray[5].trim());
        CardTypeRecord cardTypeRecord = new CardTypeRecord(cardArray[6].trim());
        String bank = cardArray[7].trim();
        return new CardRecord(
            name,
            lastName,
            cardNumber,
            issuer,
            expiryDate,
            cvv,
            cardTypeRecord,
            bank
        );
    }

}
