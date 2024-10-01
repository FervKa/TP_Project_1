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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.model.CardRecord;
import org.model.CardTypeRecord;
import org.repository.CardRepository;

public class CardRepositoryImpl implements CardRepository {
    protected static final Logger logger = LogManager.getLogger(CardRepositoryImpl.class);
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
        CardRecord result = cards.stream().filter(p -> p.cardNumber() == cardNumber).findFirst().orElse(null);
        logger.info("Card number found: " + cardNumber);
        return result;

    }

    @Override
    public List<CardRecord> find(YearMonth expiryDate){
        List<CardRecord> result = new ArrayList<>();
        for (CardRecord card : cards) {
            if (card.expiryDate().equals(expiryDate)) {
                result.add(card);
            }
        }
        logger.info("Found " + result.size() + " cards with expiry date filter.");
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
        logger.info("Found " + result.size() + " cards with bank filter.");
        return result;
    }

    public void add(CardRecord card){
        cards.add(card);
        logger.info("Added card: " + card.cardNumber());
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
