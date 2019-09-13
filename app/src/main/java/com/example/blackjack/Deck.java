package com.example.blackjack;

public class Deck
{
    private Card[] cards;
    private Suit[] suits = Suit.values();

    public Deck(){
        cards = new Card[52];
        for(int i=0;i<cards.length;i++)
        {
            cards[i] = makeCards(i);
        }
    }

    public Deck(Card[] cardArray){
        cards = cardArray;
    }

    private Card makeCards(int value)
    {
        int cardValues = value%13 + 1;
        Suit cardSuit = suits[value%4];
        return new Card(cardSuit, cardValues);
    }
}
