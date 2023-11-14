package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        playerHand.displayHand(true);
        dealerHand.displayHand(false);

        while (playerHand.calculateScore() <= 21) {
            System.out.print("Do you want to hit or stand? (h/s): ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("h")) {
                playerHand.addCard(deck.drawCard());
                playerHand.displayHand(true);
            } else if (choice.equals("s")) {
                break;
            }
        }

        dealerHand.displayHand(true);
        while (dealerHand.calculateScore() < 17) {
            dealerHand.addCard(deck.drawCard());
            dealerHand.displayHand(true);
        }

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