package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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


public class beginnersmovesActivity extends RobotActivity implements RobotLifecycleCallbacks {



    MediaPlayer player;
    ImageView imageView;
    private QiContext qiContext;
    public boolean speakingCompleted=false;
    public boolean animationCompleted=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginnersmoves);
        //QiSDK.register(this, this);

//        Intent intent = getIntent();





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
        teachDance();





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





//    public void play(View v){
//        if(player==null){
//            player =MediaPlayer.create(this,R.raw.zumbasong);
//            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    stopPlayer();
//                }
//            });
//        }
//        player.start();
//
//
//    }

//    public void pause(View v){
//
//        if(player!=null){
//            player.pause();
//        }
//
//    }

//    public void stop(View v){
//        stopPlayer();
//    }

//    private void stopPlayer(){
//        if(player!=null){
//            player.release();
//            player=null;
//            Toast.makeText(this, "level completed", Toast.LENGTH_SHORT).show();
//            Intent intent =new Intent(beginnersmovesActivity.this, feedbackActivity.class);
//            startActivity(intent);
//        }
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        stopPlayer();
//    }

    private void teachDance() {
        Log.i("RoboticActivity","enter into teachDance function entry");
        if (qiContext == null) {
            Log.i("RoboticActivity","qicontext is null");
            return;
        }

        new Thread(() -> {
            Log.i("RoboticActivity","enter into teachDance function thread");

            // Creating a Say action to make Pepper say
            Say sayBegin = SayBuilder.with(qiContext)
                    .withText("Lets start beginner level!. I can show you steps by playing song.Every change comes from small begining.Repeat the steps with me and with the rhythm.")
                    .build();

            // Animation action for greeting movements
            Animation teachAnimation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.levelwelcome)  //  testing with clapping animation resource
                    .build();
            Animate animate = AnimateBuilder.with(qiContext)
                    .withAnimation(teachAnimation)
                    .build();
            // Run the Say action and Animation action concurrently
//            sayBegin.async().run();
            //            animate.run();

            Future<Void> sayFuture = sayBegin.async().run();
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
                Intent intent =new Intent(beginnersmovesActivity.this, zumbaDance.class);
                startActivity(intent);
            }
            else{
                Log.i("RoboticActivty","future not worked in beginners activity");
                Intent intent =new Intent(beginnersmovesActivity.this, zumbaDance.class);
                startActivity(intent);
            }







//            // Creating a Say action to make Pepper say
//            Say sayFirstStep = SayBuilder.with(qiContext)
//                    .withText("first head movement.")
//                    .build();
//
//            // Animation action for first step movements
//            Animation firstStep = AnimationBuilder.with(qiContext)
//                    .withResources(R.raw.firststep)
//                    .build();
//            Animate firstStepAnimate = AnimateBuilder.with(qiContext)
//                    .withAnimation(firstStep)
//                    .build();
//
//            // Run the Say action and Animation action concurrently
//            sayFirstStep.async().run();
//            firstStepAnimate.run();
//
//            // Creating a Say  second step action to make Pepper say
//            Say saySecondStep = SayBuilder.with(qiContext)
//                    .withText("second keep both legs in small distance then raise hands and put them down slowly like this")
//                    .build();
//
//            // Animation action for first step movements
//            Animation secondStep = AnimationBuilder.with(qiContext)
//                    .withResources(R.raw.secondstep)
//                    .build();
//            Animate secondStepAnimate = AnimateBuilder.with(qiContext)
//                    .withAnimation(secondStep)
//                    .build();
//
//            saySecondStep.async().run();
//            secondStepAnimate.run();
//
//            // Creating a Say  second step action to make Pepper say
//            Say sayRepeatStep = SayBuilder.with(qiContext)
//                    .withText("We have some other interesting steps also. Lets play music and repeat steps with me.Lets move!")
//                    .build();
//
//            // Animation action for first step movements
//            Animation repeatStep = AnimationBuilder.with(qiContext)
//                    .withResources(R.raw.introduction)
//                    .build();
//            Animate repeatStepAnimate = AnimateBuilder.with(qiContext)
//                    .withAnimation(repeatStep)
//                    .build();
//
//            sayRepeatStep.async().run();
//            repeatStepAnimate.async().run();



        }).start();

    }





}

















