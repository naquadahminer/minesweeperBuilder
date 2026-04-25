package com.example.minesweeperbuilder;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    TextView gameButton, profileButton, themesButton;
    TextView currentlyChosenButton;
    Settings settings;
    View content;
    RadioGroup difficultyGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        LinearLayout scrollContent = findViewById(R.id.settings_scroll_content);

        gameButton = findViewById(R.id.settings_button_game);
        profileButton = findViewById(R.id.settings_button_profile);
        themesButton = findViewById(R.id.settings_button_themes);
        settings = new Settings();
        settings.load(this);
        loadTabContent(scrollContent, R.layout.settings_game);

        currentlyChosenButton = gameButton;

        updateTabButtons();

        gameButton.setOnClickListener(view -> {
            currentlyChosenButton = gameButton;
            loadTabContent(scrollContent, R.layout.settings_game);
            updateTabButtons();
        });

        profileButton.setOnClickListener(view -> {
            currentlyChosenButton = profileButton;
            loadTabContent(scrollContent, R.layout.settings_game);
            updateTabButtons();
        });

        themesButton.setOnClickListener(view -> {
            currentlyChosenButton = themesButton;
            loadTabContent(scrollContent, R.layout.settings_game);
            updateTabButtons();
        });

    }

    private void updateTabButtons() {
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

    private void loadTabContent(LinearLayout scrollContent, int layoutId) {
        scrollContent.removeAllViews();
        content = getLayoutInflater().inflate(layoutId, scrollContent, false);
        scrollContent.addView(content);

        android.util.Log.d("Settings", "loading tab, difficulty = " + settings.difficulty);

        difficultyGroup = content.findViewById(R.id.difficulty_group);
        if (difficultyGroup != null) {
            updateDifficutlyRadio(difficultyGroup);
            setupDifficultyListener(difficultyGroup);
        }
    }

    private void updateDifficutlyRadio(RadioGroup radio) {
        if (settings.difficulty == Difficulty.BEGINNER) {
            radio.check(R.id.settings_beginner_radio);
        } else if (settings.difficulty == Difficulty.INTERMEDIATE) {
            radio.check(R.id.settings_intermediate_radio);
        } else if (settings.difficulty == Difficulty.EXPERT) {
            radio.check(R.id.settings_expert_radio);
        }

    }

    private void setupDifficultyListener(RadioGroup difficultyGroup) {
        difficultyGroup.setOnCheckedChangeListener((g, checkedId) -> {
            android.util.Log.d("Settings", "listener fired, checkedId = " + checkedId);
            android.util.Log.d("Settings", "beginner id = " + R.id.settings_beginner_radio);
            if (checkedId == R.id.settings_beginner_radio) {
                settings.difficulty = Difficulty.BEGINNER;
            } else if (checkedId == R.id.settings_intermediate_radio) {
                settings.difficulty = Difficulty.INTERMEDIATE;
            } else if (checkedId == R.id.settings_expert_radio) {
                settings.difficulty = Difficulty.EXPERT;
            }
            android.util.Log.d("Settings", "after change, difficulty = " + settings.difficulty);
            settings.save(this);
        });
    }
}
