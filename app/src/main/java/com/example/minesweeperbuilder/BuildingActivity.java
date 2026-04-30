package com.example.minesweeperbuilder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class BuildingActivity extends AppCompatActivity {
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    MinesweeperGame game;
    ImageView smiley, flag, fieldSetupButton;
    TextView flagsCount, timer;
    boolean timerStarted = false;
    int padding = 30;
    int smallerCoordinate, largerCoordinate;
    Settings settings;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (!game.isGameOver() && !game.isGameWon()) {
                game.incrementSeconds();
                int seconds = game.getElapsedSeconds();
                timer.setText(String.format(Locale.getDefault(), "%03d", Math.min(seconds, 999)));
                timerHandler.postDelayed(this, 1000);
            }
        }
    };

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        settings = new Settings();
        settings.load(this);
        smallerCoordinate = Math.min(settings.width, settings.height);
        largerCoordinate = Math.max(settings.width, settings.height);

        smiley = findViewById(R.id.activity_building_smiley);
        smiley.post(() -> {
            CellDrawable drawable = new CellDrawable();
            drawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
            smiley.setBackground(drawable);
            smiley.setPadding(padding, padding, padding, padding);
        });

        fieldSetupButton = findViewById(R.id.activity_building_field_setup);
        fieldSetupButton.setOnClickListener(view -> {
                showFieldSetupDialog();
        });
    }

    private void flagBorderUpdate() {
        if (game.isFlagMode()) {
            CellReverseDrawable drawable = new CellReverseDrawable();
            drawable.setBounds(0, 0, flag.getWidth(), flag.getHeight());
            flag.setBackground(drawable);
        } else {
            CellDrawable drawable = new CellDrawable();
            drawable.setBounds(0, 0, flag.getWidth(), flag.getHeight());
            flag.setBackground(drawable);
        }
    }

    private void showFieldSetupDialog() {
        Dialog fieldSetupDialog = new Dialog(this);
        fieldSetupDialog.setContentView(R.layout.field_setup_dialog);
        fieldSetupDialog.show();

        Window window = fieldSetupDialog.getWindow();
        if (window != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        EditText width, height;
        Button heightIncrement, widthIncrement, heightDecrement, widthDecrement, createButton;
        width = findViewById(R.id.building_width_text_field);
        height = findViewById(R.id.building_height_text_field);
        heightIncrement = findViewById(R.id.building_height_increment_button);
        heightDecrement = findViewById(R.id.building_height_decrement_button);
        widthIncrement = findViewById(R.id.building_width_increment_button);
        widthDecrement = findViewById(R.id.building_width_decrement_button);
        createButton = findViewById(R.id.building_dialog_create_button);

    }
}
