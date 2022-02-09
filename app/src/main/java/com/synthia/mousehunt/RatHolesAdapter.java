package com.synthia.mousehunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.synthia.mousehunt.databinding.RatHoleItemBinding;

import java.util.List;

public class RatHolesAdapter extends RecyclerView.Adapter <RatHolesAdapter.RatHoleHolder >
{
    Context context;
    List<RatHole> ratHoleList;
    ClickListener listener;
    public RatHolesAdapter(Context context, List<RatHole> ratHoleList,ClickListener listener){
        this.context=context;
        this.ratHoleList=ratHoleList;
        this.listener=listener;
    }
    @NonNull
    @Override
    public RatHolesAdapter.RatHoleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RatHoleItemBinding binding=RatHoleItemBinding.inflate(LayoutInflater.from(context),parent,false);
       return new RatHoleHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RatHoleHolder holder, int position) {
        RatHole ratHole=ratHoleList.get(position);
        holder.binding.ratHoleItem.setImageResource(R.drawable.rat_hole_normal);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view,ratHole);
            }
        });

    }


    @Override
    public int getItemCount() {
        return ratHoleList.size();
    }

    public class RatHoleHolder extends RecyclerView.ViewHolder {
        public RatHoleItemBinding binding;

        public RatHoleHolder(RatHoleItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
