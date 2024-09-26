package org.interfaces;

import org.records.Card;
import java.util.Date;
import java.util.List;

public interface CardRepository {
Card findOne(long cardNumber);

List<Card> find(Date expirateDate);

List<Card> find(String bank);

void add(Card card);
}
