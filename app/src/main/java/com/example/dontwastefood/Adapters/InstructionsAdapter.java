package com.example.dontwastefood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Models.InstructionsResponse;
import com.example.dontwastefood.Models.Step;
import com.example.dontwastefood.R;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder> {
    Context context;
    List<InstructionsResponse> list;

    public InstructionsAdapter(Context context, List<InstructionsResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        holder.recyclerView_instructions_steps.setHasFixedSize(true);
        holder.recyclerView_instructions_steps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        InstructionStepAdapter stepAdapter =new InstructionStepAdapter(context,list.get(position).getSteps());
        holder.recyclerView_instructions_steps.setAdapter(stepAdapter);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionsViewHolder extends RecyclerView.ViewHolder{
    RecyclerView recyclerView_instructions_steps;

    public InstructionsViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerView_instructions_steps = itemView.findViewById(R.id.recycler_instruction_steps);

    }
}
