package com.example.minesweeperbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LibraryActivity extends AppCompatActivity implements OnFieldActionListener {
    SavedFieldsList savedFields = new SavedFieldsList();
    RecyclerView libraryRecyclerView;
    LibraryFieldsRecyclerAdapter adapter;
    ImageView backButton;

    private OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            Intent intent = new Intent(LibraryActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getOnBackPressedDispatcher().addCallback(this, callback);
        callback.setEnabled(true);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library);

        savedFields.load(this);

        libraryRecyclerView = findViewById(R.id.library_recycler_view);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LibraryFieldsRecyclerAdapter(savedFields.getFields(), this);
        libraryRecyclerView.setAdapter(adapter);

        backButton = findViewById(R.id.library_header_arrow);
        backButton.setOnClickListener(view -> {
            callback.handleOnBackPressed();
        });
    }

    @Override
    public void onPlayClick(SavedField field) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isPrebuiltField", true);
        intent.putExtra("isFromLibrary", true);
        intent.putExtra("width", field.width);
        intent.putExtra("height", field.height);
        int[] bombPosArray = field.bombPositions.stream().mapToInt(Integer::intValue).toArray();
        intent.putExtra("simplifiedField", bombPosArray);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(SavedField field) {
        savedFields.removeField(this, savedFields.getFields().indexOf(field));
        adapter.setFields(savedFields.getFields());
    }

    @Override
    public void onUploadClick(SavedField field) {
        showFieldUploadDialog(field);
    }

    @Override
    public void onRenameClick(SavedField field) {
        // TODO: add rename functionality
    }

    @Override
    public void onCloneClick(SavedField field) {
        SavedField clone = field.clone();
        savedFields.addField(this, clone);
        adapter.setFields(savedFields.getFields());
    }

    private void showFieldUploadDialog(SavedField field) {
        new FieldUploadDialog(this, () -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("fields").document(field.id).get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.exists()) {
                        db.collection("fields").document(field.id).set(field);
                        Log.d("upload", "success");
                    }
                });

        }).show();
    }
}