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

    public void doubleDown()
    {

    }

    public int bet(int betAmount, int chip)
    {
        int[] betChipsArray = new int[]{R.id.whiteChip, R.id.redChip, R.id.blueChip, R.id.greenChip, R.id.blackChip};
        int[] betAmountArray = new int[]{1, 5, 10, 25, 100};
        for (int i = 0; i < betChipsArray.length; i += 1)
        {
            if(chip == betChipsArray[i])
            {
                    betAmount += betAmountArray[i];
            }
        }
        return betAmount;
        /*if(chip == whiteChip)
        {
            betAmount += betAmountArray[0];
        }
        else if(view == redChip)
        {
            betAmount += betAmountArray[1];
        }
        else if(view == blueChip)
        {
            betAmount += betAmountArray[2];
        }
        else if(view == greenChip)
        {
            betAmount += betAmountArray[3];
        }
        else if(view == blackChip)
        {
            betAmount +=betAmountArray[4];
        }
*/
    }

    public void split()
    {

    }
}
