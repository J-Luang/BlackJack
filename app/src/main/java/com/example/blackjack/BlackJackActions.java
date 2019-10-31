package com.example.blackjack;

import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;

public class BlackJackActions
{
    private Deck deck;
    private Player player;
    private Player dealer;
    private int blackJackValue;

    BlackJackActions(Deck deck, Player player, Player dealer)
    {
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
        blackJackValue = 21;
    }

    public void deal(Player player1, Player dealer)
    {
        player1.drawCards(2);
        dealer.drawCards(2);
    }

    public void stand(Player player)
    {
        player.playerStand();
    }

    public void hit(Player player)
    {
        player.drawCards(1);
    }

    public int doubleDown(int betAmount)
    {
        betAmount *= 2;
        return betAmount;
    }

    public int bet(int betAmount, int chip, boolean switchState, int amountUserChips)
    {
        int[] betChipsArray = new int[]{R.id.whiteChip, R.id.redChip, R.id.blueChip, R.id.greenChip, R.id.blackChip};
        int[] betAmountArray = new int[]{1, 5, 10, 25, 100};
        for (int i = 0; i < betChipsArray.length; i += 1)
        {
            if(chip == betChipsArray[i])
            {
                if(!switchState && (betAmount + betAmountArray[i]) <= amountUserChips)
                {
                    betAmount += betAmountArray[i];
                }
                else if(switchState)
                {
                    if ((betAmount - betAmountArray[i]) >= 0)
                    betAmount -= betAmountArray[i];
                    else betAmount = 0;
                }
            }
        }
        return betAmount;
    }

    public int[] gameConditions(int amountBet, Player player, Player dealer)
    {
        int playerHandValue = player.calculateBlackjackHandValue();
        int dealerHandValue = dealer.calculateBlackjackHandValue();
        int[] array = new int[3];
        if(player.calculateBlackjackHandValue() > blackJackValue)
        {
            array[0] = R.string.player_bust;
            //color
            array[1] = R.color.colorAccent;
            //win amount
        }
        else if(playerHandValue < blackJackValue && dealerHandValue > blackJackValue)
        {
            array[0] = R.string.dealer_bust;
            array[1] = R.color.winColor;
            array[2] = amountBet * 2;
        }
        else if(playerHandValue < blackJackValue && playerHandValue == dealerHandValue)
        {
            array[0] = R.string.push;
            array[1] = R.color.tieColor;
            array[2] = amountBet;

        }
        else if(playerHandValue < blackJackValue && dealerHandValue > playerHandValue)
        {
            array[0] = R.string.dealer_won;
            array[1] = R.color.colorAccent;
        }
        else
        {
            array[0] = R.string.player_won;
            array[1] = R.color.winColor;
            array[2] = amountBet * 2;
        }
        return array;
    }
}
