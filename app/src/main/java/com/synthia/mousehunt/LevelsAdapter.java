package com.synthia.mousehunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.synthia.mousehunt.databinding.LevelItemBinding;

import java.util.List;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.LevelHolder> {
Context context;
List <Level> levelLists;
ClickListener listener;
public LevelsAdapter(Context context,List<Level> levelLists,ClickListener listener){
    this.context=context;
    this.levelLists=levelLists;
    this.listener=listener;
}
    @NonNull
    @Override
    public LevelsAdapter.LevelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LevelItemBinding binding= LevelItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new LevelHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull LevelsAdapter.LevelHolder holder, int position) {
Level level=levelLists.get(position);
holder.binding.normalLevel.setText(String.valueOf(level._levelNumber));
if (level._levelNumber==Utils.CURRENT_LEVEL){
    holder.binding.normalLevel.setBackgroundResource(R.drawable.bg_current_level);
}
holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        listener.onClick(view,level);
    }
});
    }

    @Override
    public int getItemCount() {
        return levelLists.size();
    }

    public static class LevelHolder extends RecyclerView.ViewHolder {
    LevelItemBinding binding;
        public LevelHolder(LevelItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}

