package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIngredients;
    private IngredientAdapter ingredientAdapter;
    private DatabaseHelper databaseHelper;
    private Button btnAddIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry_list);

        recyclerViewIngredients = findViewById(R.id.recyclerViewIngredients);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        databaseHelper = new DatabaseHelper(this);

        recyclerViewIngredients.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadIngredients();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null) {
            loadIngredients();
        }
    }

    private void loadIngredients() {

        List<Ingredient> ingredients =
                databaseHelper.getAllIngredients();

        ingredientAdapter = new IngredientAdapter(ingredients);

        recyclerViewIngredients.setAdapter(ingredientAdapter);
    }
}