package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        Hand playerHand = new Hand(true); // Indicates player's hand
        Hand dealerHand = new Hand(false); // Indicates dealer's hand

        // Initial deal
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        // Show initial hands
        playerHand.displayHand(true);
        dealerHand.displayHand(false);

        // Player's turn
        while (true) {
            System.out.print("Do you want to hit or stand? (h/s): ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("h")) {
                playerHand.addCard(deck.drawCard());
                playerHand.displayHand(true);
                if (playerHand.calculateScore() > 21) {
                    break;
                }
            } else if (choice.equals("s")) {
                break;
            }
        }

        // If the player hasn't busted, proceed to the dealer's turn
        if (playerHand.calculateScore() <= 21) {
            // Dealer's turn
            dealerHand.displayHand(true);
            while (dealerHand.calculateScore() < 17) {
                dealerHand.addCard(deck.drawCard());
                dealerHand.displayHand(true);
            }
        }

        // Display final hands and determine the winner
        playerHand.displayHand(true);
        dealerHand.displayHand(true);

        int playerScore = playerHand.calculateScore();
        int dealerScore = dealerHand.calculateScore();

        if (playerScore > 21) {
            System.out.println("Player busts! Dealer wins.");
        } else if (dealerScore > 21 || playerScore > dealerScore) {
            System.out.println("Player wins!");
        } else if (playerScore == dealerScore) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins!");
        }

        scanner.close();
    }
}