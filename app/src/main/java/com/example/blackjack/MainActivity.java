package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView playerCardTextView;
    private ImageView playerCardImage1;
    private ImageView playerCardImage2;
    private ImageView playerCardImage3;
    private ImageView dealerCardImage1;
    private ImageView dealerCardImage2;
    Card playerCard1;
    Card playerCard2;
    Card dealerCard1;
    Card dealerCard2;
    private BlackJackActions actions;
    private Button deal;
    private Button hit;
    private Button stand;
    private Deck blackjackDeck;
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
        dealerActions = new DealerController(dealer,actions);

        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
        playerCardImage3 = (ImageView) findViewById(R.id.playerCardImage3);
        dealerCardImage1 = (ImageView) findViewById(R.id.dealerCardImage1);
        dealerCardImage2 = (ImageView) findViewById(R.id.dealerCardImage2);
        deal = (Button) findViewById(R.id.deal);
        hit = (Button) findViewById(R.id.hit);
        stand = (Button) findViewById(R.id.standButton);
    }

    public void dealButtonClick(View view)
    {
        player1.drawCards(2);
        dealer.drawCards(2);
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
        playerCardImage2.setImageResource(playerCardImage2ID);
        dealerCardImage1.setImageResource(dealerCardImage1ID);
        dealerCardImage2.setImageResource(R.drawable.blue_back);
        playerCardImage1.setVisibility(View.VISIBLE);
        playerCardImage2.setVisibility(View.VISIBLE);
        dealerCardImage1.setVisibility(View.VISIBLE);
        dealerCardImage2.setVisibility(View.VISIBLE); */
    }

    public void hitButtonClick(View view)
    {
        Card playerCard3 = blackjackDeck.dealCard();
        int playerCardImage3ID = getResources().getIdentifier(playerCard3.toString(), "drawable", "com.example.blackjack");
        playerCardImage3.setImageResource(playerCardImage3ID);
        playerCardImage3.setVisibility(View.VISIBLE);
    }

    public void standButton(View view)
    {
        player1.playerStand();
        dealer.getHand().get(1).turnFaceup();
        displayCard(dealerCardImage2, dealer.getHand().get(1));
        if(dealer.calculateBlackjackHandValue() < 17)
        {
            dealer.drawCards(1);
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

    }
}
