package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Activity.DetailActivity;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.RecipeCardBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    public static final String MY_PREFS = "MyPrefs";
    private static final List<Integer> recipe_thumbnails = new ArrayList<Integer>() {{
        add(R.drawable.nutella_brownie_);
        add(R.drawable.brownie);
        add(R.drawable.yellow_cake);
        add(R.drawable.cheese_cake);
    }};
    private final OnItemClickListener listener;
    public List<Recipe> recipes = new ArrayList<Recipe>();
    RecipeCardBinding binding;
    private Context context;
    private SharedPreferences sharedPreferences;

    public RecipeAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_card, parent, false);
        binding = RecipeCardBinding.bind(view);
        sharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);


        return new RecipeViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
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

    /**
     * @param recipes
     */
    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Recipe position);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        /**
         * @param itemView
         */
        public RecipeViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * @param item
         * @param recipeItem
         * @param listener
         */
        public void bind(final int item, final Recipe recipeItem, final OnItemClickListener listener) {
            final String name = recipes.get(item).getName();
            final String servings = recipes.get(item).getServings();

            binding.recipeNameTv.setText(name);
            binding.servingsTv.setText(servings);
            binding.mainRecipeThumbnail.setImageResource(recipe_thumbnails.get(item));
            binding.mainRecipeThumbnail.setOnClickListener(new View.OnClickListener() {
                /**
                 *
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    listener.onItemClick(recipeItem);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("steps", recipes.get(item).getSteps());
                    bundle.putSerializable("ingredients", recipes.get(item).getIngredients());
                    bundle.putString("name", name);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("bundle", bundle);
                    intent.putExtra("position", item);
                    Gson gson = new Gson();
                    String resultJson = gson.toJson(recipes.get(item));
                    sharedPreferences.edit().putString("result", resultJson).apply();
                    context.startActivity(intent);

                }
            });
        }
    }
}
