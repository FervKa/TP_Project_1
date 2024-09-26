package org.classes;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CardsManager {


    public static void main(String[] args) {
        JsonFactory factory = new JsonFactory();
        ArrayList<String> cardFields = new ArrayList<>(Arrays.asList("name",
                "cardNumber",
                "issurer",
                "expiryDate",
                "cvv",
                "type",
                "bank"
        ));

        try {
            JsonParser parser = factory.createParser(new File("data/bank_cards.json"));

            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();

                if (token == null) break;

                if (JsonToken.FIELD_NAME.equals(token)) {
                    String fieldName = parser.getCurrentName();

                    if (cardFields.contains(fieldName)) {
                        token = parser.nextToken();

                        if (token == JsonToken.VALUE_STRING) {
                            System.out.println(fieldName + ": " + parser.getText());
                        } else if (token == JsonToken.VALUE_NUMBER_INT) {
                            System.out.println(fieldName + ": " + parser.getLongValue());
                        } else if (token == JsonToken.VALUE_NUMBER_FLOAT) {
                            System.out.println(fieldName + ": " + parser.getDoubleValue());
                        } else if (token == JsonToken.VALUE_TRUE || token == JsonToken.VALUE_FALSE) {
                            System.out.println(fieldName + ": " + parser.getBooleanValue());
                        }
                    }
                }
            }

            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
