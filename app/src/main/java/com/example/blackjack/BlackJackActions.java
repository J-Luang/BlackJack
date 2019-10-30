package com.example.blackjack;

import android.util.Log;
import android.widget.ImageView;

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

    public double doubleDown(double betAmount)
    {
        betAmount *= 2;
        return betAmount;
    }

    public double bet(double betAmount, double chip, boolean switchState, double amountUserChips)
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
}
