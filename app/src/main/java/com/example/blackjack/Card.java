/*
Created by Payton McBurney
Created on 09/26/19
 */

package com.example.blackjack;

import androidx.annotation.NonNull;

public class Card
{
    // The possible card suits in a standard deck of 52 playing cards
    public enum Suit
    {
        /*
        The string representation of each suit is the first letter of the suit because
        we use these strings to assign each card its corresponding image. For instance,
        the seven of clubs is the image c7.png
         */
        Clubs("c"), Diamonds("d"), Hearts("h"), Spades("s");

        private String suitString;

        Suit(String suit)
        {
            suitString = suit;
        }

        @NonNull
        @Override
        public String toString()
        {
            return suitString;
        }
    }

    // The possible card ranks in a standard deck of 52 playing cards
    public enum Rank
    {
        /*
        The string representation of each rank is either the integer or the first letter of the
        rank because we use these strings to assign each card its corresponding image. For
        instance, the ace of spades is the image sa.png
         */
        Ace("a"), Two("2"), Three("3"), Four("4"), Five("5"), Six("6"), Seven("7"),
        Eight("8"), Nine("9"), Ten("10"), Jack("j"), Queen("q"), King("k");

        private String rankString;

        Rank(String rank)
        {
            rankString = rank;
        }

        @NonNull
        @Override
        public String toString()
        {
            return rankString;
        }

    }

    private final Rank rank;
    private final Suit suit;
    private static final int ACE_SOFT_VALUE = 11;
    private static final int ACE_HARD_VALUE = 1;
    private static final int FACE_CARD_VALUE = 10;

    Card(Rank rank, Suit suit)
    {
        this.suit = suit;
        this.rank = rank;
    }

    // Returns the soft value of the card, as determined by card rank
    public int getSoftValue()
    {
        if(isAce()) return ACE_SOFT_VALUE;
        else return getNonAceValue();
    }

    // Returns the hard value of the card, as determined by card rank
    public int getHardValue()
    {
        if(isAce()) return ACE_HARD_VALUE;
        else return getNonAceValue();
    }

    // Returns the value of a non-ace card based on its rank
    private int getNonAceValue()
    {
        switch (this.rank)
        {
            case Jack:
            case Queen:
            case King:
                return FACE_CARD_VALUE;
            default:
                return Integer.parseInt(this.rank.toString());
        }
    }

    public boolean isAce()
    {
        return this.rank == Rank.Ace;
    }

    @NonNull
    @Override
    public String toString()
    {
        String cardString = this.suit.toString() + this.rank.toString();
        return cardString;
    }
}