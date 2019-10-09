package com.example.blackjack;
import java.util.*;

public class Testing
{
    public static void main(String[] args)
    {
        Card aceSpades = new Card(Card.Rank.Ace, Card.Suit.Spades);
        Card tenSpades = new Card(Card.Rank.Ten, Card.Suit.Spades);
        Card nineSpades = new Card(Card.Rank.Nine, Card.Suit.Spades);
        Card eightSpades = new Card(Card.Rank.Eight, Card.Suit.Spades);
        Card sevenSpades = new Card(Card.Rank.Seven, Card.Suit.Spades);
        Card sixSpades = new Card(Card.Rank.Six, Card.Suit.Spades);
        Card fiveSpades = new Card(Card.Rank.Five, Card.Suit.Spades);
        Card fourSpades = new Card(Card.Rank.Four, Card.Suit.Spades);
        Card threeSpades = new Card(Card.Rank.Three, Card.Suit.Spades);
        Card twoSpades = new Card(Card.Rank.Two, Card.Suit.Spades);


        Deck deck = new Deck();
        Player player = new Player(deck);

        List<Card> cards = new ArrayList<>();
        cards.add(aceSpades); cards.add(nineSpades); cards.add(aceSpades); //cards.add(aceSpades);
        player.hand.setHand(cards);

        System.out.println(player.getHand().getCards());
        System.out.println(player.calculateBlackjackHandValue());
    }
}
