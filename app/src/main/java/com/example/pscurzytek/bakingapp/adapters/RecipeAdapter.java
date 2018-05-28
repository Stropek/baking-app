package com.example.pscurzytek.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    public RecipeAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);
        }

        Recipe recipe = getItem(position);
        if (recipe != null) {
            ImageView recipeImageView = convertView.findViewById(R.id.recipe_image);
            TextView nameTextView = convertView.findViewById(R.id.recipe_name);

            Picasso.get()
                    .load(recipe.getImage())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(recipeImageView);
            nameTextView.setText(recipe.getName());
        }

        return convertView;
    }
}
