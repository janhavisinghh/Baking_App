package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    public ArrayList<Ingredients> ingredients;

    private Context context;

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);

        return new IngredientAdapter.IngredientViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final IngredientAdapter.IngredientViewHolder holder, int position) {
        holder.bind(position, ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) {
            return 0;
        }
        return ingredients.size();
    }

    public void setSteps(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView ingred_name_tv;
        private TextView ingred_qty_tv;


        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingred_name_tv = itemView.findViewById(R.id.ingredient_name);
            ingred_qty_tv = itemView.findViewById(R.id.ingredient_quantity);
        }

        public void bind(final int item, final Ingredients ingredientsItem) {
            final String ingredient_name = ingredients.get(item).getIngredient();
            final String ingredient_qty = ingredients.get(item).getQuantity();
            final String ingredient_measure = ingredients.get(item).getMeasure();
            ingred_name_tv.setText(ingredient_name);
            ingred_qty_tv.setText(ingredient_qty +" "+ingredient_measure);

        }
    }

}
