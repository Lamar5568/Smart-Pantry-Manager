package com.example.smartpantrymanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etExpiryDate;
    private Spinner spinnerUnit;
    private Button btnSaveIngredient;

    private DatabaseHelper databaseHelper;

    private int ingredientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_ingredient);

        // Connect Java to XML
        etIngredientName = findViewById(
                R.id.etIngredientName
        );

        etQuantity = findViewById(
                R.id.etQuantity
        );

        etExpiryDate = findViewById(
                R.id.etExpiryDate
        );

        spinnerUnit = findViewById(
                R.id.spinnerUnit
        );

        btnSaveIngredient = findViewById(
                R.id.btnSaveIngredient
        );

        databaseHelper = new DatabaseHelper(this);

        // Units
        String[] units = {
                "g",
                "kg",
                "ml",
                "L",
                "pcs",
                "cup",
                "tbsp",
                "tsp"
        };

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );

        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnit.setAdapter(unitAdapter);

        // Get ingredient information from MainActivity
        ingredientId = getIntent().getIntExtra(
                "ingredient_id",
                -1
        );

        String name = getIntent().getStringExtra(
                "ingredient_name"
        );

        double quantity = getIntent().getDoubleExtra(
                "ingredient_quantity",
                0
        );

        String unit = getIntent().getStringExtra(
                "ingredient_unit"
        );

        String expiryDate = getIntent().getStringExtra(
                "ingredient_expiry_date"
        );

        // Put existing information into fields
        etIngredientName.setText(name);

        etQuantity.setText(
                String.valueOf(quantity)
        );

        etExpiryDate.setText(
                expiryDate
        );

        // Select the existing unit
        if (unit != null) {

            for (int i = 0; i < units.length; i++) {

                if (units[i].equals(unit)) {

                    spinnerUnit.setSelection(i);
                    break;
                }
            }
        }

        // Save changes
        btnSaveIngredient.setOnClickListener(
                v -> updateIngredient()
        );
    }

    private void updateIngredient() {

        String name = etIngredientName.getText()
                .toString()
                .trim();

        String quantityText = etQuantity.getText()
                .toString()
                .trim();

        String unit = spinnerUnit.getSelectedItem()
                .toString();

        String expiryDate = etExpiryDate.getText()
                .toString()
                .trim();

        // Validate name
        if (TextUtils.isEmpty(name)) {

            etIngredientName.setError(
                    "Enter an ingredient name"
            );

            etIngredientName.requestFocus();

            return;
        }

        // Validate quantity
        if (TextUtils.isEmpty(quantityText)) {

            etQuantity.setError(
                    "Enter a quantity"
            );

            etQuantity.requestFocus();

            return;
        }

        double quantity;

        try {

            quantity = Double.parseDouble(
                    quantityText
            );

        } catch (NumberFormatException e) {

            etQuantity.setError(
                    "Enter a valid quantity"
            );

            etQuantity.requestFocus();

            return;
        }

        // Quantity must be greater than zero
        if (quantity <= 0) {

            etQuantity.setError(
                    "Quantity must be greater than 0"
            );

            etQuantity.requestFocus();

            return;
        }

        // Update database
        boolean updated =
                databaseHelper.updateIngredient(
                        ingredientId,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

        if (updated) {

            Toast.makeText(
                    this,
                    "Ingredient updated successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to update ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}