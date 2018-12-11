package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.DetailsCardBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailViewHolder> {
    private final DetailsAdapter.OnItemClickListener listener;
    public List<Steps> steps;
    DetailsCardBinding binding;
    private Context context;

    public DetailsAdapter(DetailsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public DetailsAdapter.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.details_card, parent, false);
        binding = DetailsCardBinding.bind(view);

        return new DetailsAdapter.DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailsAdapter.DetailViewHolder holder, int position) {
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public DetailViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(final int item, final Steps stepsItem, final DetailsAdapter.OnItemClickListener listener) {
            final String stepName = steps.get(item).getId();
            final String description = steps.get(item).getShortDescription();
            binding.headingTv.setText("Step " + stepName);
            binding.shortDescriptionTv.setText(description);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}