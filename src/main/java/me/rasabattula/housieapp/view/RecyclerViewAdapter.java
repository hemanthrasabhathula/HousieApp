package me.rasabattula.housieapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.TreeMap;

import me.rasabattula.housieapp.model.HousieNumber;
import me.rasabattula.housieapp.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

   private TreeMap<Integer,HousieNumber> numbersData;
    private TreeMap<Integer,Boolean> numbersMap;
    //private ArrayList<HousieNumber> numbersDataList;
    private Context mcontext;


    public RecyclerViewAdapter(TreeMap<Integer,Boolean> numbersMap, Context mcontext) {
        this.numbersMap = numbersMap;
        this.mcontext = mcontext;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.numbers_layout,parent,false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull RecyclerViewHolder holder, int position) {
        //if(position != 99)
        holder.numbersTV.setText(""+ (position+1));

        if(Objects.requireNonNull(numbersMap.get(position+1))) {
            holder.numbersTV.setBackground(mcontext.getResources().getDrawable(R.drawable.rounded_textview_drawn));
        }

        }



    @Override
    public int getItemCount() {
        return numbersMap.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView numbersTV;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            numbersTV = itemView.findViewById(R.id.idNumTV);


        }
    }


}
