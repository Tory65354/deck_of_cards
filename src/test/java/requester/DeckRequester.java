package requester;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeckRequester {

    private static final String URL = "https://deckofcardsapi.com/api/deck/";

    public String createShuffledDeck(int deckCount) {

        Response response = RestAssured.get(URL + "new/shuffle/?deck_count=" + deckCount);
        return response.jsonPath().getString("deck_id");
    }

    public void drawCardsFromDeck(String deckId, int count) {
        RestAssured.get(URL + deckId + "/draw/?count=" + count);
    }

    public int getRemainingCardCount(String deckId) {
        Response response = RestAssured.get(URL + deckId + "/");
        return response.jsonPath().getInt("remaining");
    }

    public String createAceDeck(int deckCount) {
        StringBuilder cards = new StringBuilder();
        for (int i = 0; i < deckCount; i++) {
            if (i > 0) {
                cards.append(",");
            }
            cards.append("AS,AD,AC,AH");
        }
        Response response = RestAssured.get(URL + "new/shuffle/?cards=" + cards);
        return response.jsonPath().getString("deck_id");
    }

    public boolean verifyAllDrawnCardsAreAces(String deckId) {
        Response response = RestAssured.get(URL + deckId + "/pile/discard/list/");
        List<Map<String, String>> drawnCards = response.jsonPath().getList("piles.discard.cards");

        for (Map<String, String> card : drawnCards) {
            String value = card.get("value");
            if (!value.equals("ACE")) {
                return false;
            }
        }
        return true;
    }

    public List<String> drawCardsFromBottom(String deckId, int cardCount) {
        List<String> drawnCards = new ArrayList<>();
         Response response = RestAssured.get(URL + deckId + "/draw/bottom/?count=" + cardCount);
         List<Map<String, String>> drawnCardsList = response.jsonPath().getList("cards");
         for (Map<String, String> card : drawnCardsList) drawnCards.add(card.get("code"));
         return drawnCards;
    }

    public boolean verifyDrawnCardsNotInDeck(String deckId, List<String> drawnCards) {
        Response response = RestAssured.get(URL + deckId + "/");
        List<Map<String, String>> remainingCards = response.jsonPath().getList("cards");

        for (String drawnCard : drawnCards) {
         boolean cardFound = false;
         for (Map<String, String> card : remainingCards) {
             if (card.get("code").equals(drawnCard)) {
                 cardFound = true;
                 break;
             }
         }
         if (cardFound) {
             return false;
         }
        }
        return true;

    }
}

