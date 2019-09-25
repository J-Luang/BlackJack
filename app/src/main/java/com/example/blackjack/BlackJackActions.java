package com.example.blackjack;

public class BlackJackActions
{
    Deck deck;
    Player player;
    Player dealer;

    BlackJackActions(Deck deck, Player player, Player dealer)
    {
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
    }

    public void stand()
    {
        player.playerStand();
    }

    public void hit()
    {

        player.checkIfPlayerBusts();
    }

    public void doubleDown()
    {

    }

    public void split()
    {

    }
}
