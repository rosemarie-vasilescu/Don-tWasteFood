package com.example.dontwastefood.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Listeners.IngredientClickListener;
import com.example.dontwastefood.Listeners.RecipeClickListener;
import com.example.dontwastefood.Models.ExtendedIngredient;
import com.example.dontwastefood.Models.Ingredient;
import com.example.dontwastefood.Models.IngredientsResponse;
import com.example.dontwastefood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchIngredientsAdapter extends RecyclerView.Adapter<SearchIngredientsViewHolder> {
    Context context;
    List<IngredientsResponse> list;
    IngredientClickListener listener;

    public SearchIngredientsAdapter(Context context, List<IngredientsResponse> list,IngredientClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener= listener;
    }

    @NonNull
    @Override
    public SearchIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchIngredientsViewHolder holder, int position) {
        //Toast.makeText(context, "onbind", Toast.LENGTH_SHORT).show();
//        holder.textView_ingredients_name.setText(String.valueOf(list.get(position).name));
//        holder.textView_ingredients_name.setSelected(true);
        holder.textView_ingredients_name.setText(list.get(position).getName());
        holder.textView_ingredients_name.setSelected(true);

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + list.get(position).getImage()).into(holder.imageView_ingredients);
        holder.ingredient_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIngredientClicked(String.valueOf(list.get(holder.getAdapterPosition()).getName()),String.valueOf(list.get(holder.getAdapterPosition()).getId()),String.valueOf(list.get(holder.getAdapterPosition()).getImage()),String.valueOf(list.get(holder.getAdapterPosition()).getAisle()), holder.getAdapterPosition());
                Log.i("searchingredient",list.get(holder.getAdapterPosition()).toString());

                holder.textView_category.setText("In pantry");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class SearchIngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_ingredients_name,textView_category;
    ImageView imageView_ingredients;
    CardView ingredient_list_container;
    public SearchIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredient_list_container = itemView.findViewById(R.id.ingredient_list_container);
        textView_ingredients_name = itemView.findViewById(R.id.textView_ingredients_quantity);
        //textView_ingredients_name = itemView.findViewById(R.id.textView_ingredients_name);
        imageView_ingredients = itemView.findViewById(R.id.imageView_ingredients);
        textView_category = itemView.findViewById(R.id.textView_category);
    }
}