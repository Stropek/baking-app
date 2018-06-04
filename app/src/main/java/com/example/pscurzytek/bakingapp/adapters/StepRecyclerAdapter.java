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
    private View selectedItem;
    private int selectedItemPosition;

    private OnStepSelectedListener stepSelectedListener;

    public StepRecyclerAdapter(Context context, ArrayList<Step> steps, OnStepSelectedListener stepSelectedListener) {
        this.context = context;
        this.steps = steps;
        this.stepSelectedListener = stepSelectedListener;
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

            if (position == 0) {
                holder.itemView.setSelected(true);
                selectedItem = holder.itemView;
                selectedItemPosition = 0;
            }
            else {
                holder.stepNumberTextView.setText(String.format("%s.", position));
            }
            holder.shortDescriptionTextView.setText(step.getShortDescription());

            holder.itemView.setOnClickListener(v -> {
                int holderPosition = holder.getAdapterPosition();
                if (selectedItemPosition != holderPosition) {
                    holder.itemView.setSelected(true);
                    selectedItem.setSelected(false);
                    selectedItem = holder.itemView;
                    selectedItemPosition = holderPosition;
                }
                stepSelectedListener.loadDetails(step);
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

        void loadDetails(Step step);
    }
}
