package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.aldebaran.qi.Future;
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


public class feedbackActivity extends RobotActivity implements RobotLifecycleCallbacks {
//public class feedbackActivity extends AppCompatActivity {

    private QiContext qiContext;
    public boolean speakingCompleted;
    public boolean animationCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        QiSDK.register(this, this);


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

        feedback();



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


    private void feedback() {
        Log.i("RoboticActivity","enter into feedback function entry");
        if (qiContext == null) {
            Log.i("RoboticActivity","qicontext is null");
            return;
        }

        new Thread(() -> {
            Log.i("RoboticActivity","enter into feedback function thread");

            // Creating a Say action to make Pepper say
            Say sayGreeting = SayBuilder.with(qiContext)
                    .withText("Wow!, That was amazing.you got three stars in beginer level. We have to practice more")
                    .build();

            // Animation action for greeting movements
            Animation greetAnimation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.welcome)  //  testing with clapping animation resource
                    .build();
            Animate animate = AnimateBuilder.with(qiContext)
                    .withAnimation(greetAnimation)
                    .build();
            // Run the Say action and Animation action concurrently
//            sayGreeting.async().run();
//            animate.async().run();


            Future<Void> sayFuture = sayGreeting.async().run();
            Future<Void> animateFuture = animate.async().run();


            sayFuture.thenConsume(stringFuture -> {
                if (stringFuture.isSuccess()) {
                    // Handle success state.
                    // Access the value with stringFuture.get().
                    speakingCompleted=true;
                } else {

                    Log.i("RoboticActivity","future in sayFuture error");
                    // Handle error state.
                    // Access the error with stringFuture.getError().
                }
            });


            animateFuture.thenConsume(stringFuture -> {
                if (stringFuture.isSuccess()) {
                    // Handle success state.
                    // Access the value with stringFuture.get().
                    animationCompleted=true;
                } else {

                    Log.i("RoboticActivity","future in sayFuture error");
                    // Handle error state.
                    // Access the error with stringFuture.getError().
                    animationCompleted=false;
                }
            });



            if(animationCompleted && speakingCompleted){
                Intent intent = new Intent(feedbackActivity.this, RatingActivity.class);
                startActivity(intent);
            }
            else{
                Log.i("RoboticActivty","future not worked in feedback activity");
                Intent intent = new Intent(feedbackActivity.this, RatingActivity.class);
                startActivity(intent);
            }







//            Say sayQuestion = SayBuilder.with(qiContext)
//                    .withText("Do you want to continue?")
//                    .build();
//
//            // Animation action for greeting movements
//            Animation QuestionAnimation = AnimationBuilder.with(qiContext)
//                    .withResources(R.raw.question_right_hand_a001)
//                    .build();
//            Animate animateQuestion = AnimateBuilder.with(qiContext)
//                    .withAnimation(QuestionAnimation)
//                    .build();
//
//            sayQuestion.async().run();
//            animateQuestion.async().run();
//
//            // Create the PhraseSet yes.
//            PhraseSet phraseSetyes = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
//                    .withTexts("yes", "ya", "OK", "alright", "let's do this again") // Add the phrases Pepper will listen to.
//                    .build(); // Build the PhraseSet.
//
//            // Create the PhraseSet No.
//            PhraseSet phraseSetNo = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
//                    .withTexts("no", "Sorry", "I can't") // Add the phrases Pepper will listen to.
//                    .build(); // Build the PhraseSet.
//
//            // Create a new listen action.
//            Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
//                    .withPhraseSets(phraseSetyes, phraseSetNo) // Set the PhraseSets to listen to.
//                    .build();  // Build the listen action.
//
//            // Run the listen action and get the result.
//            ListenResult listenResult = listen.run();
//
//            // Identify the matched phrase set.
//            PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();
//            if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetyes)) {
//                Log.i("RoboticActivity ", "Heard phrase set: yes");
//                Intent intent = new Intent(feedbackActivity.this, MainActivity.class);
//                startActivity(intent);
//            } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetNo)) {
//
//                Log.i("RoboticActivity ", "Heard phrase set: No");
//                Say sayThankyou = SayBuilder.with(qiContext)
//                        .withText("Thank you for using the app.How you rate this app?")
//                        .build();
//
//                // Animation action for greeting movements
//                Animation ratingAnimation = AnimationBuilder.with(qiContext)
//                        .withResources(R.raw.question_right_hand_a001)
//                        .build();
//                Animate animateRating = AnimateBuilder.with(qiContext)
//                        .withAnimation(ratingAnimation)
//                        .build();
//
//                sayThankyou.async().run();
//                animateRating.async().run();

//                Intent intent = new Intent(feedbackActivity.this, RatingActivity.class);
//                startActivity(intent);


                
//            }


        }).start();

    }

}