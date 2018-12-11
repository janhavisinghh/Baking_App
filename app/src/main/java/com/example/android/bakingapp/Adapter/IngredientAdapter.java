package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.IngredientItemBinding;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    public ArrayList<Ingredients> ingredients;
    IngredientItemBinding binding;
    private Context context;

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        binding = IngredientItemBinding.bind(view);

        return new IngredientAdapter.IngredientViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
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

    /**
     * @param ingredients
     */
    public void setSteps(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        /**
         * @param itemView
         */
        public IngredientViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * @param item
         * @param ingredientsItem
         */
        public void bind(final int item, final Ingredients ingredientsItem) {
            final String ingredient_name = ingredients.get(item).getIngredient();
            final String ingredient_qty = ingredients.get(item).getQuantity();
            final String ingredient_measure = ingredients.get(item).getMeasure();
            binding.ingredientName.setText(ingredient_name);
            binding.ingredientQuantity.setText(ingredient_qty + " " + ingredient_measure);

        }
    }

}
