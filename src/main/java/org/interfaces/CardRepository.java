package org.interfaces;

import org.records.Card;
import java.util.Date;

public interface CardRepository {
Card findOne(long cardNumber);

list<Card> find(Date expirateDate);

list<Card> find(String bank);

void add(Card card);
}
