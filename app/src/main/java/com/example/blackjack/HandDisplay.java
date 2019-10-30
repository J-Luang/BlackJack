package com.example.blackjack;

import android.app.Activity;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;
import java.util.ArrayList;

public class HandDisplay
{
    ConstraintLayout Layout;
    Activity activity;
    private Hand hand;
    private List<CardImage> cardImages;
    private double lastCardXPercent;
    private double yPercent;

    HandDisplay(Activity activity)
    {
        this.activity = activity;
    }

    public void create(double xPercent, double yPercent, Hand hand, ConstraintLayout layout)
    {
        Layout = layout;
        this.hand = hand;
        cardImages = new ArrayList<>();
        lastCardXPercent = xPercent;
        this.yPercent = yPercent;
    }

    // Checks for new cards in hand and creates a cardImage to add to cardImages
    private void addNewCardImages()
    {
        for(int cardIndex = 0; cardIndex < hand.size(); cardIndex++)
        {
            if(cardIndex >= cardImages.size())
            {
                CardImage cardImage = new CardImage(activity);
                cardImage.create(lastCardXPercent, yPercent, hand.get(cardIndex), Layout);
                cardImages.add(cardImage);
                lastCardXPercent += cardImage.getOFFSET();
            }
        }
    }

    public void display()
    {
        addNewCardImages();
        for (CardImage cardImage : cardImages)
        {
            cardImage.display();
        }
    }

    public void clear()
    {
        for (CardImage cardImage : cardImages)
        {
            cardImage.remove();
        }
    }

    public void flipSecondCard()
    {
        if(cardImages.size() >= 2)
        {
            cardImages.get(1).flip();
        }
    }

}
