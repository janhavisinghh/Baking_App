package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.Activity.DetailActivity;
import com.example.android.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    public List<Recipe> recipes = new ArrayList<Recipe>();
    private Context context;
    private static final List<Integer> recipe_thumbnails  = new ArrayList<Integer>() {{
        add(R.drawable.nutella_brownie_);
        add(R.drawable.brownie);
        add(R.drawable.yellow_cake);
        add(R.drawable.cheese_cake);
    }};

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Recipe position);
    }

    public RecipeAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_card, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        holder.bind(position, recipes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        private TextView name_tv;
        private TextView servings_tv;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_recipe_thumbnail);
            servings_tv = itemView.findViewById(R.id.servings_tv);
            name_tv = itemView.findViewById(R.id.recipe_name_tv);
        }

        public void bind(final int item, final Recipe recipeItem, final OnItemClickListener listener) {
            final String name = recipes.get(item).getName();
            final String servings = recipes.get(item).getServings();

            name_tv.setText(name);
            servings_tv.setText(servings);
            imageView.setImageResource(recipe_thumbnails.get(item));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(recipeItem);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("steps", recipes.get(item).getSteps());
                    bundle.putSerializable("ingredients", recipes.get(item).getIngredients());
                    bundle.putString("name", name);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("position",item);
                    context.startActivity(intent);

                }
            });
        }
    }
}
