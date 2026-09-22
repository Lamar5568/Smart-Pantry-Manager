package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.Ingredient;

import java.util.List;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);

        holder.tvIngredientName.setText(ingredient.getName());

        String details = ingredient.getQuantity()
                + " " + ingredient.getUnit();

        holder.tvIngredientDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvIngredientDetails;
        ImageButton btnEditIngredient;
        ImageButton btnDeleteIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredientName = itemView.findViewById(R.id.tvIngredientName);
            tvIngredientDetails = itemView.findViewById(R.id.tvIngredientDetails);
            btnEditIngredient = itemView.findViewById(R.id.btnEditIngredient);
            btnDeleteIngredient = itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}
