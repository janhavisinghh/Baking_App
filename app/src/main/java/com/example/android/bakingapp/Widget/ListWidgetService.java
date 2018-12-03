package com.example.android.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListWidgetService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListWidgetFactory(getApplicationContext(), intent);
    }

    public class ListWidgetFactory implements RemoteViewsFactory{
        private ArrayList<Ingredients> ingredients;
        private int position;
        private String name;
        private int appWidgetId;
        private Context context;
        ListWidgetFactory(Context context, Intent intent){
            this.context = context;
            Bundle args = intent.getBundleExtra("bundle");
            ingredients = (ArrayList<Ingredients>) args.getSerializable("ingredients");
            position = args.getInt("position",-1);
            name = args.getString("name");
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if(ingredients.size()==0)
                return 0;
            else
                return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if(ingredients==null||ingredients.size()==0) return null;
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            Ingredients ingredient = ingredients.get(position);
            views.setTextViewText(R.id.detail_ingredient_tv, ingredient.getIngredient());
            views.setTextViewText(R.id.detail_quantity_tv, ingredient.getQuantity()+" "+ ingredient.getMeasure());
            Bundle extras = new Bundle();
            extras.putSerializable(MainActivity.KEY_INGREDIENT, ingredients);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.detail_ingredient_tv, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}