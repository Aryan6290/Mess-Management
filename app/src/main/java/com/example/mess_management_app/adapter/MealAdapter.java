package com.example.mess_management_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mess_management_app.R;
import com.example.mess_management_app.model.DataX;
import com.example.mess_management_app.model.GetAllMeal;
import com.example.mess_management_app.model.Meals;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ItemViewHolder> {
    private final Context context;
    private  ArrayList<Meals>meal = new ArrayList<>();

    public MealAdapter(Context context, ArrayList<Meals> meal) {
        this.context = context;
        this.meal = meal;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.item_meal,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Meals current= meal.get(position);
        holder.nameShow.setText(current.getUserName());
        holder.typeShow.setText(current.getType());
        //Glide.with(context).load(current.getDate()).into(holder.profileImage);
//        holder.dateShow.setText(current.getDate());
//        holder.timeShow.setText(current.getTime());
//        holder.typeShow.setText(current.getType());

    }

    @Override
    public int getItemCount() {

        return  meal.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView nameShow,typeShow;
        ImageView profileImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            //dateShow=itemView.findViewById(R.id.dateDayShow);
            profileImage=itemView.findViewById(R.id.profile_image);
            typeShow=itemView.findViewById(R.id.typeShow);
            nameShow=itemView.findViewById(R.id.nameTextItem);
            //timeShow=itemView.findViewById(R.id.timeShow);
        }
    }
}
