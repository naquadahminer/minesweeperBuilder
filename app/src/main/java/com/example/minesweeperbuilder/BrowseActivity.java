package com.example.minesweeperbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity implements OnAddClickListener {
    RecyclerView browseRecyclerView;
    Button loadMoreButton;
    ImageView backButton;
    BrowseFieldsRecyclerAdapter adapter;
    List<SavedField> loadedFields = new ArrayList<>();
    FirebaseFirestore db;
    DocumentSnapshot lastVisible;
    private static final int PAGE_SIZE = 50;

    private OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            Intent intent = new Intent(BrowseActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getOnBackPressedDispatcher().addCallback(this, callback);
        callback.setEnabled(true);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browse);

        db = FirebaseFirestore.getInstance();
        browseRecyclerView = findViewById(R.id.browse_recycler_view);
        loadMoreButton = findViewById(R.id.browse_load_more_button);

        browseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BrowseFieldsRecyclerAdapter(loadedFields, this);
        browseRecyclerView.setAdapter(adapter);

        backButton = findViewById(R.id.browse_header_arrow);
        backButton.setOnClickListener(view -> {
            callback.handleOnBackPressed();
        });

        loadMoreButton.setOnClickListener(view -> loadNextPage());

        loadNextPage();
    }

    private void loadNextPage() {
        var query = db.collection("fields")
                .orderBy("name")
                .limit(PAGE_SIZE);

        if (lastVisible != null) {
            query = query.startAfter(lastVisible);
        }

        query.get().addOnSuccessListener(this::onPageLoaded);
    }

    private void onPageLoaded(QuerySnapshot snapshot) {
        if (snapshot.isEmpty()) {
            loadMoreButton.setEnabled(false);
            return;
        }

        for (DocumentSnapshot doc : snapshot.getDocuments()) {
            SavedField field = doc.toObject(SavedField.class);
            if (field != null) {
                loadedFields.add(field);
            }
        }

        lastVisible = snapshot.getDocuments().get(snapshot.size() - 1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddClick(SavedField field) {
        SavedFieldsList myFields = new SavedFieldsList();
        myFields.addField(this, field);
    }
}