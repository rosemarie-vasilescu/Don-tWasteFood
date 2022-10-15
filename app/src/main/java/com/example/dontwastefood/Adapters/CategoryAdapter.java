package com.example.dontwastefood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dontwastefood.Listeners.MoreButtonClickListener;
import com.example.dontwastefood.Models.Category;
import com.example.dontwastefood.Models.MyPantry;
import com.example.dontwastefood.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> list;
    private List<MyPantry> nestedList = new ArrayList<>();
private Context context;
private MoreButtonClickListener listener;
    public CategoryAdapter(Context context, List<Category> list, MoreButtonClickListener listener){
        this.list = list;
this.listener = listener;
this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
Category category = list.get(position);

holder.mTextView.setText(category.getItemText());

boolean isExpandable = category.isExpandable();
holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

if (isExpandable){
    holder.mArrowImage.setImageResource(R.drawable.ic_arrow_up);
}
else
{
    holder.mArrowImage.setImageResource(R.drawable.ic_arrow_down);
}

PantryAdapter adapter = new PantryAdapter(context,nestedList,listener);
holder.nestedRecyclerView.setLayoutManager(new GridLayoutManager(context,1));
holder.nestedRecyclerView.setHasFixedSize(true);
holder.nestedRecyclerView.setAdapter(adapter);
holder.linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        category.setExpandable(!category.isExpandable());
        nestedList = category.getNestedList();
        notifyItemChanged(holder.getAdapterPosition());

    }
});

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private RelativeLayout expandableLayout;
        private TextView mTextView;
        private ImageView mArrowImage;
        private RecyclerView nestedRecyclerView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            mTextView = itemView.findViewById(R.id.itemTv);
            mArrowImage = itemView.findViewById(R.id.arro_imageview);
            nestedRecyclerView = itemView.findViewById(R.id.child_rv);
        }
    }
}
