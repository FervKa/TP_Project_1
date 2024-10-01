package org.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.model.CardRecord;
import org.repository.impl.CardRepositoryImpl;
import org.model.CardTypeRecord;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardRepositoryTest {
    private CardRepositoryImpl cardRepository;

    @BeforeEach
    public void setUp() {
        cardRepository = new CardRepositoryImpl();
    }
    @Test
    public void test_find_by_number() {
        long existingCardNumber = 5132344882847009L;
        long nonExistingCardNumber = 9999999999999999L;
        CardRecord card = cardRepository.findOne(existingCardNumber);
        assertNotNull(card);
        assertEquals(existingCardNumber, card.cardNumber());
        assertNull(cardRepository.findOne(nonExistingCardNumber));
    }
    @Test
    public void test_add_card() {
        CardRecord newCard = new CardRecord(
                "Carlos", "Lopez", 9876543210123456L, "Visa",
                YearMonth.of(2025, 12), 321,
                new CardTypeRecord("Crédito"), "bank");

        cardRepository.add(newCard);

        CardRecord foundCard = cardRepository.findOne(9876543210123456L);
        assertNotNull(foundCard);
    }
    @Test
    public void test_find_by_bank() {
        String bankName = "bank";
        List<CardRecord> cards = cardRepository.find(bankName);

        assertFalse(cards.isEmpty());
        for (CardRecord card : cards) {
            assertEquals(bankName, card.bank());
        }
    }
    @Test
    public void test_find_by_expiry_date() {
        YearMonth expiryDate = YearMonth.of(2025, 12);
        List<CardRecord> cards = cardRepository.find(expiryDate);

        assertFalse(cards.isEmpty());
        for (CardRecord card : cards) {
            assertEquals(expiryDate, card.expiryDate());
        }
    }
}

    /*Type here the tests should be to test all the methods and the rest of the app*/


