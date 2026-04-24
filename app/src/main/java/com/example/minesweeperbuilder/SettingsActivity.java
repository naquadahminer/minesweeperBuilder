package com.example.minesweeperbuilder;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    TextView gameButton, profileButton, themesButton;
    TextView currentlyChosenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        gameButton = findViewById(R.id.settings_button_game);
        profileButton = findViewById(R.id.settings_button_profile);
        themesButton = findViewById(R.id.settings_button_themes);

        currentlyChosenButton = gameButton;

        updateButtons();

        gameButton.setOnClickListener(view -> {
            currentlyChosenButton = gameButton;
            updateButtons();
        });

        profileButton.setOnClickListener(view -> {
            currentlyChosenButton = profileButton;
            updateButtons();
        });

        themesButton.setOnClickListener(view -> {
            currentlyChosenButton = themesButton;
            updateButtons();
        });
    }

    private void updateButtons() {
        gameButton.post(() -> {
            Drawable drawable;
            if (currentlyChosenButton == gameButton) {
                drawable = new CellRevealedDrawable();
            } else {
                drawable = new CellDrawable();
            }

            drawable.setBounds(0, 0, gameButton.getWidth(), gameButton.getHeight());
            gameButton.setBackground(drawable);
        });

        profileButton.post(() -> {
            Drawable drawable;
            if (currentlyChosenButton == profileButton) {
                drawable = new CellRevealedDrawable();
            } else {
                drawable = new CellDrawable();
            }

            drawable.setBounds(0, 0, profileButton.getWidth(), profileButton.getHeight());
            profileButton.setBackground(drawable);
        });

        themesButton.post(() -> {
            Drawable drawable;
            if (currentlyChosenButton == themesButton) {
                drawable = new CellRevealedDrawable();
            } else {
                drawable = new CellDrawable();
            }

            drawable.setBounds(0, 0, themesButton.getWidth(), themesButton.getHeight());
            themesButton.setBackground(drawable);
        });

    }
}
