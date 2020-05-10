package com.example.firstproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Pokemon> values;
    private Context context1;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter;
        ImageView imageView;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            imageView = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Pokemon item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(List<Pokemon> myDataset,Context context) {
        values = myDataset;
        context1 = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Pokemon currentPokemon = values.get(position);
        holder.txtHeader.setText(currentPokemon.getName());

        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context1,Activity2.class);
                intent.putExtra("name",currentPokemon.getName());
                intent.putExtra("type",currentPokemon.getType());
                intent.putExtra("weaknesses",currentPokemon.getWeaknesses());
                intent.putExtra("height",currentPokemon.getHeight());
                intent.putExtra("weight",currentPokemon.getWeight());
                intent.putExtra("prev",currentPokemon.getPrev_evolution());
                intent.putExtra("next",currentPokemon.getNext_evolution());
                intent.putExtra("img",currentPokemon.getImg());
                context1.startActivity(intent);
            }
        });

        holder.txtFooter.setText(currentPokemon.getNum());
        Glide.with(context1).load(currentPokemon.getImg()).into(holder.imageView);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
