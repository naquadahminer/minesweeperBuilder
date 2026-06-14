package com.example.minesweeperbuilder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class BuildingActivity extends AppCompatActivity implements OnCellClickListener {
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    BuildingGame game;
    ImageView smiley, flag, fieldSetupButton;
    TextView flagsCount, timer;
    boolean timerStarted = false;
    int padding = 30;
    int smallerCoordinate, largerCoordinate;
    Settings settings;

    @SuppressLint({"DefaultLocale", "ClickableViewAccessibility"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        settings = new Settings();
        calculateCoordinates();

        flagsCount = findViewById(R.id.activity_building_flagsleft);
        smiley = findViewById(R.id.activity_building_smiley);
        smiley.post(() -> {
            CellDrawable drawable = new CellDrawable();
            drawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
            smiley.setBackground(drawable);
            smiley.setPadding(padding, padding, padding, padding);
        });

        gridRecyclerView = findViewById(R.id.activity_building_grid);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(this, (settings.portraitOrientation) ? smallerCoordinate : largerCoordinate) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        gridRecyclerView.setNestedScrollingEnabled(false);
        game = new BuildingGame((settings.portraitOrientation) ? largerCoordinate : smallerCoordinate, (settings.portraitOrientation) ? smallerCoordinate : largerCoordinate);
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(game.getMineGrid().getCells(), this);
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);
        flagsCount.setText("000");

        fieldSetupButton = findViewById(R.id.activity_building_field_setup);
        fieldSetupButton.setOnClickListener(view -> {
            showBuildingFieldSetupDialog();
        });
    }

    private void showBuildingFieldSetupDialog() {
        new BuildingFieldSetupDialog(this, () -> {
            calculateCoordinates();
            gridRecyclerView.setLayoutManager(new GridLayoutManager(this, (settings.portraitOrientation) ? smallerCoordinate : largerCoordinate) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            game = new BuildingGame((settings.portraitOrientation) ? largerCoordinate : smallerCoordinate, (settings.portraitOrientation) ? smallerCoordinate : largerCoordinate);
            mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
        }).show();
    }

    @Override
    public void onCellClick(Cell cell) {
        game.handleCellClick(cell);
        mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
    }

    private void calculateCoordinates() {
        settings.load(this);
        smallerCoordinate = Math.min(settings.buildingWidth, settings.buildingHeight);
        largerCoordinate = Math.max(settings.buildingWidth, settings.buildingHeight);
    }
}
