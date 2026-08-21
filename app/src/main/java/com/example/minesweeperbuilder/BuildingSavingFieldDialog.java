package com.example.minesweeperbuilder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BuildingSavingFieldDialog extends Dialog {
    EditText fieldNameView;
    TextView warning;
    Button saveButton;
    SavedFieldsList savedFields = new SavedFieldsList();
    String fieldName;
    OnFieldConfirmedListener listener;
    int width, height, bombCount;
    boolean portraitMode;
    List<Integer> simplifiedField;

    public interface OnFieldConfirmedListener {
        void onFieldConfirmed();
    }

    public BuildingSavingFieldDialog(@NonNull Context context, OnFieldConfirmedListener listener, int width, int height, int bombCount, boolean portraitMode, List<Integer> simplifiedField) {
        super(context);
        this.listener = listener;
        this.width = width;
        this.height = height;
        this.bombCount = bombCount;
        this.portraitMode = portraitMode;
        this.simplifiedField = simplifiedField;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_saving_field_dialog);
        savedFields.load(getContext());

        fieldNameView = findViewById(R.id.building_saving_field_dialog_text);
        warning = findViewById(R.id.building_saving_field_dialog_warning);
        saveButton = findViewById(R.id.building_saving_field_dialog_save_button);


        saveButton.setOnClickListener(view -> {
            fieldName = fieldNameView.getText().toString();
            if (fieldName.isEmpty()) {
                warning.setText("Field name is empty");
            } else if (savedFields.containsField(getContext(), fieldName)) {
                warning.setText("Field name already exists");
            } else if (fieldName.length() >= 11) {
                warning.setText("Field name is too long");
            } else {
                SavedField field = new SavedField(fieldName, width, height, bombCount, portraitMode, simplifiedField);
                savedFields.addField(getContext(), field);
                savedFields.save(getContext());

                listener.onFieldConfirmed();
                dismiss();
            }
        });

        Window window = getWindow();
        if (window != null) {
            window.setLayout((int)(getContext().getResources()
            .getDisplayMetrics().widthPixels * 0.9),
            ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


}
