package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient googleSignInClient;
    private LeaderboardsClient leaderboardClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setContentView(R.layout.activity_main);
        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            signIn();
        }



        Button startGameButton = (Button) findViewById(R.id.game_start_button);
        Button settingsButton = (Button) findViewById(R.id.settings_button);
        Button creditsButton = (Button) findViewById(R.id.credits_button);
        Button tutorialButton = (Button) findViewById(R.id.tutorial_button);
        Button leaderboardButton = (Button) findViewById(R.id.leaderboard_button);

        startGameButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GameActivity.class)));

        settingsButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        creditsButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CreditsActivity.class)));

        tutorialButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, TutorialActivity.class)));

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLeaderboard();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        signOut();
    }



    private void hideTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
    }

    public void signIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.silentSignIn().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                leaderboardClient = Games.getLeaderboardsClient(MainActivity.this, task.getResult());

            }
            else{
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 2);
            }
        });
    }

    public void signOut(){
        if(googleSignInClient != null){
            googleSignInClient.signOut();
            leaderboardClient = null;
        }
    }

    public void showLeaderboard(){
        if(leaderboardClient == null){
            String warning = getString(R.string.not_logged_in_error);
            AlertDialog.Builder alert = new AlertDialog.Builder(this).setMessage(warning)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            signIn();
                        }
                    });
            alert.show();

        }
        else{
            SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
            int submission = sharedPreferences.getInt("highScore", 0);
            leaderboardClient.submitScore(getString(R.string.leaderboard_high_score), submission);
            leaderboardClient
                    .getAllLeaderboardsIntent()
                    .addOnSuccessListener(intent -> startActivityForResult(intent, 1));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                GoogleSignInAccount signedInAccount = result.getSignInAccount();
                leaderboardClient = Games.getLeaderboardsClient(this, signedInAccount);
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.connection_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();
            }
        }
    }
}
