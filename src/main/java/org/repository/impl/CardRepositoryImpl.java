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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.model.CardRecord;
import org.model.CardTypeRecord;
import org.repository.CardRepository;

public class CardRepositoryImpl implements CardRepository {
    protected static final Logger logger = LoggerFactory.getLogger(CardRepositoryImpl.class);
    private List<CardRecord> cards = null;

    public CardRepositoryImpl() {
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
        CardRecord result = cards.stream()
                .filter(p -> p.cardNumber() == cardNumber)
                .findFirst()
                .orElse(null);
        if (result == null) {
            logger.warn("Card not found: {}", cardNumber);
        } else {
            logger.info("Card number found: {}", cardNumber);
        }
        return result;

    }

    @Override
    public List<CardRecord> find(YearMonth expiryDate){
        List<CardRecord> result = cards.stream()
                .filter(p -> p.expiryDate().equals(expiryDate))
                .toList();
        if (result.isEmpty()) {
            logger.warn("Expiry date does not match any item in the file: {}", expiryDate);
        } else {
            logger.info("Found {} cards with expiry date filter.", result.size());
        }
        return result;
    }

    @Override
    public List<CardRecord> find(String bank){
        List<CardRecord> result = cards.stream()
                .filter(p -> p.bank().equals(bank))
                .toList();
        if (result.isEmpty()) {
            logger.warn("Bank does not match any item in the file: {}", bank);
        } else {
            logger.info("Found {} cards with bank filter.", result.size());
        }
        return result;
    }

    public CardRecord add(CardRecord card){
        cards.add(card);
        logger.info("Added card: {}", card.cardNumber());
        return card;
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
