
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class HandReader {

    /* "2" = 2, ..."T" = 10, "J" = 11, "Q" = 12, "K" = 13, "A" = 14 */
    private HashMap<String, Integer> cardRankings;

    public HandReader() {
        //this.handRankings = new HashMap<String, Integer>();
        this.cardRankings = new HashMap<>();
        //this.arrayTohandRankings();
        this.arrayTocardRankings();
    }

    /* Map "2" to 2, ..., "T" to 10, "J" to 11, "Q" to 12, "K" to 13, "A" to 14. */
    private void arrayTocardRankings() {
        String[] cardRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "T",
            "J", "Q", "K", "A"};
        for (int i = 2; i <= 14; i++) {
            cardRankings.put(cardRanks[i - 2], i);
        }
    }

    /**
     * Determines the type of hand.
     *
     * @param cards A String array of length 5, with each index holding a
     * representation of a Card object.
     * @return An integer from 0-9 that represents the rank of the hand, e.g.
     * two pair, flush, etc.
     */
    public int handReader(String... cards) {
        if (this.isHighCard(cards)) {
            return 0;
        }
        else if (this.isPair(cards)) {
            return 1;
        }
        else if (this.isTwoPair(cards)) {
            return 2;
        }
        else if (this.is3OfAKind(cards)) {
            return 3;
        }
        else if (this.isStraight(cards) && !(this.isFlush(cards))) {

            return 4;
        }
        else if (this.isFlush(cards) && !(this.isStraight(cards))) {
            return 5;
        }
        else if (this.isFullHouse(cards)) {
            return 6;
        }
        else if (this.is4OfAKind(cards)) {
            return 7;
        }
        else if (this.isStraightFlush(cards)) {
            return 8;
        }
        else {
            return 9;
        }

    }

    /**
     * A helper method that converts a hand in array form to a sorted ArrayList
     * which holds the rank corresponding to each card as given by the map
     * cardRankings. For example a hand containing the cards 5C, KD, 3C, AH, QH
     * would be converted to an ArrayList containing the integers 3, 5, 12, 13,
     * 14, in that order. This is useful in other methods in determining various
     * properties of the hand.
     *
     * @param hand The five cards to be converted.
     * @return A hand sorted by the values of its constituent cards
     */
    private ArrayList<Integer> arrayToArrayList(String[] hand) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(this.cardRankings.get(hand[i].substring(0, 1)));
            Collections.sort(list);
        }
        return list;
    }

    /**
     * Checks if a hand is not a pair, not two pair, ..., not a straight flush,
     * i.e. "no pair", in poker parlance.
     *
     * @param cards The cards that comprise the hand.
     * @return True if the rank of the hand does not contain a pair or better.
     */
    public boolean isHighCard(String... cards) {
        if (!(this.isPair(cards) || this.isTwoPair(cards) || this.is3OfAKind(cards) || this.isStraight(cards)
                || this.isFlush(cards) || this.isFullHouse(cards) || this.is4OfAKind(cards)
                || this.isStraightFlush(cards))) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the hand contains exactly one pair.
     *
     * @param cards The hand to evaluate.
     * @return True if the hand contains exactly one pair, and false otherwise.
     */
    public boolean isPair(String... cards) {
        ArrayList<String> list = new ArrayList<>();

        /* Adds the rank of each card in the hand to a list. */
        for (String x : cards) {
            list.add(x.substring(0, 1));
        }

        /* One of the first four indices must contain one of the cards that 
        comprise the pair. However a full house and two pair also contain cards
        that have frequency 2, so these cases must be ruled out, which explains
        the && condition. */
        return (Collections.frequency(list, list.get(0)) == 2 || Collections.frequency(list, list.get(1)) == 2
                || Collections.frequency(list, list.get(2)) == 2 || Collections.frequency(list, list.get(3)) == 2)
                && !(this.isFullHouse(cards) || this.isTwoPair(cards));

    }

    /**
     * Checks if the hand contains two pair.
     *
     * @param cards The hand to evaluate.
     * @return True if the hand contains two pair, and false otherwise.
     */
    public boolean isTwoPair(String... cards) {
        ArrayList<String> list = new ArrayList<>();

        /* Put the ranks of the cards in a list and sort it. */
        for (String x : cards) {
            list.add(x.substring(0, 1));
        }
        Collections.sort(list);

        /* The bigger pair must have one card at index 3, 
        and the smaller pair must have one card at index 1; 
        if both of these are true, then the hand must be two pair. */
        return Collections.frequency(list, list.get(1)) == 2 && Collections.frequency(list, list.get(3)) == 2;
    }

    /**
     * Checks if a hand contains 3 of a kind.
     *
     * @param cards The hand to be evaluated.
     * @return True if the hand contains three of a kind, and false otherwise.
     */
    public boolean is3OfAKind(String... cards) {
        ArrayList<String> list = new ArrayList<>();
        for (String x : cards) {
            list.add(x.substring(0, 1));
        }
        Collections.sort(list);

        /* One of the first three indices must contain one of the cards that 
        comprise the 3 of a kind, and we also need to check that the hand is not a full house. */
        return (Collections.frequency(list, list.get(0)) == 3 || Collections.frequency(list, list.get(1)) == 3
                || Collections.frequency(list, list.get(2)) == 3) && !(this.isFullHouse(cards));
    }

    /**
     * Checks if the hand is a straight.
     *
     * @param cards The hand to be evaluated.
     * @return True if the hand contains a straight, and false otherwise.
     */
    public boolean isStraight(String... cards) {
        ArrayList<Integer> list = new ArrayList<>();

        /* Put the ranks of the cards in a list and sort it. */
        for (String x : cards) {
            list.add(this.cardRankings.get(x.substring(0, 1)));
        }
        Collections.sort(list);

        /* In a straight, the rank of each card must differ from its neightbor(s) by exactly 1. 
        If this fails for at least one pair of neighbors, the hand is not a straight. */
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) - list.get(i - 1) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the hand is a flush.
     *
     * @param cards The hand to be evaluated.
     * @return True if the hand contains a flush, and false otherwise.
     */
    public boolean isFlush(String... cards) {
        /* Get the suit of the first card. */
        String suit = cards[0].substring(1);

        for (int i = 1; i < cards.length; i++) {
            /* If any of the other cards have a suit different from the suit 
            of the first card, the hand is not a flush. */
            if (!suit.equals(cards[i].substring(1))) {
                return false;
            }
        }

        /* If all sucessive cards have the same suit as the first, the hand is a flush. */
        return true;
    }

    /**
     * Checks if the hand is a full house.
     *
     * @param cards The hand to be evaluated.
     * @return True if the hand contains a full house, and false otherwise.
     */
    public boolean isFullHouse(String... cards) {
        ArrayList<String> list = new ArrayList<>();
        for (String x : cards) {
            list.add(x.substring(0, 1));
        }
        Collections.sort(list);

        /* Since the list is sorted, if the hand is a full house the entries 
        in the list must be of the form XXYYY or YYYXX. If it is one of these, 
        it is a full house; otherwise it is not. */
        return ((Collections.frequency(list, list.get(1)) == 2) && (Collections.frequency(list, list.get(2)) == 3))
                || ((Collections.frequency(list, list.get(2)) == 3) && (Collections.frequency(list, list.get(3)) == 2));
    }

    /**
     * Checks if the hand contains 4 of a kind.
     *
     * @param cards The hand to be evaluated.
     * @return True if the hand contains 4 of a kind, and false otherwise.
     */
    public boolean is4OfAKind(String... cards) {
        ArrayList<String> list = new ArrayList<>();
        for (String x : cards) {
            list.add(x.substring(0, 1));
        }

        /* One of the first two indices must contain one of the cards that 
        comprise the 4 of a kind. If either of them have frequency 4 in the list, 
        the hand contains four of a kind; otherwise it does not. */
        return Collections.frequency(list, list.get(0)) == 4 || Collections.frequency(list, list.get(1)) == 4;
    }

    /**
     * Checks if the hand is a straight flush.
     *
     * @param cards The hand to be evaluated.
     * @return True if the hand contains a straight flush, and false otherwise.
     */
    public boolean isStraightFlush(String... cards) {
        ArrayList<Integer> temp = this.arrayToArrayList(cards);

        /* If the hand is a straight and it is a flush,  but not a royal flush,
        it is a straight flush. */
        return this.isFlush(cards) && this.isStraight(cards)
                && temp.get(4) != 14;
    }

    /**
     * Checks if the hand is a royal flush.
     *
     * @param cards The hand to be evaluated.
     * @return True if the hand contains a royal flush, and false otherwise.
     */
    public boolean isRoyalFlush(String... cards) {
        ArrayList<Integer> temp = this.arrayToArrayList(cards);

        /* Check that the highest rank in the hand is an ace, and that the hand
        is a straight flush. */
        return temp.get(4) == 14 && this.isStraightFlush();
    }
}
