package com.ilja.carservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    private ClickListener mClickListener;
    private ArrayList<Car> carList;
    private Context context;


    public Adapter(Context ct, ArrayList<Car> localCarList, ClickListener clickListener) {
        context = ct;
        carList = localCarList;
        ArrayList<Car> carListAll = new ArrayList<>(carList);
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Car> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(carList);
            } else {
                for (Car car: carList) {
                    if (car.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(car);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            carList.clear();
            carList.addAll((Collection<? extends Car>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView carModel, carID, carBrand;
        ClickListener clickListener;


        public ViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);



            carModel = itemView.findViewById(R.id.carModel);
            carID = itemView.findViewById(R.id.carID);
            carBrand = itemView.findViewById(R.id.carBrand);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "Do something with this click", Toast.LENGTH_SHORT).show();
//
//
//                }
//            });

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
