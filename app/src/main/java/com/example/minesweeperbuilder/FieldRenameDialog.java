package com.example.minesweeperbuilder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

public class FieldRenameDialog extends Dialog {
    private OnRenameConfirmedListener listener;
    SavedFieldsList savedFields = new SavedFieldsList();
    Button confirmButton;
    EditText nameField;
    SavedField field;
    TextView warning;

    public interface OnRenameConfirmedListener {
        void onRenameConfirmed();
    }

    public FieldRenameDialog(@NonNull Context context, SavedField field, OnRenameConfirmedListener listener) {
        super(context);
        this.listener = listener;
        this.field = field;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.field_rename_dialog);
        super.onCreate(savedInstanceState);
        savedFields.load(getContext());

        confirmButton = findViewById(R.id.field_rename_button);
        nameField = findViewById(R.id.field_rename_dialog_edit);
        warning = findViewById(R.id.field_rename_dialog_warning);

        confirmButton.setOnClickListener(view -> {
            String newName = nameField.getText().toString();
            if (newName.isEmpty()) {
                warning.setText("Field name is empty");
            } else if (newName.equals(field.name)) {
                warning.setText("You didn't change a name");
            } else if (savedFields.containsField(getContext(), newName)) {
                warning.setText("Field's name already exists");
            } else if (newName.length() >= 11) {
                warning.setText("Field name is too long");
            } else {
                field.name = newName;
                savedFields.save(getContext());

                listener.onRenameConfirmed();
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
