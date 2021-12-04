package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GameOverFragment extends Fragment{


    public GameOverFragment() {
        // Required empty public constructor
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
        Button replayButton = gameOverView.findViewById(R.id.game_replay_button);
        Button menuButton = gameOverView.findViewById(R.id.menu_button);

        replayButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), GameActivity.class)));


        menuButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), MainActivity.class)));

        return gameOverView;
    }

}