package com.example.minesweeperbilder;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements OnCellClickListener{
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    MinesweeperGame game;
    TextView smiley, timer, flag, flagsCount;
    boolean timerStarted;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = findViewById(R.id.activity_main_flag);
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        flagsCount = findViewById(R.id.activity_main_flagsleft);

        smiley = findViewById(R.id.activity_main_smiley);
        smiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game = new MinesweeperGame(10, 10);
                mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
                mineGridRecyclerAdapter.setGameOver(game.isGameOver());
                timerStarted = false;
                flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));
            }
        });

        gridRecyclerView = findViewById(R.id.activity_main_grid);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(this, 10));
        game = new MinesweeperGame(10, 10);
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(game.getMineGrid().getCells(), this);
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);
        flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));
    }

    @Override
    public void onCellClick(Cell cell) {
        game.handleCellClick(cell);

        flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));

        if (game.isGameOver()) {
            Toast.makeText(this, "Game is over", Toast.LENGTH_SHORT).show();
            mineGridRecyclerAdapter.setGameOver(game.isGameOver());
            game.getMineGrid().revealAllBombs();
        }

        if (game.isGameWon()) {
            Toast.makeText(this, "Good job", Toast.LENGTH_SHORT).show();
            game.getMineGrid().revealAllBombs();
        }

        mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
    }
}