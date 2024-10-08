package org.model;
import java.time.YearMonth;

public record CardRecord(
        String name,
        String lastName,
        long cardNumber,
        String issurer,
        YearMonth expiryDate,
        Integer cvv,
        CardTypeRecord cardTypeRecord,
        String bank){
}