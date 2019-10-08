package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView playerCardTextView;
    private TextView winLoss;
    private ImageView playerCardImage1;
    private ImageView playerCardImage2;
    private ImageView dealerCardImage1;
    private ImageView dealerCardImage2;
    ImageView lastCard;
    private Card playerCard1;
    private Card playerCard2;
    private Card dealerCard1;
    private Card dealerCard2;
    private BlackJackActions actions;
    private Button deal;
    private Button hit;
    private Button stand;
    private Deck blackjackDeck;
    private int players;
    private int playerCards;
    private int dealerCards;
    private Player player1;
    private Player dealer;
    private DealerController dealerActions;
    private Timer timer;
    private List<ImageView> arrayPlayerCard;
    private List<ImageView> arrayDealerCard;
    private List<ImageView> playerCardImageArray;
    private List<ImageView> dealerCardImageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actions = new BlackJackActions(blackjackDeck, player1, dealer);
        blackjackDeck = new Deck(1);
        player1 = new Player(blackjackDeck);
        dealer = new Player(blackjackDeck);
        playerCards = 0;
        dealerCards = 0;
        dealerActions = new DealerController(dealer,actions);
        timer = new Timer();
        arrayPlayerCard = new ArrayList<>();
        playerCardImageArray = new ArrayList<>();
        dealerCardImageArray = new ArrayList<>();

        winLoss = (TextView) findViewById(R.id.gameWinCheck);
        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
        dealerCardImage1 = (ImageView) findViewById(R.id.dealerCardImage1);
        dealerCardImage2 = (ImageView) findViewById(R.id.dealerCardImage2);
        playerCardImageArray.add(playerCardImage1);
        playerCardImageArray.add(playerCardImage2);
        dealerCardImageArray.add(dealerCardImage1);
        dealerCardImageArray.add(dealerCardImage2);
        deal = (Button) findViewById(R.id.deal);
        hit = (Button) findViewById(R.id.hit);
        stand = (Button) findViewById(R.id.standButton);

        lastCard = findViewById(R.id.playerCardImage2);
    }

    public void dealButtonClick(View view)
    {
        int dealtCards = 2;
        deal.setVisibility(View.INVISIBLE);
        hit.setVisibility(View.VISIBLE);
        stand.setVisibility(View.VISIBLE);

        /*for(int i = 0; i < 2; i++)
        {
            createNewCard(playerCardImageArray);
            createNewCard(dealerCardImageArray);
        }

         */
        player1.drawCards(dealtCards);
        dealer.drawCards(dealtCards);
        playerCards = player1.getHand().size();
        dealerCards = dealer.getHand().size();
        dealer.getHand().get(1).turnFaceDown();
        for(int i = 0; i < dealtCards; i++)
        {
            displayCard(playerCardImageArray.get(i), player1.getHand().get(i));
            displayCard(dealerCardImageArray.get(i), dealer.getHand().get(i));
        }
/*      int playerCardImage1ID = getResources().getIdentifier(player1.getHand().get(0).toString(), "drawable", "com.example.blackjack");
        int playerCardImage2ID = getResources().getIdentifier(player1.getHand().get(1).toString(), "drawable", "com.example.blackjack");
        int dealerCardImage1ID = getResources().getIdentifier(dealer.getHand().get(0).toString(), "drawable", "com.example.blackjack");
        int dealerCardImage2ID = getResources().getIdentifier(dealer.getHand().get(1).toString(), "drawable", "com.example.blackjack");
        playerCardImage1.setImageResource(playerCardImage1ID);
        playerCardImage2.setImageResource(playerCardImage2ID);3
        dealerCardImage1.setImageResource(dealerCardImage1ID);
        dealerCardImage2.setImageResource(R.drawable.blue_back);
        playerCardImage1.setVisibility(View.VISIBLE);
        playerCardImage2.setVisibility(View.VISIBLE);
        dealerCardImage1.setVisibility(View.VISIBLE);
        dealerCardImage2.setVisibility(View.VISIBLE); */
    }

    public void hitButtonClick(View view)
    {
        hit.setVisibility(View.VISIBLE);
        playerCards += 1;
        Card playerCard = blackjackDeck.dealCard();
        final ConstraintLayout layout = findViewById(R.id.myLayout);
        //Loop to move images?
        moveNewCard(playerCard, layout);

        /*
        player1.drawCards(1);
        for(int i = 0; i < 1; i++)
        {
            displayCard(playerCardImageArray.get(playerCardImageArray.size() - 1), player1.getHand().get(arrayPlayerCard.size() - 1));
        }
        */
        gameConditions();


    }

    private void moveNewCard(Card playerCard, ConstraintLayout layout) {
        final ImageView newPlayerCardImage = createNewCard(playerCardImageArray);

        final float currentX = lastCard.getX();
        final float currentY = lastCard.getY();
        final int currentHeight = lastCard.getHeight();
        final int currentWidth = lastCard.getWidth();
        final float separation = 160;

        newPlayerCardImage.requestLayout();

        newPlayerCardImage.setX(currentX + separation);
        newPlayerCardImage.setY(currentY);

        newPlayerCardImage.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        newPlayerCardImage.setLayoutParams(new LinearLayout.LayoutParams(currentWidth, currentHeight));
        displayCard(newPlayerCardImage, playerCard);
        layout.addView(newPlayerCardImage);
        lastCard = newPlayerCardImage;
    }

    public void standButton(View view)
    {
        stand.setVisibility(View.VISIBLE);
        player1.playerStand();
        dealer.getHand().get(1).turnFaceup();
        displayCard(dealerCardImage2, dealer.getHand().get(1));
        if(dealer.calculateBlackjackHandValue() < 17)
        {
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    dealer.drawCards(1);
                }
            }, 1000);
        }
        else
        {
            gameConditions();
        }
    }

    public void displayCard(ImageView imageView, Card card)
    {
        int cardImageID = getResources().getIdentifier(card.toString(), "drawable", "com.example.blackjack");
        if(card.getFaceUp()) imageView.setImageResource(cardImageID);
        else imageView.setImageResource(R.drawable.blue_back);
        imageView.setVisibility(View.VISIBLE);
    }

    public void gameConditions()
    {
        int playerHandValue = player1.calculateBlackjackHandValue();
        int dealerHandValue = dealer.calculateBlackjackHandValue();
        if(player1.calculateBlackjackHandValue() > 21)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.player_bust);
            timer.schedule(new TimerTask()
            {
                public void run()
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            clearTable();
                        }
                    });
                }
            }, 1000);
        }
        else if(playerHandValue < 21 && dealerHandValue > 21)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.dealer_bust);
            timer.schedule(new TimerTask()
            {
                public void run()
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            clearTable();
                        }
                    });
                }
            }, 1000);
        }
        else if(playerHandValue < 21 && playerHandValue == dealerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.push);
            timer.schedule(new TimerTask()
            {
                public void run()
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            clearTable();
                        }
                    });
                }
            }, 1000);
        }
        else if(playerHandValue < 21 && dealerHandValue > playerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.dealer_won);
            timer.schedule(new TimerTask()
            {
                public void run()
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            clearTable();
                        }
                    });
                }
            }, 1000);
        }
        else if (playerHandValue < 21 && playerHandValue > dealerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.player_won);
            timer.schedule(new TimerTask()
            {
                public void run()
                {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            clearTable();
                        }
                    });
                }
            }, 1000);
        }
    }

    public void clearTable()
    {
        winLoss.setVisibility(View.INVISIBLE);
        playerCardImage1.setVisibility(View.INVISIBLE);
        playerCardImage2.setVisibility(View.INVISIBLE);
        dealerCardImage1.setVisibility(View.INVISIBLE);
        dealerCardImage2.setVisibility(View.INVISIBLE);
        hit.setVisibility(View.INVISIBLE);
        stand.setVisibility(View.INVISIBLE);
        deal.setVisibility(View.VISIBLE);
        player1.discardHand();
        dealer.discardHand();
    }

    public ImageView createNewCard(List<ImageView> arrayToBeAdded)
    {
        final ImageView newCard = new ImageView(this);
        //newCard.setAdjustViewBounds(true);
        arrayToBeAdded.add(newCard);
        return newCard;
    }
}
