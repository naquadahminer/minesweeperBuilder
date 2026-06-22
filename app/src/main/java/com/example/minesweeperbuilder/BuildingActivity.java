package com.example.minesweeperbuilder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;
import java.util.Objects;

public class BuildingActivity extends AppCompatActivity implements OnCellClickListener {
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    BuildingGame game;
    ImageView smiley, flag, fieldSetupButton, testFieldButton;
    TextView flagsCount;
    boolean loadingPrebuiltField;
    int padding = 30;
    int numberOfBombs;
    int width, height;
    Settings settings;
    private OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            Intent intent = new Intent(BuildingActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    };

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        numberOfBombs = getIntent().getIntExtra("numberOfBombs", 0);
        getOnBackPressedDispatcher().addCallback(this, callback);
        callback.setEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        loadingPrebuiltField = getIntent().getBooleanExtra("loadingPrebuiltField", false);

        settings = new Settings();
        calculateDimensions();

        flagsCount = findViewById(R.id.activity_building_flagsleft);
        smiley = findViewById(R.id.activity_building_smiley);
        smiley.post(() -> {
            CellDrawable drawable = new CellDrawable();
            drawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
            smiley.setBackground(drawable);
            smiley.setPadding(padding, padding, padding, padding);
        });

        flagsCount = findViewById(R.id.activity_building_flagsleft);

        smiley.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    CellReverseDrawable smileyClickedDrawable = new CellReverseDrawable();
                    smileyClickedDrawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
                    smiley.setBackground(smileyClickedDrawable);
                    smiley.setImageResource(R.drawable.clicked_smiley);
                    smiley.setPadding(padding, padding, padding, padding);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    CellDrawable drawable = new CellDrawable();
                    drawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
                    smiley.setBackground(drawable);
                    game = new BuildingGame(height, width, numberOfBombs);
                    flagsCount.setText(String.format(Locale.getDefault(), "%03d", game.getNumberOfBombs()));
                    mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
                    smiley.setImageResource(R.drawable.smiley);
                    smiley.setPadding(padding, padding, padding, padding);
                    break;
            }
            return true;
        });

        gridRecyclerView = findViewById(R.id.activity_building_grid);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(this, width) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        gridRecyclerView.setNestedScrollingEnabled(false);
        game = new BuildingGame(height, width, numberOfBombs);
        if (loadingPrebuiltField) {
            game.getMineGrid().setMineGrid(Objects.requireNonNull(getIntent().getIntArrayExtra("simplifiedField")));
            game.getMineGrid().revealAllCells();
        }
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(game.getMineGrid().getCells(), this);
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);
        flagsCount.setText(String.format(Locale.getDefault(), "%03d", numberOfBombs));

        fieldSetupButton = findViewById(R.id.activity_building_field_setup);
        fieldSetupButton.setOnClickListener(view -> {
            showBuildingFieldSetupDialog();
        });

        testFieldButton = findViewById(R.id.activity_building_mode_change);
        testFieldButton.setOnClickListener(view -> {
            Intent intent = new Intent(BuildingActivity.this, MainActivity.class);
            intent.putExtra("isPrebuiltField", true);
            intent.putExtra("numberOfBombs", game.getNumberOfBombs());
            intent.putExtra("simplifiedField", game.getSimplifiedGrid());
            startActivity(intent);
        });
    }

    private void showBuildingFieldSetupDialog() {
        new BuildingFieldSetupDialog(this, () -> {
            calculateDimensions();
            gridRecyclerView.setLayoutManager(new GridLayoutManager(this, width) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            game = new BuildingGame(height, width, numberOfBombs);
            mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
        }).show();
    }

    @Override
    public void onCellClick(Cell cell) {
        game.handleCellClick(cell);
        mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
        flagsCount.setText(String.format(Locale.getDefault(), "%03d", game.getNumberOfBombs()));
    }

    private void calculateDimensions() {
        settings.load(this);
        if (settings.portraitOrientation) {
            width = Math.min(settings.buildingWidth, settings.buildingHeight);
            height = Math.max(settings.buildingWidth, settings.buildingHeight);
        } else {
            width = Math.max(settings.buildingWidth, settings.buildingHeight);
            height = Math.min(settings.buildingWidth, settings.buildingHeight);
        }
    }
}
