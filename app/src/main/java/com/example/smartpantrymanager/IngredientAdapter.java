package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {

        if (ingredientList != null) {
            this.ingredientList = ingredientList;
        } else {
            this.ingredientList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder,
            int position) {

        Ingredient ingredient = ingredientList.get(position);

        // Display ingredient name
        holder.tvIngredientName.setText(
                ingredient.getName()
        );

        // Display quantity and unit
        String details = ingredient.getQuantity()
                + " "
                + ingredient.getUnit();

        holder.tvIngredientDetails.setText(details);

        // EDIT BUTTON
        holder.btnEditIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    EditIngredientActivity.class
            );

            intent.putExtra(
                    "ingredient_id",
                    ingredient.getId()
            );

            intent.putExtra(
                    "ingredient_name",
                    ingredient.getName()
            );

            intent.putExtra(
                    "ingredient_quantity",
                    ingredient.getQuantity()
            );

            intent.putExtra(
                    "ingredient_unit",
                    ingredient.getUnit()
            );

            intent.putExtra(
                    "ingredient_expiry_date",
                    ingredient.getExpiryDate()
            );

            v.getContext().startActivity(intent);
        });

        // DELETE BUTTON
        holder.btnDeleteIngredient.setOnClickListener(v -> {

            Context context = v.getContext();

            new AlertDialog.Builder(context)
                    .setTitle("Delete Ingredient")
                    .setMessage(
                            "Are you sure you want to delete "
                                    + ingredient.getName()
                                    + "?"
                    )
                    .setPositiveButton(
                            "Delete",
                            (dialog, which) -> {

                                DatabaseHelper databaseHelper =
                                        new DatabaseHelper(context);

                                boolean deleted =
                                        databaseHelper.deleteIngredient(
                                                ingredient.getId()
                                        );

                                if (deleted) {

                                    Toast.makeText(
                                            context,
                                            "Ingredient deleted successfully",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    // Get the current position
                                    int currentPosition =
                                            holder.getAdapterPosition();

                                    // Remove the item from the list
                                    if (currentPosition != RecyclerView.NO_POSITION) {

                                        ingredientList.remove(currentPosition);

                                        notifyItemRemoved(currentPosition);
                                    }

                                } else {

                                    Toast.makeText(
                                            context,
                                            "Failed to delete ingredient",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    // Update the ingredients displayed by the RecyclerView
    public void updateIngredients(List<Ingredient> newIngredients) {

        if (newIngredients != null) {
            ingredientList = new ArrayList<>(newIngredients);
        } else {
            ingredientList = new ArrayList<>();
        }

        notifyDataSetChanged();
    }

    // ViewHolder
    public static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvIngredientDetails;

        ImageButton btnEditIngredient;
        ImageButton btnDeleteIngredient;

        public IngredientViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvIngredientName =
                    itemView.findViewById(
                            R.id.tvIngredientName
                    );

            tvIngredientDetails =
                    itemView.findViewById(
                            R.id.tvIngredientDetails
                    );

            btnEditIngredient =
                    itemView.findViewById(
                            R.id.btnEditIngredient
                    );

            btnDeleteIngredient =
                    itemView.findViewById(
                            R.id.btnDeleteIngredient
                    );
        }
    }
}