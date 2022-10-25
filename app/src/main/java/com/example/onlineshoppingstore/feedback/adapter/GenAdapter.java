package com.example.onlineshoppingstore.feedback.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingstore.feedback.model.Feedback;

import java.util.List;

public abstract class GenAdapter<TAdapter> extends RecyclerView.Adapter<GenAdapter<TAdapter>.ViewHolder> {
    private List<TAdapter> list;
    private Context context;

    public GenAdapter(List<TAdapter> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return (ViewHolder) setViewHolder(parent);
    }

    public abstract ViewHolder setViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TAdapter item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(TAdapter item);
    }
}
