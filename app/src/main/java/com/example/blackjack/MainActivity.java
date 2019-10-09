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
import java.util.Collections;
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
    ImageView playerLastCard;
    ImageView dealerLastCard;
    private BlackJackActions actions;
    private Button deal;
    private Button hit;
    private Button stand;
    private Deck blackjackDeck;
    private Player player1;
    private Player dealer;
    private Timer timer;
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
        timer = new Timer();
        playerCardImageArray = new ArrayList<>();
        dealerCardImageArray = new ArrayList<>();

        winLoss = (TextView) findViewById(R.id.gameWinCheck);
        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
        dealerCardImage1 = (ImageView) findViewById(R.id.dealerCardImage1);
        dealerCardImage2 = (ImageView) findViewById(R.id.dealerCardImage2);
        //playerCardImageArray.add(playerCardImage1);
        //playerCardImageArray.add(playerCardImage2);
        //dealerCardImageArray.add(dealerCardImage1);
        //dealerCardImageArray.add(dealerCardImage2);
        deal = (Button) findViewById(R.id.deal);
        hit = (Button) findViewById(R.id.hit);
        stand = (Button) findViewById(R.id.standButton);

        //playerLastCard = findViewById(R.id.playerCardImage2);
        //dealerLastCard = findViewById(R.id.dealerCardImage2);

    }

    public void dealButtonClick(View view)
    {
        deal.setVisibility(View.INVISIBLE);
        hit.setVisibility(View.VISIBLE);
        stand.setVisibility(View.VISIBLE);
        actions.deal(player1, dealer);
        dealer.getHand().get(1).turnFaceDown();
        playerCardImageArray.add(playerCardImage1);
        playerCardImageArray.add(playerCardImage2);
        dealerCardImageArray.add(dealerCardImage1);
        dealerCardImageArray.add(dealerCardImage2);
        playerLastCard = findViewById(R.id.playerCardImage2);
        dealerLastCard = findViewById(R.id.dealerCardImage2);
        for(int i = 0; i < 2; i++)
        {
            displayCard(playerCardImageArray.get(i), player1.getHand().get(i));
            displayCard(dealerCardImageArray.get(i), dealer.getHand().get(i));
        }
    }

    public void hitButtonClick(View view)
    {
        actions.hit(player1);
        final ConstraintLayout layout = findViewById(R.id.myLayout);
        moveNewCard(layout, playerLastCard, player1);
        if(player1.calculateBlackjackHandValue() > 21)
        {
            gameConditions();
        }

    }

    public void standButton(View view)
    {
        actions.stand(player1);
        dealer.getHand().get(1).turnFaceUp();
        displayCard(dealerCardImage2, dealer.getHand().get(1));
        if(dealer.calculateBlackjackHandValue() < 17)
        {
            actions.hit(dealer);
            final ConstraintLayout layout = findViewById(R.id.myLayout);
            moveNewCard(layout, dealerLastCard, dealer);

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
            clearTable();
        }
        else if(playerHandValue < 21 && dealerHandValue > 21)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.dealer_bust);
            clearTable();
        }
        else if(playerHandValue < 21 && playerHandValue == dealerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.push);
            clearTable();
        }
        else if(playerHandValue < 21 && dealerHandValue > playerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.dealer_won);
            clearTable();
        }
        else
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.player_won);
            clearTable();
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

        for(ImageView imageView : playerCardImageArray) {
            imageView.setVisibility(View.INVISIBLE);
        }
        for(ImageView imageView : dealerCardImageArray) {
            imageView.setVisibility(View.INVISIBLE);
        }

        playerCardImageArray.clear();
        dealerCardImageArray.clear();
    }

    private void moveNewCard(ConstraintLayout layout, ImageView lastCard, Player player)
    {
        ImageView image;
        if(player == player1)
        {
            image = createNewCard(playerCardImageArray);
        }
        else
        {
            image = createNewCard(dealerCardImageArray);
        }
        final float currentX = lastCard.getX();
        final float currentY = lastCard.getY();
        final int currentHeight = lastCard.getHeight();
        final int currentWidth = lastCard.getWidth();
        final float separation = 160;

        image.requestLayout();

        image.setX(currentX + separation);
        image.setY(currentY);

        image.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        image.setLayoutParams(new LinearLayout.LayoutParams(currentWidth, currentHeight));
        displayCard(image, player1.getHand().getCards().get(playerCardImageArray.size() - 1));
        layout.addView(image);
        if(player == player1)
        {
            playerLastCard = image;
        }
        else
        {
            dealerLastCard = image;
        }
    }

    public ImageView createNewCard(List<ImageView> arrayToBeAdded)
    {
        final ImageView newCard = new ImageView(this);
        arrayToBeAdded.add(newCard);
        return newCard;
    }
}
