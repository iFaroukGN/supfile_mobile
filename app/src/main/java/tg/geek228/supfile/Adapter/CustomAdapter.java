package tg.geek228.supfile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tg.geek228.supfile.Interface.OnItemClickListener;
import tg.geek228.supfile.Model.Ressource;
import tg.geek228.supfile.R;
import tg.geek228.supfile.ViewHolder.ViewHolder;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater inflater;
        private ArrayList<Ressource> data;
        private Context context;
        private ViewHolder myHolder;
        public CustomAdapter (Context context, ArrayList<Ressource> data) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            this.data = data;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            View v = inflater.inflate(R.layout.item_layout, parent, false);
            holder = new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            myHolder = (ViewHolder) holder;

            System.out.println("res "+data.get(position).getName());
            myHolder.getResourceName().setText(data.get(position).getName());

            myHolder.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onCLick(View view, int position) {

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            });

        }


        @Override
        public int getItemCount() {
            return data.size();
        }
    }