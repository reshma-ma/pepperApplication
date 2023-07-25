package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


import android.widget.ImageView;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Say;


public class zumbaDance extends RobotActivity implements RobotLifecycleCallbacks {
//public class zumbaDance extends AppCompatActivity {


    private QiContext qiContext;
    public boolean animationCompleted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zumba_dance2);
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

        danceZumba();


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



    private void danceZumba() {
        Log.i("RoboticActivity","enter into danceZumba function entry");
        if (qiContext == null) {
            Log.i("RoboticActivity","qicontext is null");
            return;
        }

//                if(play==null){
//            play =MediaPlayer.create(this,R.raw.song);
//            play.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    Log.i("RoboticActivity","music end");
//                }
//            });
//        }
//        play.start();








        new Thread(() -> {
            Log.i("RoboticActivity","enter into danceZumba function thread");

            // Creating a Say action to make Pepper say
            Say sayGreeting = SayBuilder.with(qiContext)
                    .withText("play music!")
                    .build();

            // Animation action for greeting movements
            Animation greetAnimation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.headraise)  //  testing with  animation resource
                    .build();
            Animate animate = AnimateBuilder.with(qiContext)
                    .withAnimation(greetAnimation)
                    .build();
            // Run the Say action and Animation action concurrently
            sayGreeting.run();

//            animate.run();
            Future<Void> animationFuture=animate.async().run();
            animationFuture.thenConsume(stringFuture -> {
                if (stringFuture.isSuccess()) {
                    // Handle success state.
                    // Access the value with stringFuture.get().
                    animationCompleted=true;
                } else {

                    Log.i("RoboticActivity","future in sayFuture error");
                    // Handle error state.
                    // Access the error with stringFuture.getError().
                }
            });

            if(animationCompleted){

                Intent intent =new Intent(zumbaDance.this, feedbackActivity.class);
                startActivity(intent);

            }

            else{

                Log.i("RoboticAvtivity","error in future inside zumbadance activity");
                Intent intent =new Intent(zumbaDance.this, feedbackActivity.class);
                startActivity(intent);
            }


        }).start();

    }

}