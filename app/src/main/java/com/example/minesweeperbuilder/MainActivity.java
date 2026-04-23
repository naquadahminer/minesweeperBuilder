package com.example.minesweeperbuilder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnCellClickListener{
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    MinesweeperGame game;
    ImageView smiley, flag;
    TextView flagsCount, timer;
    boolean timerStarted = false;
    int padding = 30;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.activity_main_timer);
        smiley = findViewById(R.id.activity_main_smiley);
        smiley.post(() -> {
            CellDrawable drawable = new CellDrawable();
            drawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
            smiley.setBackground(drawable);
            smiley.setPadding(padding, padding, padding, padding);
        });

        flag = findViewById(R.id.activity_main_flag);
        flag.post(() -> {
            CellDrawable drawable = new CellDrawable();
            drawable.setBounds(0, 0, flag.getWidth(), flag.getHeight());
            flag.setBackground(drawable);
        });
        flag.setOnClickListener(view -> {
            game.toggleMode();
            if (game.isFlagMode()) {
                CellReverseDrawable drawable = new CellReverseDrawable();
                drawable.setBounds(0, 0, flag.getWidth(), flag.getHeight());
                flag.setBackground(drawable);
            } else {
                CellDrawable drawable = new CellDrawable();
                drawable.setBounds(0, 0, flag.getWidth(), flag.getHeight());
                flag.setBackground(drawable);
            }
        });

        flagsCount = findViewById(R.id.activity_main_flagsleft);

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
                    drawable.setBounds(0, 0, flag.getWidth(), flag.getHeight());
                    flag.setBackground(drawable);
                    game = new MinesweeperGame(10, 10, 10);
                    timerHandler.removeCallbacks(timerRunnable);
                    mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
                    mineGridRecyclerAdapter.setGameOver(game.isGameOver());
                    timerStarted = false;
                    flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));
                    timer.setText(String.format(Locale.getDefault(), "%03d", 0));
                    smiley.setImageResource(R.drawable.smiley);
                    smiley.setPadding(padding, padding, padding, padding);
                    break;
            }
            return true;
        });



        gridRecyclerView = findViewById(R.id.activity_main_grid);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(this, 10) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        gridRecyclerView.setNestedScrollingEnabled(false);
        game = new MinesweeperGame(10, 10, 10);
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(game.getMineGrid().getCells(), this);
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);
        flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));
    }

    @Override
    public void onCellClick(Cell cell) {
        int cellIndex = game.getMineGrid().getCells().indexOf(cell);
        if (game.isFieldClosed()) {
            game.getMineGrid().generateGrid(10, cellIndex);
            game.setFieldClosed(false);
        }
        game.handleCellClick(game.getMineGrid().getCells().get(cellIndex));
        startTimer();
        flagsCount.setText(String.format(Locale.getDefault(), "%03d", game.getNumberOfBombs() - game.getFlagCount()));

        if (game.isGameOver()) {
            mineGridRecyclerAdapter.setGameOver(game.isGameOver());
            game.getMineGrid().revealAllBombs();
            smiley.setImageResource(R.drawable.dead_smiley);
            smiley.setPadding(padding, padding, padding, padding);
        }

        if (game.isGameWon()) {
            game.getMineGrid().setAllFlags();
            smiley.setImageResource(R.drawable.cool_smiley);
            smiley.setPadding(padding, padding, padding, padding);
        }

        mineGridRecyclerAdapter.setCells(game.getMineGrid().getCells());
    }

    private void startTimer() {
        if (!timerStarted) {
            timerStarted = true;
            timerHandler.postDelayed(timerRunnable, 1000);
        }
    }
}