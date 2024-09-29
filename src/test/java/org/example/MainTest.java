package org.example;

import org.classes.CardsManager;
import org.classes.CreditCards;
import org.junit.jupiter.api.Test;

public class MainTest {
    CreditCards creditCards = new CreditCards();
    CardsManager cardsManager = new CardsManager();

    @Test
    public void test() {
        CreditCards.runner();
    }


    /*Type here the tests should be to test all the methods and the rest of the app*/

    @Test
    public void test2() {
        System.out.println(cardsManager.readCards());
    }
}
