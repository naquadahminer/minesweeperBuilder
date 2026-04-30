package com.example.minesweeperbuilder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class CustomFieldSetupDialog extends Dialog {
    private OnFieldConfirmedListener listener;
    EditText width, height, bombCount;
    CheckBox isNG;
    Button heightIncrement, widthIncrement, heightDecrement, widthDecrement, bombCountDecrement, bombCountIncrement, confirmButton;
    Settings settings;

    public interface OnFieldConfirmedListener {
        void onFieldConfirmed();
    }

    public CustomFieldSetupDialog(Context context, OnFieldConfirmedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_field_setup_dialog);

        settings = new Settings();
        width = findViewById(R.id.custom_width_text_field);
        height = findViewById(R.id.custom_height_text_field);
        bombCount = findViewById(R.id.custom_bomb_text_field);
        isNG = findViewById(R.id.custom_dialog_NG_checkbox);
        heightIncrement = findViewById(R.id.custom_height_increment_button);
        heightDecrement = findViewById(R.id.custom_height_decrement_button);
        widthIncrement = findViewById(R.id.custom_width_increment_button);
        widthDecrement = findViewById(R.id.custom_width_decrement_button);
        bombCountIncrement = findViewById(R.id.custom_bomb_increment_button);
        bombCountDecrement = findViewById(R.id.custom_bomb_decrement_button);
        confirmButton = findViewById(R.id.custom_dialog_create_button);

        heightDecrement.setOnClickListener(view -> {
            int currentHeight = Integer.parseInt(height.getText().toString());
            currentHeight--;
            if (currentHeight < 8) currentHeight = 8;
            height.setText(String.valueOf(currentHeight));
        });

        heightIncrement.setOnClickListener(view -> {
            int currentHeight = Integer.parseInt(height.getText().toString());
            currentHeight++;
            if (currentHeight > 99) currentHeight = 99;
            height.setText(String.valueOf(currentHeight));
        });

        widthDecrement.setOnClickListener(view -> {
            int currentWidth = Integer.parseInt(width.getText().toString());
            currentWidth--;
            if (currentWidth < 8) currentWidth = 8;
            width.setText(String.valueOf(currentWidth));
        });

        widthIncrement.setOnClickListener(view -> {
            int currentWidth = Integer.parseInt(width.getText().toString());
            currentWidth++;
            if (currentWidth > 99) currentWidth = 99;
            width.setText(String.valueOf(currentWidth));
        });

        bombCountDecrement.setOnClickListener(view -> {
            int currentWidth = Integer.parseInt(width.getText().toString());
            int currentHeight = Integer.parseInt(height.getText().toString());
            int currentBombCount = Integer.parseInt(bombCount.getText().toString());
            currentBombCount--;
            if (currentBombCount < 1) currentBombCount = 1;
            bombCount.setText(String.valueOf(currentBombCount));
        });

        bombCountIncrement.setOnClickListener(view -> {
            int currentWidth = Integer.parseInt(width.getText().toString());
            int currentHeight = Integer.parseInt(height.getText().toString());
            int currentBombCount = Integer.parseInt(bombCount.getText().toString());
            currentBombCount++;
            if (currentBombCount > (currentHeight*currentWidth - 1)) currentBombCount = (currentHeight*currentWidth - 1);
            bombCount.setText(String.valueOf(currentBombCount));
        });

        confirmButton.setOnClickListener(view -> {
            int widthValue = Integer.parseInt(width.getText().toString());
            int heightValue = Integer.parseInt(height.getText().toString());
            int bombCountValue = Integer.parseInt(bombCount.getText().toString());
            settings.difficulty = "CUSTOM";
            settings.height = heightValue;
            settings.width = widthValue;
            settings.bombCount = bombCountValue;
            settings.save(getContext());
            listener.onFieldConfirmed();
            dismiss();
        });

        Window window = getWindow();
        if (window != null) {
            window.setLayout((int)(getContext().getResources()
            .getDisplayMetrics().widthPixels * 0.9),
            ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

}
