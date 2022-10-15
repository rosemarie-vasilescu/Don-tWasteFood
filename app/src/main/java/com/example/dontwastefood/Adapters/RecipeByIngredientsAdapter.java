package com.example.dontwastefood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Listeners.RecipeByIngredientsListener;
import com.example.dontwastefood.Listeners.RecipeClickListener;
import com.example.dontwastefood.Models.RecipeByIngredientsResponse;
import com.example.dontwastefood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeByIngredientsAdapter extends RecyclerView.Adapter<RecipeByIngredientsViewHolder> {
     Context context;
     List<RecipeByIngredientsResponse> list;
     RecipeClickListener listener;

    public RecipeByIngredientsAdapter(Context context, List<RecipeByIngredientsResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeByIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeByIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_recipe_by_ingredients, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeByIngredientsViewHolder holder, int position) {
        holder.textView_similar_title.setText(list.get(position).getTitle());
        //Picasso.get().load("https://spoonacular.com/recipeImages/"+list.get(position).id +"-556x370." +list.get(position).imageType).into(holder.imageView_similar);
        Picasso.get().load(list.get(position).getImage()).into(holder.imageView_similar);
        if(list.get(position).getUsedIngredientCount() == list.get(position).getMissedIngredientCount()){
            holder.textView_number.setText("You Have All The Ingredients");

        }
        else{
        holder.textView_number.setText("You Have " + list.get(position).getUsedIngredientCount() + " Out Of " + list.get(position).getMissedIngredientCount() + " Ingredients");
        }
        holder.similar_recipe_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class RecipeByIngredientsViewHolder extends RecyclerView.ViewHolder{
    CardView similar_recipe_holder;
    TextView textView_similar_title,textView_number;
    ImageView imageView_similar;
    public RecipeByIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);

        similar_recipe_holder = itemView.findViewById(R.id.similar_recipe_holder);
        textView_similar_title = itemView.findViewById(R.id.textView_similar_title);
        imageView_similar = itemView.findViewById(R.id.imageView_similar);
        textView_number = itemView.findViewById(R.id.number);
    }
}