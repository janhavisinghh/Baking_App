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

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    public List<Steps> steps;

    private Context context;

    private final DetailsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Steps position);
    }

    public StepAdapter(DetailsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.details_card, parent, false);

        return new StepAdapter.StepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StepAdapter.StepViewHolder holder, int position) {
        holder.bind(position, steps.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }
        return steps.size();
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        private TextView step_name_tv;
        private TextView short_desc_tv;


        public StepViewHolder(View itemView) {
            super(itemView);
            step_name_tv = itemView.findViewById(R.id.heading_tv);
            short_desc_tv = itemView.findViewById(R.id.short_description_tv);
        }

        public void bind(final int item, final Steps stepsItem, final DetailsAdapter.OnItemClickListener listener) {
            final String stepName = steps.get(item).getId();
            final String description = steps.get(item).getShortDescription();
            step_name_tv.setText("Step " +stepName);
            short_desc_tv.setText(description);

        }
    }

}
