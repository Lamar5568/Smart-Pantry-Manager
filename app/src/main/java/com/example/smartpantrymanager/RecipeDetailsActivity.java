package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private TextView tvRecipeDetailsName;
    private TextView tvRecipeDetailsIngredients;
    private TextView tvRecipeDetailsInstructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_details);


        // ========================================================
        // CONNECT UI ELEMENTS
        // ========================================================

        tvRecipeDetailsName =
                findViewById(R.id.tvRecipeDetailsName);

        tvRecipeDetailsIngredients =
                findViewById(R.id.tvRecipeDetailsIngredients);

        tvRecipeDetailsInstructions =
                findViewById(R.id.tvRecipeDetailsInstructions);


        // ========================================================
        // DATABASE
        // ========================================================

        databaseHelper =
                new DatabaseHelper(this);


        // ========================================================
        // GET RECIPE ID
        // ========================================================

        int recipeId =
                getIntent().getIntExtra(
                        "RECIPE_ID",
                        -1
                );


        // ========================================================
        // LOAD RECIPE
        // ========================================================

        if (recipeId != -1) {

            loadRecipeDetails(recipeId);
        }
    }


    // ============================================================
    // LOAD RECIPE DETAILS
    // ============================================================

    private void loadRecipeDetails(int recipeId) {

        Recipe recipe =
                databaseHelper.getRecipeById(
                        recipeId
                );


        if (recipe == null) {

            tvRecipeDetailsName.setText(
                    "Recipe not found"
            );

            tvRecipeDetailsIngredients.setText(
                    ""
            );

            tvRecipeDetailsInstructions.setText(
                    ""
            );

            return;
        }


        // ========================================================
        // RECIPE NAME
        // ========================================================

        tvRecipeDetailsName.setText(
                recipe.getName()
        );


        // ========================================================
        // REQUIRED INGREDIENTS
        // ========================================================

        List<RecipeIngredient> ingredients =
                databaseHelper.getRecipeIngredients(
                        recipeId
                );


        StringBuilder ingredientsText =
                new StringBuilder();


        for (RecipeIngredient ingredient : ingredients) {

            ingredientsText
                    .append("• ")
                    .append(ingredient.getName())
                    .append(" - ")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }


        tvRecipeDetailsIngredients.setText(
                ingredientsText.toString()
        );


        // ========================================================
        // COOKING INSTRUCTIONS
        // ========================================================

        tvRecipeDetailsInstructions.setText(
                recipe.getInstructions()
        );
    }
}