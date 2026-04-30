package com.example.minesweeperbuilder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class BuildingFieldSetupDialog extends Dialog {
    private CustomFieldSetupDialog.OnFieldConfirmedListener listener;
    EditText width, height;
    Button heightIncrement, widthIncrement, heightDecrement, widthDecrement, confirmButton;
    Settings settings;

    public interface OnFieldConfirmedListener {
        void onFieldConfirmed();
    }

    public BuildingFieldSetupDialog(Context context, CustomFieldSetupDialog.OnFieldConfirmedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_field_setup_dialog);

        settings = new Settings();
        width = findViewById(R.id.building_width_text_field);
        height = findViewById(R.id.building_height_text_field);
        heightIncrement = findViewById(R.id.building_height_increment_button);
        heightDecrement = findViewById(R.id.building_height_decrement_button);
        widthIncrement = findViewById(R.id.building_width_increment_button);
        widthDecrement = findViewById(R.id.building_width_decrement_button);
        confirmButton = findViewById(R.id.building_dialog_create_button);

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

        confirmButton.setOnClickListener(view -> {
            settings.buildingHeight = Integer.parseInt(height.getText().toString());
            settings.buildingWidth = Integer.parseInt(width.getText().toString());
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
