package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.aldebaran.qi.sdk.object.conversation.QiChatExecutor;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.holder.AutonomousAbilitiesType;
import com.aldebaran.qi.sdk.object.holder.Holder;

import android.widget.ImageView;

import android.os.Bundle;

import java.text.CollationElementIterator;

public class splashscreen extends RobotActivity implements RobotLifecycleCallbacks {
//public class splashscreen extends AppCompatActivity {


    Animation up,down;
    ImageView imageView;
    TextView textView;

    private QiContext qiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        QiSDK.register(this, this);
        setContentView(R.layout.activity_splashscreen);


        QiSDK.register(this, this);

        ImageView imageView= findViewById(R.id.splashApp);
        up= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up);
        imageView.setAnimation(up);


        TextView textView= findViewById(R.id.appname);
        down=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down);
        textView.setAnimation(down);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                welcomeToZumba();
                ZumbaIntroduction();
                Intent intent =new Intent(splashscreen.this, MainActivity.class);
                startActivity(intent);


                finish();

            }
        },8000);


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

        // Update the TextView to notify that the Say action is done.
        /*runOnUiThread(() -> {
            Intent intent = new Intent(PepperActivity.this, MainActivity.class);
            startActivity(intent);
        });*/

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

    private void welcomeToZumba() {
        if (qiContext == null) {
            return;
        }

        new Thread(() -> {
            Log.i("RoboticActivity","enter into welcomeToZumba function");

            // Creating a Say action to make Pepper say
            Say sayWelcome = SayBuilder.with(qiContext)
                    .withText("Welcome to Zumba dance,Lets Move!")
                    .build();

            // Animation action for greeting movements
            com.aldebaran.qi.sdk.object.actuation.Animation greetAnimation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.welcome)  //    animation resource
                    .build();
            Animate animate = AnimateBuilder.with(qiContext)
                    .withAnimation(greetAnimation)
                    .build();
            // Run the Say action and Animation action concurrently
            sayWelcome.async().run();
            animate.async().run();

        }).start();

    }


    private void ZumbaIntroduction() {
        if (qiContext == null) {
            return;
        }

        new Thread(() -> {
            Log.i("RoboticActivity","enter into welcomeToZumba function");

            // Creating a Say action to make Pepper say
            Say sayIntroduction = SayBuilder.with(qiContext)
                    .withText("do you know what is zumba?.Zumba is a dance fitness program. It is a combination of exercise and dance.Zumba have several benefits like, it promote physical activity, social interaction, and overall well-being among people")
                    .build();

            // Animation action for greeting movements
            com.aldebaran.qi.sdk.object.actuation.Animation greetAnimation = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.introduction)  //    animation resource
                    .build();
            Animate animate = AnimateBuilder.with(qiContext)
                    .withAnimation(greetAnimation)
                    .build();
            // Run the Say action and Animation action concurrently
            sayIntroduction.async().run();
            animate.async().run();

        }).start();

    }








}













