package com.example.blackjack;
import java.util.*;

public class Testing
{
    public static void main(String[] args)
    {
        Deck deck = new Deck();
        Player player = new Player(deck);
        Player dealer = new Player(deck);
        BlackJackActions actions = new BlackJackActions(deck, player, dealer);

        List<Card> cards = new ArrayList<>();
    }
}
