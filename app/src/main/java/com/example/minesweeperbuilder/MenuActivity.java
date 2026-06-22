package com.example.minesweeperbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    private OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            finishAffinity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getOnBackPressedDispatcher().addCallback(this, callback);
        callback.setEnabled(true);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        Button gameButton = findViewById(R.id.menu_button_game);
        gameButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.putExtra("isPrebuildField", false);
            startActivity(intent);
        });

        Button buildingButton = findViewById(R.id.menu_button_building);
        buildingButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, BuildingActivity.class);
            startActivity(intent);
        });

        Button libraryButton = findViewById(R.id.menu_button_library);
        libraryButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, LibraryActivity.class);
            startActivity(intent);
        });

        Button settingsButton = findViewById(R.id.menu_button_settings);
        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        Button aboutButton = findViewById(R.id.menu_button_about);
        aboutButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}