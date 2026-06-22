package com.example.minesweeperbuilder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnCellClickListener{
    RecyclerView gridRecyclerView;
    MineGridRecyclerAdapter mineGridRecyclerAdapter;
    MinesweeperGame game;
    ImageView smiley, flag;
    TextView flagsCount, timer;
    boolean timerStarted = false;
    boolean isPrebuiltField;
    int padding = 30;
    int bombCount;
    int width, height;
    Settings settings;
    private OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            if (isPrebuiltField) {
                Intent intent = new Intent(MainActivity.this, BuildingActivity.class);
                intent.putExtra("loadingPrebuiltField", true);
                intent.putExtra("numberOfBombs", game.getNumberOfBombs());
                intent.putExtra("simplifiedField", game.getSimplifiedGrid());
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        }
    };

    // timer and it's displaying
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
        getOnBackPressedDispatcher().addCallback(this, callback);
        callback.setEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isPrebuiltField = getIntent().getBooleanExtra("isPrebuiltField", false);
        settings = new Settings();
        settings.load(this);
        calculateDimensions();

        if (isPrebuiltField) {
            bombCount = getIntent().getIntExtra("numberOfBombs", 10);
            System.out.println(bombCount);
        } else {
            bombCount = settings.bombCount;
        }

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
            flagBorderUpdate();
        });
        flag.setOnClickListener(view -> {
            if(!settings.forcedNF) {
                game.toggleMode();
                flagBorderUpdate();
            }
        });

        flagsCount = findViewById(R.id.activity_main_flagsleft);
        // smiley click logic
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
                    game = new MinesweeperGame(height, width, bombCount);
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


        // generating minegrid. depending on the orientation it'll use the lower or higher value of height and width for the field displaying width
        gridRecyclerView = findViewById(R.id.activity_main_grid);
        gridRecyclerView.setLayoutManager(new GridLayoutManager(this, width) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        gridRecyclerView.setNestedScrollingEnabled(false);
        game = new MinesweeperGame(height, width, bombCount);
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(game.getMineGrid().getCells(), this);
        gridRecyclerView.setAdapter(mineGridRecyclerAdapter);
        flagsCount.setText(String.format("%03d", game.getNumberOfBombs() - game.getFlagCount()));
        game.setMode(settings.explore);
        flagBorderUpdate();
    }

    @Override
    public void onCellClick(Cell cell) {
        int cellIndex = game.getMineGrid().getCells().indexOf(cell);
        if (game.isFieldClosed()) {
            if (isPrebuiltField) {
                game.getMineGrid().setMineGrid(Objects.requireNonNull(getIntent().getIntArrayExtra("simplifiedField")));
            } else {
                game.getMineGrid().generateGrid(settings.bombCount, cellIndex);
            }
            game.setFieldClosed(false);
        }
        game.handleCellClick(game.getMineGrid().getCells().get(cellIndex));
        startTimer();
        flagsCount.setText(String.format(Locale.getDefault(), "%03d", game.getNumberOfBombs() - game.getFlagCount()));

        CellDrawable drawable = new CellDrawable();
        drawable.setBounds(0, 0, smiley.getWidth(), smiley.getHeight());
        smiley.setBackground(drawable);
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