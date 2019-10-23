package com.example.blackjack;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

// A lot of the code in this class based on https://stackoverflow.com/questions/22115021/android-create-imageview-outside-mainactivity
public class CardImage
{
    ConstraintLayout Layout;
    Activity activity;
    private Card card;
    private ImageView imageView;
    private static int HEIGHT; // Height and width are not final, but are constant after they are
    private static int WIDTH; // set based on the screen size
    boolean faceUp;

    public CardImage(Activity activity)
    {
        this.activity = activity;

        // This code sets the height and width of each card based on screen size.
        // Taken from https://stackoverflow.com/questions/12780384/is-it-safe-to-use-getwidth-on-display-even-though-its-deprecated
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        // Values were calculated using previous card dimensions of 427 x 298 and a screen size of 1440 x 2392
        HEIGHT = (int) ((double) displaymetrics.heightPixels / 3.372365);
        WIDTH = (int) ((double) displaymetrics.widthPixels / 8.026846);
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
