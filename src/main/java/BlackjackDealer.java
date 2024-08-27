import java.util.ArrayList;
import java.util.Collections;
public class BlackjackDealer {
    private ArrayList<Card> deck;

    public void generateDeck() {
        deck = new ArrayList<>(52);
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        int[] values = {2,3,4,5,6,7,8,9,10,10,10,10,11};
        for (String suit : suits) {
            for (int value : values) {
                deck.add(new Card(suit, value));
            }
        }
    }

    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>(2);
        hand.add(deck.remove(deck.size()-1));
        hand.add(deck.remove(deck.size()-1));
        return hand;
    }

    public Card drawOne() {
        if (!deck.isEmpty()) {
            return deck.remove(deck.size() - 1);
        }
        return null;
    }

    public void shuffleDeck() {
        generateDeck();
        Collections.shuffle(deck);
    }

    public int deckSize() {
        return deck.size();
    }
}
