package com.example.groupproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameOverFragment extends Fragment{
    private int currentScore;
    int highScore;

    public GameOverFragment() {
        // Required empty public constructor
    }

    public GameOverFragment(int currentScore, int highScore){
        this.currentScore = currentScore;
        this.highScore = highScore;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gameOverView = inflater.inflate(R.layout.fragment_game_over, container, false);
        //create the replay and back to meny buttons
        Button replayButton = gameOverView.findViewById(R.id.game_replay_button);
        Button menuButton = gameOverView.findViewById(R.id.menu_button);
        //create the textviews for current score and highscore
        TextView scoreText = gameOverView.findViewById(R.id.scoreText);
        TextView highScoreText = gameOverView.findViewById(R.id.highScoreText);
        //set the textViews to display the correct scores
        scoreText.setText("Score: " + currentScore);
        highScoreText.setText("Highscore: " + highScore);

        //replay button reloads gameactivity
        replayButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), GameActivity.class)));

        //menu button loads back to menu
        menuButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), MainActivity.class)));

        return gameOverView;
    }

}