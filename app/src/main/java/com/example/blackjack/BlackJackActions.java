package com.example.blackjack;

public class BlackJackActions
{
    private Deck deck;
    private Player player;
    private Player dealer;

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
