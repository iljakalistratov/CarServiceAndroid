package com.ilja.carservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ClickListener mClickListener;
    private ArrayList<Car> carList;
    private Context context;


    public Adapter(Context ct, ArrayList<Car> localCarList, ClickListener clickListener) {
        context = ct;
        carList = localCarList;
        this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.carID.setText(String.valueOf(carList.get(position).getId()));
        holder.carModel.setText(carList.get(position).getModel());
        holder.carBrand.setText(carList.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView carModel, carID, carBrand;
        ClickListener clickListener;


        public ViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;



            carModel = itemView.findViewById(R.id.carModel);
            carID = itemView.findViewById(R.id.carID);
            carBrand = itemView.findViewById(R.id.carBrand);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Do something with this click", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
