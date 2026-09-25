package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSuggestedRecipes;
    private RecipeAdapter recipeAdapter;
    private DatabaseHelper databaseHelper;
    private TextView tvSuggestedRecipesTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);


        // ========================================================
        // CONNECT UI ELEMENTS
        // ========================================================

        recyclerViewSuggestedRecipes =
                findViewById(R.id.recyclerViewSuggestedRecipes);

        tvSuggestedRecipesTitle =
                findViewById(R.id.tvSuggestedRecipesTitle);


        // ========================================================
        // DATABASE
        // ========================================================

        databaseHelper =
                new DatabaseHelper(this);


        // ========================================================
        // RECYCLER VIEW
        // ========================================================

        recyclerViewSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );


        recipeAdapter =
                new RecipeAdapter(
                        new ArrayList<>()
                );


        recyclerViewSuggestedRecipes.setAdapter(
                recipeAdapter
        );


        // ========================================================
        // LOAD SUGGESTED RECIPES
        // ========================================================

        loadSuggestedRecipes();
    }


    // ============================================================
    // LOAD SUGGESTED RECIPES
    // ============================================================

    private void loadSuggestedRecipes() {

        List<Recipe> suggestedRecipes =
                databaseHelper.getSuggestedRecipes();

        recipeAdapter.updateRecipes(
                suggestedRecipes
        );


        // ========================================================
        // EMPTY STATE
        // ========================================================

        if (suggestedRecipes.isEmpty()) {

            tvSuggestedRecipesTitle.setText(
                    "Suggested Recipes\n\n" +
                            "No recipes can be made with your current pantry."
            );

        } else {

            tvSuggestedRecipesTitle.setText(
                    "Suggested Recipes"
            );
        }
    }
}