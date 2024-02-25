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

    public List<Map<String, String>> drawCardsFromDeck(String deckId, int count) {
        Response response = RestAssured.get(URL + deckId + "/draw/?count=" + count);
        return response.jsonPath().getList("cards");
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
        List<Map<String, String>> drawnCards = drawCardsFromDeck(deckId, 52);

        for (Map<String, String> card : drawnCards) {
            String value = card.get("value");
            if (!value.equals("ACE")) {
                return false;
            }
        }
        return true;
    }

    public List<String> drawCardsFromBottom(String deckId, int count) {
        List<String> bottomCards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Response response = RestAssured.get(URL + deckId + "/draw/bottom/");
            String cardCode = response.jsonPath().getString("cards[0].code");
            bottomCards.add(cardCode);
        }
        return bottomCards;
    }

    public boolean verifyCardInDeck(String deckId, String cardCode) {
        Response response = RestAssured.get(URL + deckId + "/");
        List<Map<String, String>> cards = response.jsonPath().getList("cards");
        for (Map<String, String> card : cards) {
            if (card.get("code").equals(cardCode)) {
                return true;
            }
        }
        return false;
    }
}

