package com.example.blackjack;

public class Testing
{
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player player1 = new Player(deck);
        Player player2 = new Player(deck);
        player1.drawCards(26);
        player2.drawCards(26);
        System.out.println(player1.getHand());
        System.out.println(player2.getHand());
        System.out.println();
        player2.discardHand();
        System.out.println(player1.getHand());
        System.out.println(player2.getHand());
        System.out.println();
        player2.drawCards(20);
        System.out.println(player1.getHand());
        System.out.println(player2.getHand());
        
    }
}
