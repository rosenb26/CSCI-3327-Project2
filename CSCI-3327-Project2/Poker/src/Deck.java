
import java.util.ArrayList;
import java.util.Random;

/**
 * A class to represent a standard deck of 52 playing cards.
 */
public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();
        this.constructDeck();
    }

    /**
     * A deck has four suits (clubs, hearts, diamonds, spades) and 13 ranks
     * (2-Ten, Jack, Queen, King, Ace). Note that in most forms of poker,
     * including the classical 5-card draw variant implemented in this program,
     * the powerful ace can assume either the lowest or highest rank depending
     * on the other constituent cards in the hand. For example, if the player's
     * hand is A, 2, 3, 4, 5, the ace would rank directly below the 2 in order
     * to form a straight A-5, while if the hand contained the cards T, J, Q, K,
     * A, the ace would rank directly above the king in order to form a straight
     * T-A.
     */
    private void constructDeck() {
        String[] suits = {"C", "D", "H", "S"};
        String[] ranks = new String[13];
        ranks[0] = "A";

        for (int i = 2; i <= 9; i++) {
            ranks[i - 1] = Integer.toString(i);
        }

        ranks[9] = "T";
        ranks[10] = "J";
        ranks[11] = "Q";
        ranks[12] = "K";

        /* Populate the deck: AC, ..., KC, AD, ..., KD, AH, ..., KH, ..., AS, ..., KS. */
        for (String rank : ranks) {
            for (String suit : suits) {
                this.deck.add(new Card(rank, suit));
            }
        }

    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    /**
     * Removes and returns a single card at random from the current deck. Note
     * that multiple cards can be thus removed from the deck, thereby simulating
     * the drawing of cards without replacement from a real deck.
     *
     * @return A single random card drawn from the deck.
     */
    public Card drawRandomCard() {
        Random rng = new Random();
        return this.deck.remove(rng.nextInt(this.deck.size()));
    }
}
