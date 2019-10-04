package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private TextView playerCardTextView;
    private ImageView playerCardImage1;
    private ImageView playerCardImage2;
    private ImageView dealerCardImage1;
    private ImageView dealerCardImage2;
    private ImageView lastCard;
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

        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
        dealerCardImage1 = (ImageView) findViewById(R.id.dealerCardImage1);
        dealerCardImage2 = (ImageView) findViewById(R.id.dealerCardImage2);
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
        player1.drawCards(dealtCards);
        dealer.drawCards(dealtCards);
        playerCards = player1.getHand().size();
        dealerCards = dealer.getHand().size();
        dealer.getHand().get(1).turnFaceDown();
        displayCard(playerCardImage1, player1.getHand().get(0));
        displayCard(playerCardImage2, player1.getHand().get(1));
        displayCard(dealerCardImage1, dealer.getHand().get(0));
        displayCard(dealerCardImage2, dealer.getHand().get(1));

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
        //Loop to move images?
        final ConstraintLayout layout = findViewById(R.id.myLayout);
        final ImageView newPlayerCardImage = new ImageView(this);
        int newPlayerCardImageID = getResources().getIdentifier(playerCard.toString(), "drawable", "com.example.blackjack");
        newPlayerCardImage.setImageResource(newPlayerCardImageID);
        newPlayerCardImage.setVisibility(View.VISIBLE);

        final float currentX = lastCard.getX();
        final float currentY = lastCard.getY();
        final int currentHeight = lastCard.getLayoutParams().height;
        final int currentWidth = lastCard.getLayoutParams().width;
        final float separation = 100;

        Log.i("position", "XPosition =" + playerCardImage2.getX());
        Log.i("position", "YPosition =" + playerCardImage2.getY());
        Log.i("position", "Height =" + currentHeight);
        Log.i("position", "Width =" + currentWidth);
        Log.i("position", "LastXPosition =" + currentX);
        Log.i("position", "LastYPosition =" + currentY);
        /*LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(currentWidth,currentHeight);
        newPlayerCardImage.setLayoutParams(cardParams);*/
        newPlayerCardImage.setX(currentX + separation);
        newPlayerCardImage.setY(currentY);

        newPlayerCardImage.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));

        layout.addView(newPlayerCardImage);

        lastCard = newPlayerCardImage;

        gameConditions();
    }

    public void standButton(View view)
    {
        stand.setVisibility(View.VISIBLE);
        player1.playerStand();
        dealer.getHand().get(1).turnFaceup();
        displayCard(dealerCardImage2, dealer.getHand().get(1));
        if(dealer.calculateBlackjackHandValue() == 21)
        {
            gameConditions();
        }
        else if(dealer.calculateBlackjackHandValue() < 17)
        {
            dealer.drawCards(1);
        }
        gameConditions();
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
        //Could also switch to case statements? also timer for the message
        if(player1.calculateBlackjackHandValue() > 21)
        {
            //Text for player has busted
            clearTable();
        }
        else if(playerHandValue < 21 && dealerHandValue > 21)
        {
            //Text dealer busted you win
            clearTable();
        }
        else if(playerHandValue < 21 && playerHandValue == dealerHandValue)
        {
            //Text draw or push
            clearTable();
        }
        else if(playerHandValue < 21 && dealerHandValue > playerHandValue)
        {
            // Text dealer has won
            clearTable();
        }
        else if (playerHandValue < 21 && playerHandValue > dealerHandValue)
        {
            //Text you won!
            clearTable();
        }
    }

    public void clearTable()
    {
        //Text image change to invisible
        playerCardImage1.setVisibility(View.INVISIBLE);
        playerCardImage2.setVisibility(View.INVISIBLE);
        dealerCardImage1.setVisibility(View.INVISIBLE);
        dealerCardImage2.setVisibility(View.INVISIBLE);
        hit.setVisibility(View.INVISIBLE);
        stand.setVisibility(View.INVISIBLE);
        deal.setVisibility(View.VISIBLE);
    }

    public void createNewCard(View view)
    {
        
    }
}
