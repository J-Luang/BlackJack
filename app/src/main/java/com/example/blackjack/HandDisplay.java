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
    private int lastCardX;
    private int y;
    private final float OFFSET = 160;

    HandDisplay(Activity activity)
    {
        this.activity = activity;
    }

    public void create(int x, int y, Hand hand, ConstraintLayout layout)
    {
        Layout = layout;
        this.hand = hand;
        cardImages = new ArrayList<>();
        lastCardX = x;
        this.y = y;
    }

    // Checks for new cards in hand and creates a cardImage to add to cardImages
    private void addNewCardImages()
    {
        for(int cardIndex = 0; cardIndex < hand.size(); cardIndex++)
        {
            if(cardIndex >= cardImages.size())
            {
                CardImage cardImage = new CardImage(activity);
                cardImage.create(lastCardX, y, hand.get(cardIndex), Layout);
                cardImages.add(cardImage);
                lastCardX += OFFSET;
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
