package com.example.blackjack;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CardImage
{
    ConstraintLayout Layout;
    Activity activity;
    private Card card;
    private ImageView imageView;
    private final static int HEIGHT = 427;
    private final static int WIDTH = 298;
    boolean faceUp;

    public CardImage(Activity activity)
    {
        this.activity = activity;
    }

    public void create(int x, int y, Card card, ConstraintLayout layout)
    {
        Layout = layout;
        imageView = new ImageView(activity);
        this.card = card;
        imageView.setX(x);
        imageView.setY(y);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        params.height = HEIGHT;
        params.width = WIDTH;
        imageView.setLayoutParams(params);
        Layout.addView(imageView);
        faceUp = true;
    }

    public void move(float x, float y)
    {
        imageView.setX(x);
        imageView.setY(y);
    }

    public void display()
    {
        int cardImageID = activity.getResources().getIdentifier(card.toString(), "drawable", "com.example.blackjack");
        if(card.getFaceUp()) imageView.setImageResource(cardImageID);
        else imageView.setImageResource(R.drawable.blue_back);
        imageView.setVisibility(View.VISIBLE);
    }

    public void remove()
    {
        Layout.removeView(imageView);
    }

    public void flip()
    {
        faceUp = !faceUp;
    }
}
