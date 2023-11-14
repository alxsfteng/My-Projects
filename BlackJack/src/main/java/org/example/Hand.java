package org.example;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private ArrayList<Card> cards;
    private boolean isPlayer;

    public Hand(boolean isPlayer) {
        cards = new ArrayList<>();
        this.isPlayer = isPlayer;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int calculateScore() {
        int score = 0;
        int numAces = 0;

        for (Card card : cards) {
            score += card.getValue();
            if (card.getRank().equals("A")) {
                numAces++;
            }
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    public void displayHand(boolean showAllCards) {
        if (isPlayer) {
            System.out.println("Your hand:");
        } else {
            System.out.println("Dealer's hand:");
        }

        for (int i = 0; i < cards.size(); i++) {
            if (i == 0 && !showAllCards && !isPlayer) {
                System.out.println("[Hidden Card]");
            } else {
                System.out.println(cards.get(i));
            }
        }

        if (isPlayer || showAllCards) {
            System.out.println("Total score: " + calculateScore());
        } else {
            System.out.println("Total score: ?");
        }
    }
}