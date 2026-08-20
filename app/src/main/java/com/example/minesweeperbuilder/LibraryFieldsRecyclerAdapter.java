package com.example.minesweeperbuilder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
        TextView nameView, sizeAndMineCountView, fieldMenuButton;
        ImageView playButton, deleteButton;

        public LibraryFieldViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.lib_field_name);
            sizeAndMineCountView = itemView.findViewById(R.id.lib_field_size);
            fieldMenuButton = itemView.findViewById(R.id.lib_field_menu);
            playButton = itemView.findViewById(R.id.lib_play_field);
        }

        public void bind(final SavedField field) {
            nameView.setText(field.name);
            sizeAndMineCountView.setText(field.width + "x" + field.height + " - " + Integer.toString(field.bombCount) + " mines");
            fieldMenuButton.setOnClickListener(view -> openPopupMenu(view, field));
            playButton.setOnClickListener(view -> listener.onPlayClick(field));
        }

        private void openPopupMenu(View v, SavedField field) {
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            menu.getMenuInflater().inflate(R.menu.library_field_menu, menu.getMenu());

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getTitle().equals("Delete")) {
                        listener.onDeleteClick(field);
                    } else if (item.getTitle().equals("Rename")) {
                        listener.onRenameClick(field);
                    } else if (item.getTitle().equals("Upload")) {
                        listener.onUploadClick(field);
                    } else if (item.getTitle().equals("Clone")) {
                        listener.onCloneClick(field);
                    }

                    return true;
                }
            });
            menu.show();
        }
    }
}