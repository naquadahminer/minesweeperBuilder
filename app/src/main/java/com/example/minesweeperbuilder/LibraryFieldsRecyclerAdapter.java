package com.example.minesweeperbuilder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibraryFieldsRecyclerAdapter extends RecyclerView.Adapter<LibraryFieldsRecyclerAdapter.LibraryFieldViewHolder>  {
    private List<SavedField> fields;
    private OnFieldActionListener listener;

    public LibraryFieldsRecyclerAdapter(List<SavedField> fields, OnFieldActionListener listener) {
        this.fields = fields;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LibraryFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_field_template, parent, false);
        return new LibraryFieldViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryFieldViewHolder holder, int position) {
        holder.bind(fields.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    public void setFields(List<SavedField> fields) {
        this.fields = fields;
        notifyDataSetChanged();
    }

    class LibraryFieldViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, sizeAndMineCountView, uploadButton;
        ImageView playButton, deleteButton;

        public LibraryFieldViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.lib_field_name);
            sizeAndMineCountView = itemView.findViewById(R.id.lib_field_size);
            uploadButton = itemView.findViewById(R.id.lib_upload_field);
            playButton = itemView.findViewById(R.id.lib_play_field);
            deleteButton = itemView.findViewById(R.id.lib_delete_field);
        }

        public void bind(final SavedField field) {
            nameView.setText(field.name);
            sizeAndMineCountView.setText(field.width + "x" + field.height + " - " + Integer.toString(field.bombCount) + " mines");
            playButton.setOnClickListener(view -> listener.onPlayClick(field));
            deleteButton.setOnClickListener(view -> listener.onDeleteClick(field));
            uploadButton.setOnClickListener(view -> listener.onUploadClick(field));
        }
    }
}