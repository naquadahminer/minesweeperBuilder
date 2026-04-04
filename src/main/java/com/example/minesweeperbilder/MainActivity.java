package com.example.minesweeperbilder;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnCellClickListener{
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    MinesweeperGame game;
    TextView smiley, flag, flagsCount;
    boolean timerStarted;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smiley = findViewById(R.id.activity_main_smiley);
        smiley.post(() -> {
            CellDrawable drawable = new CellDrawable();
            drawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
            smiley.setBackground(drawable);
        });

        flag = findViewById(R.id.activity_main_flag);
        flag.setOnClickListener(view -> {
            game.toggleMode();
            if (game.isFlagMode()) {
                GradientDrawable border = new GradientDrawable();
                border.setColor(0xFFFFFFFF);
                border.setStroke(1, 0xFF000000);
                flag.setBackground(border);
            } else {
                GradientDrawable border = new GradientDrawable();
                border.setColor(0xFFFFFFFF);
                flag.setBackground(border);
            }
        });

        flagsCount = findViewById(R.id.activity_main_flagsleft);

        smiley = findViewById(R.id.activity_main_smiley);
        smiley.setOnClickListener(v -> {
            game = new MinesweeperGame(10, 10);
            mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
            mineGridRecyclerAdapter.setGameOver(game.isGameOver());
            timerStarted = false;
            flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));
        });

        gridRecyclerView = findViewById(R.id.activity_main_grid);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(this, 10) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        gridRecyclerView.setNestedScrollingEnabled(false);
        game = new MinesweeperGame(10, 10);
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(game.getMineGrid().getCells(), this);
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);
        flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));
    }

    @Override
    public void onCellClick(Cell cell) {
        game.handleCellClick(cell);

        flagsCount.setText(String.format(Locale.getDefault(), "%03d", game.getNumberOfBombs() - game.getFlagCount()));

        if (game.isGameOver()) {
            mineGridRecyclerAdapter.setGameOver(game.isGameOver());
            game.getMineGrid().revealAllBombs();
        }

        if (game.isGameWon()) {
            game.getMineGrid().revealAllBombs();
        }

        mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
    }
}