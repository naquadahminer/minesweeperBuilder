package com.example.minesweeperbuilder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MineGridRecyclerAdapter extends RecyclerView.Adapter<MineGridRecyclerAdapter.MineTileViewHolder> {
    private List<Cell> cells;
    private OnCellClickListener listener;
    private boolean isGameOver;

    public MineGridRecyclerAdapter(List<Cell> cells, OnCellClickListener listener) {
        this.cells = cells;
        this.listener = listener;
        this.isGameOver = false;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    @NonNull
    @Override
    public MineTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new MineTileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MineTileViewHolder holder, int position) {
        holder.bind(cells.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
        notifyDataSetChanged();
    }

    class MineTileViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        public MineTileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextView = itemView.findViewById(R.id.item_cell_value);
        }

        public void bind(final Cell cell) {
            itemView.setBackground(new CellDrawable());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCellClick(cell);
                }
            });

            if (cell.isRevealed()) {
                itemView.setBackground(new CellRevealedDrawable());
                if (cell.getValue() == Cell.BOMB) {
                    if (isGameOver) {
                        itemView.setBackgroundColor(Color.RED);
                    }
                    valueTextView.setText(R.string.bomb);
                } else if (cell.getValue() == Cell.BLANK) {
                    valueTextView.setText("");
                } else {
                    valueTextView.setText(String.valueOf(cell.getValue()));
                    if (cell.getValue() == 1) {
                        valueTextView.setTextColor(Color.BLUE);
                    } else if (cell.getValue() == 2) {
                        valueTextView.setTextColor(Color.GREEN);
                    } else if (cell.getValue() == 3) {
                        valueTextView.setTextColor(Color.RED);
                    } else if (cell.getValue() == 4) {
                        valueTextView.setTextColor(Color.rgb(255, 22, 249) );
                    } else if (cell.getValue() == 5) {
                        valueTextView.setTextColor(Color.rgb(138, 74, 15));
                    } else if (cell.getValue() == 6) {
                        valueTextView.setTextColor(Color.CYAN);
                    } else if (cell.getValue() == 7) {
                        valueTextView.setTextColor(Color.LTGRAY);
                    } else if (cell.getValue() == 8) {
                        valueTextView.setTextColor(Color.DKGRAY);
                    }
                }
            } else if (cell.isFlagged()) {
                valueTextView.setText(R.string.flag);
            }
        }
    }
}