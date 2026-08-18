package com.example.minesweeperbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LibraryActivity extends AppCompatActivity implements LibraryFieldsRecyclerAdapter.OnFieldActionListener {
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
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> testData = new HashMap<>();
            testData.put("message", "hello firestore");
            db.collection("test").add(testData);

            db.collection("test").get().addOnSuccessListener(snapshot -> {
                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    Log.d("firestore", doc.getData().toString());
                }
            });
        });
    }

    @Override
    public void onPlayClick(SavedField field) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isPrebuiltField", true);
        intent.putExtra("isFromLibrary", true);
        intent.putExtra("width", field.width);
        intent.putExtra("height", field.height);
        intent.putExtra("simplifiedField", field.bombPositions);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(SavedField field) {
        savedFields.removeField(this, savedFields.getFields().indexOf(field));
        adapter.setFields(savedFields.getFields());
    }
}