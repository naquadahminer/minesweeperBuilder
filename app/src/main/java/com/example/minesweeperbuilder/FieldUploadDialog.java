package com.example.minesweeperbuilder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

public class FieldUploadDialog extends Dialog {
    private OnUploadConfirmedListener listener;
    private Button confirmButton;


    public interface OnUploadConfirmedListener {
        void onUploadConfirmed();
    }

    public FieldUploadDialog(@NonNull Context context, FieldUploadDialog.OnUploadConfirmedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_upload_dialog);

        confirmButton = findViewById(R.id.field_upload_button);

        confirmButton.setOnClickListener(view -> {
            listener.onUploadConfirmed();
            dismiss();
        });
    }
}
