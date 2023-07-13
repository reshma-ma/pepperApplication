package com.example.myapplication;


import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import com.aldebaran.qi.sdk.QiContext;

import com.aldebaran.qi.sdk.QiSDK;

import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;

import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;

import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;

import com.aldebaran.qi.sdk.design.activity.RobotActivity;

import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;

import com.aldebaran.qi.sdk.util.PhraseSetUtil;








public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {



    AppCompatButton btnbeginer,btncomplex,btnadvanced;
    private QiContext qiContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QiSDK.register(this, this);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        btnbeginer=findViewById(R.id.btnbeginner);
        btncomplex=findViewById(R.id.btncomplex);
        btnadvanced=findViewById(R.id.btnadvanced);


        btnbeginer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, beginnersmovesActivity.class);
                startActivity(intent);

            }
        });
        btncomplex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, complexmovesActivity.class);
                startActivity(intent);

            }
        });
        btnadvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, AdvancemovesActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.
        Log.i("RoboticActivity", "Robot focus gained");
        this.qiContext = qiContext;

        chooseLevel();

    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
        Log.i("RoboticActivity", "Robot focus lost");
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
        Log.i("RoboticActivity", "Robot focus refused: " + reason);
    }


    private void chooseLevel() {
        Log.i("RoboticActivity","enter into chooselevel function entry");
        if (qiContext == null) {
            Log.i("RoboticActivity","qicontext is null");
            return;
        }

        new Thread(() -> {
            Log.i("RoboticActivity","enter into choose level function thread");

            // Creating a Say action to make Pepper say
            Say sayGreeting = SayBuilder.with(qiContext)
                    .withText("I can show you how to do zumba dance. I have three levels for your interest. one beginner level,two complex level and three advance level. Which level do you prefer?")
                    .build();

            // Animation action for greeting movements
            Animation greetAnimation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.chooselevel)  //  testing with clapping animation resource
                    .build();
            Animate animate = AnimateBuilder.with(qiContext)
                    .withAnimation(greetAnimation)
                    .build();
            // Run the Say action and Animation action concurrently
            sayGreeting.async().run();
            animate.async().run();

            // Create the PhraseSet 1.
            PhraseSet phraseSetone = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                    .withTexts("beginner", "one", "first") // Add the phrases Pepper will listen to.
                    .build(); // Build the PhraseSet.

            // Create the PhraseSet 2.
            PhraseSet phraseSettwo = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                    .withTexts("complex", "two", "second") // Add the phrases Pepper will listen to.
                    .build(); // Build the PhraseSet.

            // Create the PhraseSet 3.
            PhraseSet phraseSetthree = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                    .withTexts("advance", "three", "third") // Add the phrases Pepper will listen to.
                    .build(); // Build the PhraseSet.


            // Create a new listen action.
            Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                    .withPhraseSets(phraseSetone, phraseSettwo,phraseSetthree) // Set the PhraseSets to listen to.
                    .build();  // Build the listen action.


            // Run the listen action and get the result.
            ListenResult listenResult = listen.run();

            // Identify the matched phrase set.
            PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();
            if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetone)) {
                Log.i("RoboticActivity ", "Heard phrase set: one");
                Intent intent =new Intent(MainActivity.this, beginnersmovesActivity.class);
                startActivity(intent);
            } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSettwo)) {
                Log.i("RoboticActivity ", "Heard phrase set: two");
                Intent intent =new Intent(MainActivity.this, complexmovesActivity.class);
                startActivity(intent);
            } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetthree)) {
                Log.i("RoboticActivity ", "Heard phrase set: three");
                Intent intent =new Intent(MainActivity.this, AdvancemovesActivity.class);
                startActivity(intent);
            }


        }).start();

    }



}
