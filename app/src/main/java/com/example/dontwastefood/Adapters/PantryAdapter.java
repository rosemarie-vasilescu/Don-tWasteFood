package com.example.dontwastefood.Adapters;

import static java.time.temporal.ChronoUnit.DAYS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Activities.PantryActivity;
import com.example.dontwastefood.Listeners.MoreButtonClickListener;
import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryViewHolder> {
    public static final String EDIT = "EDIT";
    public static final String SHOPPING = "SHOPPING";
    public static final String DELETE = "DELETE";
    Context context;
    List<MyPantry> list;
    MoreButtonClickListener listener;




    public PantryAdapter(Context context, List<MyPantry> list,MoreButtonClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PantryViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        holder.textView_ingredients_name.setText(list.get(position).getName());
        holder.textView_ingredients_name.setSelected(true);




        LocalDateTime now = null;
      String dateNow = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM dd, yyyy");
            dateNow = dtf.format(now);
            Log.i("now",dtf.format(now));
        }

//        sdf.format(now);
        String input =list.get(position).getDate();

        DateTimeFormatter formatter = null;
        int y = 1;
        int m = 1;
        int dayOfMonth = 1;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern( "MM dd, yyyy" );
            LocalDate localDate = LocalDate.parse( input , formatter );
            LocalDate localDate2 = LocalDate.parse(dateNow , formatter );
            Log.i("local1",localDate.toString());
            Log.i("local2",now.toString());
            long daysBetween = DAYS.between(localDate2,localDate);
            Log.i("days",String.valueOf(daysBetween));

            y = localDate.getYear();
            m = localDate.getMonthValue();
            dayOfMonth = localDate.getDayOfMonth();
            if(daysBetween > 0 && daysBetween <4){
                holder.textView_ingredients_category.setText("Will expire in " + daysBetween + " days");
            }
            else if(daysBetween <= 0){
                holder.textView_ingredients_category.setText("Expired " + Math.abs(daysBetween) + " days ago");

            }
        }

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + list.get(position).getImage()).into(holder.imageView_ingredients);
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_pantry, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_popup_edit:

                                listener.onMoreButtonClicked(EDIT,String.valueOf(list.get(holder.getAdapterPosition()).getId()),String.valueOf(list.get(holder.getAdapterPosition()).getName()),String.valueOf(list.get(holder.getAdapterPosition()).getDate()),view);

                                break;
                            case R.id.action_popup_shopping:
                                listener.onMoreButtonClicked(SHOPPING,String.valueOf(list.get(holder.getAdapterPosition()).getId()),String.valueOf(list.get(holder.getAdapterPosition()).getName()),String.valueOf(list.get(holder.getAdapterPosition()).getDate()),view);
                                break;
                            case R.id.action_popup_delete:
                                listener.onMoreButtonClicked(DELETE,String.valueOf(list.get(holder.getAdapterPosition()).getId()),String.valueOf(list.get(holder.getAdapterPosition()).getName()),String.valueOf(list.get(holder.getAdapterPosition()).getDate()),view);
                                break;
                            default:

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class PantryViewHolder extends RecyclerView.ViewHolder  {
    TextView textView_ingredients_name,textView_ingredients_category;
    ImageView imageView_ingredients,btn_plus;
    CardView ingredient_list_container;

    public PantryViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredient_list_container = itemView.findViewById(R.id.ingredient_list_container);
        textView_ingredients_name = itemView.findViewById(R.id.textView_ingredients_quantity);

        imageView_ingredients = itemView.findViewById(R.id.imageView_ingredients);
        btn_plus = (ImageView) itemView.findViewById(R.id.btn_plus_meal);
        textView_ingredients_category = itemView.findViewById(R.id.textView_category);

    }

}