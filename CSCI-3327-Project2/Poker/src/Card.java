
/**
 * A class that represents a playing card from a standard 52-card deck.
 */
public class Card {

    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return this.rank;
    }

    public String getSuit() {
        return this.suit;
    }

    /**
     * Generate a convenient and human-readable representation of a Card object,
     * e.g. AS (ace of spades), 3H (three of hearts), etc.
     *
     * @return A String representation of a Card object.
     */
    @Override
    public String toString() {
        return this.rank + this.suit;
    }
}
