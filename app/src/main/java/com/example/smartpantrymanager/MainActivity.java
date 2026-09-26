package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIngredients;
    private IngredientAdapter ingredientAdapter;
    private DatabaseHelper databaseHelper;

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnSettings;
    private Button btnToolbarMenu;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry_list);

        // ============================================================
        // TOOLBAR
        // ============================================================

        toolbar =
                findViewById(R.id.toolbar);

        btnToolbarMenu =
                findViewById(R.id.btnToolbarMenu);

        btnToolbarMenu.setOnClickListener(v -> {

            PopupMenu popupMenu =
                    new PopupMenu(
                            MainActivity.this,
                            btnToolbarMenu
                    );

            popupMenu.getMenuInflater().inflate(
                    R.menu.main_menu,
                    popupMenu.getMenu()
            );

            popupMenu.setOnMenuItemClickListener(item -> {

                int itemId =
                        item.getItemId();

                // Open Suggested Recipes
                if (itemId ==
                        R.id.menuSuggestedRecipes) {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    SuggestedRecipesActivity.class
                            );

                    startActivity(intent);

                    return true;
                }

                // Open Settings
                if (itemId ==
                        R.id.menuSettings) {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    SettingsActivity.class
                            );

                    startActivity(intent);

                    return true;
                }

                return false;
            });

            popupMenu.show();
        });

        // ============================================================
        // RECYCLER VIEW
        // ============================================================

        recyclerViewIngredients =
                findViewById(
                        R.id.recyclerViewIngredients
                );

        // ============================================================
        // BUTTONS
        // ============================================================

        btnAddIngredient =
                findViewById(
                        R.id.btnAddIngredient
                );

        btnSuggestedRecipes =
                findViewById(
                        R.id.btnSuggestedRecipes
                );

        btnSettings =
                findViewById(
                        R.id.btnSettings
                );

        // ============================================================
        // DATABASE
        // ============================================================

        databaseHelper =
                new DatabaseHelper(this);

        // Make sure the default recipes exist
        databaseHelper.seedDefaultRecipes();

        // ============================================================
        // RECYCLER VIEW SETUP
        // ============================================================

        recyclerViewIngredients.setLayoutManager(
                new LinearLayoutManager(this)
        );

        ingredientAdapter =
                new IngredientAdapter(
                        new ArrayList<>()
                );

        recyclerViewIngredients.setAdapter(
                ingredientAdapter
        );

        // ============================================================
        // ADD INGREDIENT
        // ============================================================

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            AddIngredientActivity.class
                    );

            startActivity(intent);
        });

        // ============================================================
        // SUGGESTED RECIPES
        // ============================================================

        btnSuggestedRecipes.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SuggestedRecipesActivity.class
                    );

            startActivity(intent);
        });

        // ============================================================
        // SETTINGS
        // ============================================================

        btnSettings.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });

        // ============================================================
        // LOAD PANTRY INGREDIENTS
        // ============================================================

        loadIngredients();
    }

    // ================================================================
    // REFRESH PANTRY WHEN RETURNING TO THIS SCREEN
    // ================================================================

    @Override
    protected void onResume() {

        super.onResume();

        if (databaseHelper != null) {

            loadIngredients();
        }
    }

    // ================================================================
    // LOAD INGREDIENTS FROM DATABASE
    // ================================================================

    private void loadIngredients() {

        List<Ingredient> ingredients =
                databaseHelper.getAllIngredients();

        ingredientAdapter.updateIngredients(
                ingredients
        );
    }
}