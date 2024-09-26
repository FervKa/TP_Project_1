package org.records;

import java.util.Date;

public record CardType(
        private String name){
}
public record Card(
        String name,
        String lastName,
        long cardNumber,
        String issurer,
        Date expiryDate,
        Integer cvv,
        CardType cardType,
        String bank){

}