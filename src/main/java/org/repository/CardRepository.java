package org.repository;

import org.model.CardRecord;
import java.time.YearMonth;
import java.util.List;

public interface CardRepository {
    CardRecord findOne(long cardNumber);
    List<CardRecord> find(YearMonth expiryDate);
    List<CardRecord> find(String bank);
    CardRecord add(CardRecord card);
}
