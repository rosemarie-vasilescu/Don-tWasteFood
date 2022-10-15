package com.example.dontwastefood.Adapters;

import static java.time.temporal.ChronoUnit.DAYS;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Listeners.MoreButtonClickListener;
import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.Models.ShoppingList;
import com.example.dontwastefood.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {
    public static final String EDIT = "EDIT";
    public static final String SHOPPING = "SHOPPING";
    public static final String DELETE = "DELETE";
    Context context;
    List<ShoppingList> list;
    MoreButtonClickListener listener;




    public ShoppingListAdapter(Context context, List<ShoppingList> list,MoreButtonClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_shop_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        holder.textView_ingredients_name.setText(list.get(position).getQuantity() + " " + list.get(position).getName());
        holder.textView_ingredients_name.setSelected(true);
holder.checkBox_bought.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.isChecked())
        {
            holder.textView_ingredients_name.setPaintFlags(holder.textView_ingredients_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.textView_ingredients_name.setPaintFlags(holder.textView_ingredients_name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

        }
    }
});


//TODO: nested recylcerview pe categorii


//        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + list.get(position).image).into(holder.imageView_ingredients);
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_shoppinglist, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.action_popup_edit:
                                listener.onMoreButtonClicked(EDIT,String.valueOf(list.get(holder.getAdapterPosition()).getId()),String.valueOf(list.get(holder.getAdapterPosition()).getName()),String.valueOf(list.get(holder.getAdapterPosition()).getQuantity()),view);
                                break;
                            case R.id.action_popup_delete:
                                listener.onMoreButtonClicked(DELETE,String.valueOf(list.get(holder.getAdapterPosition()).getId()),String.valueOf(list.get(holder.getAdapterPosition()).getName()),String.valueOf(list.get(holder.getAdapterPosition()).getQuantity()),view);
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
class ShoppingListViewHolder extends RecyclerView.ViewHolder  {
    MaterialCheckBox checkBox_bought;
    TextView textView_ingredients_name,textView_quantity;
    ImageView btn_plus;
    CardView shop_item_container;

    public ShoppingListViewHolder(@NonNull View itemView) {
        super(itemView);
        shop_item_container = itemView.findViewById(R.id.shop_item_container);
        textView_ingredients_name = itemView.findViewById(R.id.textView_item_name);
        //textView_ingredients_name = itemView.findViewById(R.id.textView_ingredients_name);
        btn_plus = itemView.findViewById(R.id.btn_plus);
        textView_quantity = itemView.findViewById(R.id.textView_quantity);
checkBox_bought = itemView.findViewById(R.id.checkbox_buy);
    }
//
//    @Override
//    public void onClick(View view) {
//
//        showPopupMenu(view);
//    }
//    private void showPopupMenu(View view){
//        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
//        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_pantry, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(this);
//        popupMenu.show();
//
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_popup_edit:
//                Intent intent = new Intent()
//            case R.id.action_popup_shopping:
//                Log.d("tag","add");
//                return true;
//            case R.id.action_popup_delete:
//                Log.d("tag","delete");
//                return true;
//            default:
//                return false;
//        }
//
//    }
}
