package com.example.minesweeperbuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BrowseFieldsRecyclerAdapter extends RecyclerView.Adapter<BrowseFieldsRecyclerAdapter.BrowseFieldViewHolder> {
    private List<SavedField> fields;
    private OnAddClickListener listener;

    public BrowseFieldsRecyclerAdapter(List<SavedField> fields, OnAddClickListener listener) {
        this.fields = fields;
        this.listener = listener;
    }

    public void setFields(List<SavedField> fields) {
        this.fields = fields;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BrowseFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_field_template, parent, false);
        return new BrowseFieldViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseFieldViewHolder holder, int position) {
        holder.bind(fields.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    class BrowseFieldViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, sizeView;
        ImageView addButton;

        public BrowseFieldViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.browse_field_name);
            sizeView = itemView.findViewById(R.id.browse_field_size);
            addButton = itemView.findViewById(R.id.browse_add_field);
        }

        public void bind(final SavedField field) {
            nameView.setText(field.name);
            sizeView.setText(field.width + "x" + field.height + " - " + field.bombCount + " mines");
            addButton.setOnClickListener(view -> listener.onAddClick(field));
        }
    }
}