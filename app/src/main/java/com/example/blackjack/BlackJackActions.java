package com.example.blackjack;

import android.util.Log;
import android.widget.ImageView;

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

    public int bet(int betAmount, int chip, boolean switchState)
    {
        int[] betChipsArray = new int[]{R.id.whiteChip, R.id.redChip, R.id.blueChip, R.id.greenChip, R.id.blackChip};
        int[] betAmountArray = new int[]{1, 5, 10, 25, 100};
        for (int i = 0; i < betChipsArray.length; i += 1)
        {
            if(chip == betChipsArray[i])
            {
                if(!switchState)
                {
                    betAmount += betAmountArray[i];
                }
                else
                {
                    betAmount -= betAmountArray[i];
                }
            }
        }
        return betAmount;
    }

    public int gameConditions()
    {
        int playerHandValue = player.calculateBlackjackHandValue();
        int dealerHandValue = dealer.calculateBlackjackHandValue();
        if(player.calculateBlackjackHandValue() > blackJackValue)
        {
            return R.string.player_bust;

        }
        else if(playerHandValue < blackJackValue && dealerHandValue > blackJackValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            amountUserChips += betAmount * 2;
            winLoss.setText(R.string.dealer_bust);
            clearTableDelay();
        }
        else if(playerHandValue < blackJackValue && playerHandValue == dealerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            amountUserChips += betAmount;
            winLoss.setText(R.string.push);
            clearTableDelay();
        }
        else if(playerHandValue < blackJackValue && dealerHandValue > playerHandValue)
        {

            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.dealer_won);
            clearTableDelay();
        }
        else
        {
            winLoss.setVisibility(View.VISIBLE);
            amountUserChips += betAmount * 2;
            winLoss.setText(R.string.player_won);
            clearTableDelay();
        }
    }
}
