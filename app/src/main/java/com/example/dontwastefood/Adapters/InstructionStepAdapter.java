package com.example.dontwastefood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Models.Step;
import com.example.dontwastefood.R;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder>{
    Context context;
    List<Step> list;

    public InstructionStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instruction_step,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        holder.textView_instructions_number.setText(String.valueOf(list.get(position).getNumber()));
        holder.textView_instructions_step.setText(list.get(position).getStep());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
class InstructionStepViewHolder extends RecyclerView.ViewHolder{
    TextView textView_instructions_number, textView_instructions_step;
    public InstructionStepViewHolder(@NonNull View itemView) {

        super(itemView);

        textView_instructions_number = itemView.findViewById(R.id.textView_instructions_number);
        textView_instructions_step = itemView.findViewById(R.id.textView_instructions_step);

    }
}