package com.example.minesweeperbuilder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        ImageView valueImageView;

        public MineTileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueImageView = itemView.findViewById(R.id.item_cell_value);
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
                    valueImageView.setBackgroundResource(R.drawable.mine);
                } else if (cell.getValue() == Cell.BLANK) {
                    valueImageView.setImageResource(R.color.transparent);
                } else {
                    if (cell.getValue() == 1) {
                        valueImageView.setImageResource(R.drawable.one);
                    } else if (cell.getValue() == 2) {
                        valueImageView.setImageResource(R.drawable.two);
                    } else if (cell.getValue() == 3) {
                        valueImageView.setImageResource(R.drawable.three);
                    } else if (cell.getValue() == 4) {
                        valueImageView.setImageResource(R.drawable.four);
                    } else if (cell.getValue() == 5) {
                        valueImageView.setImageResource(R.drawable.five);
                    } else if (cell.getValue() == 6) {
                        valueImageView.setImageResource(R.drawable.six);
                    } else if (cell.getValue() == 7) {
                        valueImageView.setImageResource(R.drawable.seven);
                    } else if (cell.getValue() == 8) {
                        valueImageView.setImageResource(R.drawable.eight);
                    }
                }
            } else if (cell.isFlagged()) {
                valueImageView.setImageResource(R.drawable.flag);
            }
        }
    }
}