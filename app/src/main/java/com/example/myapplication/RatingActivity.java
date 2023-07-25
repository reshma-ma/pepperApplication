package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;

import com.aldebaran.qi.sdk.QiSDK;

import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;

import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.HolderBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;

import com.aldebaran.qi.sdk.design.activity.RobotActivity;

import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.QiChatExecutor;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.holder.AutonomousAbilitiesType;
import com.aldebaran.qi.sdk.object.holder.Holder;
import com.aldebaran.qi.sdk.object.humanawareness.HumanAwareness;



import android.widget.ImageView;

import android.os.Bundle;

import java.text.CollationElementIterator;
import java.util.concurrent.ExecutionException;


public class RatingActivity extends RobotActivity implements RobotLifecycleCallbacks {


    private QiContext qiContext;
    public boolean animationCompleted;
    public boolean speakingCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QiSDK.register(this, this);
        setContentView(R.layout.activity_rating);
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.
        Log.i("RoboticActivity", "Robot focus gained");
        this.qiContext = qiContext;
        rating();

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



    private void rating() {

        Log.i("RoboticActivity","enter into rating function entry");
        if (qiContext == null) {
            Log.i("RoboticActivity","qicontext is null");
        }

        new Thread(() -> {
            Log.i("RoboticActivity","enter into greetings function thread");


            Say sayThankyou = SayBuilder.with(qiContext)
                        .withText("Thank you for using the app.How you rate this app?")
                        .build();

                // Animation action for greeting movements
                Animation ratingAnimation = AnimationBuilder.with(qiContext)
                        .withResources(R.raw.question_right_hand_a001)
                        .build();
                Animate animateRating = AnimateBuilder.with(qiContext)
                        .withAnimation(ratingAnimation)
                        .build();

//                sayThankyou.async().run();
//                animateRating.async().run();


            // Run the say action asynchronously and get the Future
            Future<Void> sayFuture = sayThankyou.async().run();
            Future<Void> animationFuture=animateRating.async().run();
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

            animationFuture.thenConsume(stringFuture -> {
                if (stringFuture.isSuccess()) {
                    // Handle success state.
                    // Access the value with stringFuture.get().
                    animationCompleted=true;
                } else {

                    Log.i("RoboticActivity","future in animationFuture error");
                    // Handle error state.
                    // Access the error with stringFuture.getError().
                }
            });


            if(speakingCompleted && animationCompleted){

                Intent intent =new Intent(RatingActivity.this, MainActivity.class);
                startActivity(intent);

            }
            else{
                Log.i("RoboticActivity","error in future inside pepperActivity");
            }





//            Intent intent =new Intent(PepperActivity.this, splashscreen.class);
//            startActivity(intent);

        }).start();



    }
}