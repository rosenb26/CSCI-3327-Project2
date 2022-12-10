
import java.util.Map;
import java.util.TreeMap;

public class Simulator {

    /* Map the type of hand to the number of occurrences. */
    private Map<Integer, Integer> handCounts;
    private int numberOfSimulations;

    public Simulator(int n) {
        this.handCounts = new TreeMap<>();
        this.numberOfSimulations = n;
    }

    /**
     * Simulate drawing hands from a deck. Each iteration draws 5 cards from a
     * fresh deck, passes the hand to a HandReader object to determine the type
     * of hand it is, and updates the appropriate counter in the map that stores
     * the results.
     */
    public void simulate() {
        for (int i = 0; i < this.numberOfSimulations; i++) {
            Deck deck = new Deck();
            String[] hand = this.drawRandomHand(deck);
            int result = new HandReader().handReader(hand);
            this.handCounts.put(result, this.handCounts.getOrDefault(result, 0) + 1);
        }
    }

    /**
     * Draw 5 random cards from the deck.
     *
     * @param deck The deck from which the cards are to be drawn.
     * @return A String array of 5 cards.
     */
    private String[] drawRandomHand(Deck deck) {
        Card[] hand = new Card[5];
        for (int i = 0; i < 5; i++) {
            hand[i] = deck.drawRandomCard();
        }
        return this.convertHand(hand);
    }

    /**
     * Convert an array of Card objects to an array containing the String
     * representations of the individual cards.
     *
     * @param cards The array of Card objects.
     * @return An array containing the String representations of the given
     * cards.
     */
    private String[] convertHand(Card[] cards) {
        String[] hand = new String[cards.length];
        for (int i = 0; i < cards.length; i++) {
            hand[i] = cards[i].toString();
        }
        return hand;
    }

    /**
     * Display the results of the simulation.
     */
    public void printResults() {
        System.out.println("\nResults for " + this.numberOfSimulations + " iterations:\n");
        for (int i : this.handCounts.keySet()) {
            if (i == 0) {
                System.out.print("High card: ");
            }
            else if (i == 1) {
                System.out.print("Pair: ");
            }
            else if (i == 2) {
                System.out.print("Two pair: ");
            }
            else if (i == 3) {
                System.out.print("Three of a kind: ");
            }
            else if (i == 4) {
                System.out.print("Straight: ");
            }
            else if (i == 5) {
                System.out.print("Flush: ");
            }
            else if (i == 6) {
                System.out.print("Full house: ");
            }
            else if (i == 7) {
                System.out.print("Four of a kind: ");
            }
            else if (i == 8) {
                System.out.print("Straight flush: ");
            }
            else {
                System.out.print("Royal flush: ");
            }
            int amount = this.handCounts.get(i);
            double ratio = 1.0 * amount / this.numberOfSimulations;
            System.out.println(amount + " (" + ratio * 100 + " %)");
        }

    }

}
