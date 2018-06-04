package com.example.pscurzytek.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

public class StepRecyclerAdapter extends RecyclerView.Adapter<StepRecyclerAdapter.ViewHolder> {

    private final Context context;
    private List<Step> steps;
    private View currentItem;
    private int currentStepPosition;

    private OnStepSelectedListener stepSelectedListener;

    public StepRecyclerAdapter(Context context, ArrayList<Step> steps, int currentStepPosition, OnStepSelectedListener stepSelectedListener) {
        this.context = context;
        this.steps = steps;
        this.stepSelectedListener = stepSelectedListener;
        this.currentStepPosition = currentStepPosition;
    }

    @NonNull
    @Override
    public StepRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.step_recycler_item, parent, false);

        return new StepRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepRecyclerAdapter.ViewHolder holder, int position) {
        List<Step> steps = this.steps;

        if (steps.size() >= position - 1) {
            Step step = steps.get(position);

            if (position == currentStepPosition) {
                holder.itemView.setSelected(true);
                currentItem = holder.itemView;
                currentStepPosition = 0;
            }
            if (position != 0) {
                holder.stepNumberTextView.setText(String.format("%s.", position));
            }
            holder.shortDescriptionTextView.setText(step.getShortDescription());

            holder.itemView.setOnClickListener(v -> {
                int holderPosition = holder.getAdapterPosition();
                if (currentStepPosition != holderPosition) {
                    holder.itemView.setSelected(true);
                    currentItem.setSelected(false);
                    currentItem = holder.itemView;
                    currentStepPosition = holderPosition;
                }
                stepSelectedListener.loadDetails(step, currentStepPosition);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (steps != null) {
            return steps.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView stepNumberTextView;
        TextView shortDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);

            stepNumberTextView = itemView.findViewById(R.id.step_number_textView);
            shortDescriptionTextView = itemView.findViewById(R.id.short_description_textView);
        }
    }

    public interface OnStepSelectedListener {

        void loadDetails(Step step, int currentStepPosition);
    }
}
