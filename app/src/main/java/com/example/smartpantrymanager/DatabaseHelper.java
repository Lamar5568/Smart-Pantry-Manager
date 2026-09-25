package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ============================================================
    // DATABASE INFORMATION
    // ============================================================

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;


    // ============================================================
    // PANTRY INGREDIENTS TABLE
    // ============================================================

    private static final String TABLE_INGREDIENTS = "ingredients";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_EXPIRY_DATE = "expiry_date";


    // ============================================================
    // RECIPES TABLE
    // ============================================================

    private static final String TABLE_RECIPES = "recipes";

    private static final String COLUMN_RECIPE_ID = "id";
    private static final String COLUMN_RECIPE_NAME = "name";
    private static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";


    // ============================================================
    // RECIPE INGREDIENTS TABLE
    // ============================================================

    private static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    private static final String COLUMN_RECIPE_INGREDIENT_ID =
            "id";

    private static final String COLUMN_RECIPE_ID_FK =
            "recipe_id";

    private static final String COLUMN_RECIPE_INGREDIENT_NAME =
            "name";

    private static final String COLUMN_RECIPE_INGREDIENT_QUANTITY =
            "quantity";

    private static final String COLUMN_RECIPE_INGREDIENT_UNIT =
            "unit";


    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public DatabaseHelper(Context context) {
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }


    // ============================================================
    // CREATE DATABASE
    // ============================================================

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Enable foreign keys
        db.execSQL("PRAGMA foreign_keys=ON");


        // --------------------------------------------------------
        // CREATE PANTRY INGREDIENTS TABLE
        // --------------------------------------------------------

        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +

                        COLUMN_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_NAME +
                        " TEXT NOT NULL, " +

                        COLUMN_QUANTITY +
                        " REAL NOT NULL, " +

                        COLUMN_UNIT +
                        " TEXT NOT NULL, " +

                        COLUMN_EXPIRY_DATE +
                        " TEXT" +

                        ")";

        db.execSQL(createIngredientsTable);


        // --------------------------------------------------------
        // CREATE RECIPES TABLE
        // --------------------------------------------------------

        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +

                        COLUMN_RECIPE_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_RECIPE_NAME +
                        " TEXT NOT NULL, " +

                        COLUMN_RECIPE_INSTRUCTIONS +
                        " TEXT NOT NULL" +

                        ")";

        db.execSQL(createRecipesTable);


        // --------------------------------------------------------
        // CREATE RECIPE INGREDIENTS TABLE
        // --------------------------------------------------------

        String createRecipeIngredientsTable =
                "CREATE TABLE " +
                        TABLE_RECIPE_INGREDIENTS + " (" +

                        COLUMN_RECIPE_INGREDIENT_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COLUMN_RECIPE_ID_FK +
                        " INTEGER NOT NULL, " +

                        COLUMN_RECIPE_INGREDIENT_NAME +
                        " TEXT NOT NULL, " +

                        COLUMN_RECIPE_INGREDIENT_QUANTITY +
                        " REAL NOT NULL, " +

                        COLUMN_RECIPE_INGREDIENT_UNIT +
                        " TEXT NOT NULL, " +

                        "FOREIGN KEY (" +
                        COLUMN_RECIPE_ID_FK +
                        ") REFERENCES " +
                        TABLE_RECIPES +
                        "(" +
                        COLUMN_RECIPE_ID +
                        ")" +

                        " ON DELETE CASCADE" +

                        ")";

        db.execSQL(createRecipeIngredientsTable);
    }


    // ============================================================
    // DATABASE UPGRADE
    // ============================================================

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        /*
         * IMPORTANT:
         *
         * We do NOT delete the ingredients table here.
         *
         * This protects existing pantry data.
         */

        if (oldVersion < 2) {

            // Create recipes table
            String createRecipesTable =
                    "CREATE TABLE IF NOT EXISTS " +
                            TABLE_RECIPES + " (" +

                            COLUMN_RECIPE_ID +
                            " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                            COLUMN_RECIPE_NAME +
                            " TEXT NOT NULL, " +

                            COLUMN_RECIPE_INSTRUCTIONS +
                            " TEXT NOT NULL" +

                            ")";

            db.execSQL(createRecipesTable);


            // Create recipe ingredients table
            String createRecipeIngredientsTable =
                    "CREATE TABLE IF NOT EXISTS " +
                            TABLE_RECIPE_INGREDIENTS + " (" +

                            COLUMN_RECIPE_INGREDIENT_ID +
                            " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                            COLUMN_RECIPE_ID_FK +
                            " INTEGER NOT NULL, " +

                            COLUMN_RECIPE_INGREDIENT_NAME +
                            " TEXT NOT NULL, " +

                            COLUMN_RECIPE_INGREDIENT_QUANTITY +
                            " REAL NOT NULL, " +

                            COLUMN_RECIPE_INGREDIENT_UNIT +
                            " TEXT NOT NULL, " +

                            "FOREIGN KEY (" +
                            COLUMN_RECIPE_ID_FK +
                            ") REFERENCES " +
                            TABLE_RECIPES +
                            "(" +
                            COLUMN_RECIPE_ID +
                            ")" +

                            " ON DELETE CASCADE" +

                            ")";

            db.execSQL(createRecipeIngredientsTable);
        }
    }


    // ============================================================
    // ADD PANTRY INGREDIENT
    // ============================================================

    public boolean addIngredient(
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_NAME,
                name
        );

        values.put(
                COLUMN_QUANTITY,
                quantity
        );

        values.put(
                COLUMN_UNIT,
                unit
        );

        values.put(
                COLUMN_EXPIRY_DATE,
                expiryDate
        );

        long result =
                db.insert(
                        TABLE_INGREDIENTS,
                        null,
                        values
                );

        return result != -1;
    }


    // ============================================================
    // UPDATE PANTRY INGREDIENT
    // ============================================================

    public boolean updateIngredient(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_NAME,
                name
        );

        values.put(
                COLUMN_QUANTITY,
                quantity
        );

        values.put(
                COLUMN_UNIT,
                unit
        );

        values.put(
                COLUMN_EXPIRY_DATE,
                expiryDate
        );

        int rowsUpdated =
                db.update(
                        TABLE_INGREDIENTS,
                        values,
                        COLUMN_ID + " = ?",
                        new String[]{
                                String.valueOf(id)
                        }
                );

        return rowsUpdated > 0;
    }


    // ============================================================
    // DELETE PANTRY INGREDIENT
    // ============================================================

    public boolean deleteIngredient(int id) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        int rowsDeleted =
                db.delete(
                        TABLE_INGREDIENTS,
                        COLUMN_ID + " = ?",
                        new String[]{
                                String.valueOf(id)
                        }
                );

        return rowsDeleted > 0;
    }


    // ============================================================
    // GET ALL PANTRY INGREDIENTS
    // ============================================================

    public List<Ingredient> getAllIngredients() {

        List<Ingredient> ingredients =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM " +
                                TABLE_INGREDIENTS,
                        null
                );

        try {

            if (cursor.moveToFirst()) {

                do {

                    int id =
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_ID
                                    )
                            );

                    String name =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_NAME
                                    )
                            );

                    double quantity =
                            cursor.getDouble(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_QUANTITY
                                    )
                            );

                    String unit =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_UNIT
                                    )
                            );

                    String expiryDate =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_EXPIRY_DATE
                                    )
                            );

                    Ingredient ingredient =
                            new Ingredient(
                                    id,
                                    name,
                                    quantity,
                                    unit,
                                    expiryDate
                            );

                    ingredients.add(
                            ingredient
                    );

                } while (cursor.moveToNext());
            }

        } finally {

            cursor.close();
        }

        return ingredients;
    }


    // ============================================================
    // ADD RECIPE
    // ============================================================

    public long addRecipe(
            String name,
            String instructions) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_RECIPE_NAME,
                name
        );

        values.put(
                COLUMN_RECIPE_INSTRUCTIONS,
                instructions
        );

        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }


    // ============================================================
    // ADD RECIPE INGREDIENT
    // ============================================================

    public long addRecipeIngredient(
            long recipeId,
            String name,
            double quantity,
            String unit) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COLUMN_RECIPE_ID_FK,
                recipeId
        );

        values.put(
                COLUMN_RECIPE_INGREDIENT_NAME,
                name
        );

        values.put(
                COLUMN_RECIPE_INGREDIENT_QUANTITY,
                quantity
        );

        values.put(
                COLUMN_RECIPE_INGREDIENT_UNIT,
                unit
        );

        return db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }


    // ============================================================
    // CHECK WHETHER RECIPES EXIST
    // ============================================================

    public boolean recipesExist() {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM " +
                                TABLE_RECIPES,
                        null
                );

        try {

            if (cursor.moveToFirst()) {

                return cursor.getInt(0) > 0;
            }

        } finally {

            cursor.close();
        }

        return false;
    }


    // ============================================================
    // GET ALL RECIPES
    // ============================================================

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM " +
                                TABLE_RECIPES +
                                " ORDER BY " +
                                COLUMN_RECIPE_NAME +
                                " ASC",
                        null
                );

        try {

            if (cursor.moveToFirst()) {

                do {

                    int id =
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_RECIPE_ID
                                    )
                            );

                    String name =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_RECIPE_NAME
                                    )
                            );

                    String instructions =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_RECIPE_INSTRUCTIONS
                                    )
                            );

                    Recipe recipe =
                            new Recipe(
                                    id,
                                    name,
                                    instructions
                            );

                    recipes.add(
                            recipe
                    );

                } while (cursor.moveToNext());
            }

        } finally {

            cursor.close();
        }

        return recipes;
    }


    // ============================================================
    // GET SUGGESTED RECIPES
    // ============================================================

    public List<Recipe> getSuggestedRecipes() {

        List<Recipe> suggestedRecipes =
                new ArrayList<>();

        List<Recipe> allRecipes =
                getAllRecipes();

        for (Recipe recipe : allRecipes) {

            if (hasAllRequiredIngredients(
                    recipe.getId())) {

                suggestedRecipes.add(recipe);
            }
        }

        return suggestedRecipes;
    }


    // ============================================================
    // CHECK WHETHER A RECIPE CAN BE MADE
    // ============================================================

    private boolean hasAllRequiredIngredients(
            int recipeId) {

        List<RecipeIngredient> recipeIngredients =
                getRecipeIngredients(recipeId);

        /*
         * A recipe without ingredients cannot be considered
         * a valid pantry match.
         */

        if (recipeIngredients.isEmpty()) {

            return false;
        }

        for (RecipeIngredient recipeIngredient :
                recipeIngredients) {

            boolean ingredientAvailable =
                    hasEnoughPantryQuantity(
                            recipeIngredient.getName(),
                            recipeIngredient.getQuantity(),
                            recipeIngredient.getUnit()
                    );

            if (!ingredientAvailable) {

                return false;
            }
        }

        return true;
    }


    // ============================================================
    // CHECK PANTRY QUANTITY FOR ONE INGREDIENT
    // ============================================================

    private boolean hasEnoughPantryQuantity(
            String requiredName,
            double requiredQuantity,
            String requiredUnit) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        /*
         * LOWER() and TRIM() make ingredient matching
         * case-insensitive and ignore accidental spaces.
         *
         * Example:
         *
         * "Flour"
         * "flour"
         * " FLOUR "
         *
         * are treated as the same ingredient.
         */

        Cursor cursor =
                db.rawQuery(
                        "SELECT SUM(" +
                                COLUMN_QUANTITY +
                                ") " +
                                "FROM " +
                                TABLE_INGREDIENTS +
                                " WHERE LOWER(TRIM(" +
                                COLUMN_NAME +
                                ")) = LOWER(TRIM(?)) " +
                                "AND LOWER(TRIM(" +
                                COLUMN_UNIT +
                                ")) = LOWER(TRIM(?))",
                        new String[]{
                                requiredName,
                                requiredUnit
                        }
                );

        try {

            if (cursor.moveToFirst()) {

                /*
                 * SUM() returns NULL when there are no
                 * matching pantry ingredients.
                 */

                if (cursor.isNull(0)) {

                    return false;
                }

                double availableQuantity =
                        cursor.getDouble(0);

                return availableQuantity >=
                        requiredQuantity;
            }

        } finally {

            cursor.close();
        }

        return false;
    }


    // ============================================================
    // GET INGREDIENTS FOR A RECIPE
    // ============================================================

    public List<RecipeIngredient> getRecipeIngredients(
            int recipeId) {

        List<RecipeIngredient> ingredients =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM " +
                                TABLE_RECIPE_INGREDIENTS +
                                " WHERE " +
                                COLUMN_RECIPE_ID_FK +
                                " = ?" +
                                " ORDER BY " +
                                COLUMN_RECIPE_INGREDIENT_NAME +
                                " ASC",
                        new String[]{
                                String.valueOf(recipeId)
                        }
                );

        try {

            if (cursor.moveToFirst()) {

                do {

                    int id =
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_RECIPE_INGREDIENT_ID
                                    )
                            );

                    String name =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_RECIPE_INGREDIENT_NAME
                                    )
                            );

                    double quantity =
                            cursor.getDouble(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_RECIPE_INGREDIENT_QUANTITY
                                    )
                            );

                    String unit =
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                            COLUMN_RECIPE_INGREDIENT_UNIT
                                    )
                            );

                    RecipeIngredient ingredient =
                            new RecipeIngredient(
                                    id,
                                    recipeId,
                                    name,
                                    quantity,
                                    unit
                            );

                    ingredients.add(
                            ingredient
                    );

                } while (cursor.moveToNext());
            }

        } finally {

            cursor.close();
        }

        return ingredients;
    }


    // ============================================================
    // GET SINGLE RECIPE
    // ============================================================

    public Recipe getRecipeById(int recipeId) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM " +
                                TABLE_RECIPES +
                                " WHERE " +
                                COLUMN_RECIPE_ID +
                                " = ?",
                        new String[]{
                                String.valueOf(recipeId)
                        }
                );

        try {

            if (cursor.moveToFirst()) {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_NAME
                                )
                        );

                String instructions =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COLUMN_RECIPE_INSTRUCTIONS
                                )
                        );

                return new Recipe(
                        id,
                        name,
                        instructions
                );
            }

        } finally {

            cursor.close();
        }

        return null;
    }


    // ============================================================
    // DELETE ALL RECIPES
    // ============================================================

    public void deleteAllRecipes() {

        SQLiteDatabase db =
                this.getWritableDatabase();

        db.delete(
                TABLE_RECIPES,
                null,
                null
        );
    }


    // ============================================================
    // SEED DEFAULT RECIPES
    // ============================================================

    public void seedDefaultRecipes() {

        // Do not insert duplicate recipes.
        if (recipesExist()) {
            return;
        }


        // --------------------------------------------------------
        // 1. Pancakes
        // --------------------------------------------------------

        long recipeId = addRecipe(
                "Pancakes",
                "Mix the flour, sugar and baking powder. "
                        + "Add the milk and eggs and mix until smooth. "
                        + "Cook spoonfuls of batter in a lightly heated "
                        + "pan until golden on both sides."
        );

        addRecipeIngredient(recipeId, "Flour", 200, "g");
        addRecipeIngredient(recipeId, "Milk", 250, "ml");
        addRecipeIngredient(recipeId, "Eggs", 2, "pcs");
        addRecipeIngredient(recipeId, "Sugar", 30, "g");


        // --------------------------------------------------------
        // 2. Omelette
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Omelette",
                "Beat the eggs. Add salt and pepper. "
                        + "Cook in a heated pan and add the cheese "
                        + "and tomato. Fold and serve."
        );

        addRecipeIngredient(recipeId, "Eggs", 3, "pcs");
        addRecipeIngredient(recipeId, "Cheese", 50, "g");
        addRecipeIngredient(recipeId, "Tomato", 1, "pcs");


        // --------------------------------------------------------
        // 3. Tomato Pasta
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Tomato Pasta",
                "Cook the pasta until tender. Fry the onion and "
                        + "garlic, then add the tomatoes. Simmer "
                        + "and combine with the cooked pasta."
        );

        addRecipeIngredient(recipeId, "Pasta", 200, "g");
        addRecipeIngredient(recipeId, "Tomato", 2, "pcs");
        addRecipeIngredient(recipeId, "Onion", 1, "pcs");
        addRecipeIngredient(recipeId, "Garlic", 2, "pcs");


        // --------------------------------------------------------
        // 4. Chicken Pasta
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Chicken Pasta",
                "Cook the pasta. Cook the chicken in a pan until "
                        + "completely cooked. Add the onion and cream, "
                        + "then combine with the pasta."
        );

        addRecipeIngredient(recipeId, "Pasta", 200, "g");
        addRecipeIngredient(recipeId, "Chicken", 250, "g");
        addRecipeIngredient(recipeId, "Onion", 1, "pcs");
        addRecipeIngredient(recipeId, "Cream", 100, "ml");


        // --------------------------------------------------------
        // 5. Fried Rice
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Fried Rice",
                "Cook the rice and allow it to cool. Fry the "
                        + "vegetables and eggs, then add the rice "
                        + "and soy sauce. Stir-fry until heated."
        );

        addRecipeIngredient(recipeId, "Rice", 200, "g");
        addRecipeIngredient(recipeId, "Eggs", 2, "pcs");
        addRecipeIngredient(recipeId, "Carrots", 1, "pcs");
        addRecipeIngredient(recipeId, "Soy Sauce", 30, "ml");


        // --------------------------------------------------------
        // 6. Grilled Cheese Sandwich
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Grilled Cheese Sandwich",
                "Place cheese between two slices of bread. "
                        + "Spread butter on the outside and grill "
                        + "both sides until golden and the cheese melts."
        );

        addRecipeIngredient(recipeId, "Bread", 2, "pcs");
        addRecipeIngredient(recipeId, "Cheese", 60, "g");
        addRecipeIngredient(recipeId, "Butter", 20, "g");


        // --------------------------------------------------------
        // 7. Chicken Sandwich
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Chicken Sandwich",
                "Cook the chicken thoroughly. Place the chicken, "
                        + "lettuce and tomato between slices of bread. "
                        + "Add mayonnaise and serve."
        );

        addRecipeIngredient(recipeId, "Bread", 2, "pcs");
        addRecipeIngredient(recipeId, "Chicken", 150, "g");
        addRecipeIngredient(recipeId, "Tomato", 1, "pcs");
        addRecipeIngredient(recipeId, "Lettuce", 30, "g");
        addRecipeIngredient(recipeId, "Mayonnaise", 20, "g");


        // --------------------------------------------------------
        // 8. French Toast
        // --------------------------------------------------------

        recipeId = addRecipe(
                "French Toast",
                "Beat the eggs with milk and sugar. Dip the bread "
                        + "into the mixture and fry both sides until "
                        + "golden brown."
        );

        addRecipeIngredient(recipeId, "Bread", 2, "pcs");
        addRecipeIngredient(recipeId, "Eggs", 2, "pcs");
        addRecipeIngredient(recipeId, "Milk", 100, "ml");
        addRecipeIngredient(recipeId, "Sugar", 20, "g");


        // --------------------------------------------------------
        // 9. Vegetable Rice
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Vegetable Rice",
                "Cook the rice. Fry the vegetables until tender "
                        + "and combine them with the cooked rice. "
                        + "Season and serve."
        );

        addRecipeIngredient(recipeId, "Rice", 200, "g");
        addRecipeIngredient(recipeId, "Carrots", 1, "pcs");
        addRecipeIngredient(recipeId, "Peas", 50, "g");
        addRecipeIngredient(recipeId, "Onion", 1, "pcs");


        // --------------------------------------------------------
        // 10. Scrambled Eggs
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Scrambled Eggs",
                "Beat the eggs with a little milk. Pour into a "
                        + "heated pan and stir continuously until cooked."
        );

        addRecipeIngredient(recipeId, "Eggs", 3, "pcs");
        addRecipeIngredient(recipeId, "Milk", 50, "ml");
        addRecipeIngredient(recipeId, "Butter", 10, "g");


        // --------------------------------------------------------
        // 11. Tomato Soup
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Tomato Soup",
                "Cook the tomatoes, onion and garlic until soft. "
                        + "Add water or stock and simmer. Blend until "
                        + "smooth and add cream before serving."
        );

        addRecipeIngredient(recipeId, "Tomato", 4, "pcs");
        addRecipeIngredient(recipeId, "Onion", 1, "pcs");
        addRecipeIngredient(recipeId, "Garlic", 2, "pcs");
        addRecipeIngredient(recipeId, "Cream", 100, "ml");


        // --------------------------------------------------------
        // 12. Chicken Salad
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Chicken Salad",
                "Cook the chicken thoroughly and allow it to cool. "
                        + "Combine with lettuce, tomato and cucumber. "
                        + "Add mayonnaise and serve."
        );

        addRecipeIngredient(recipeId, "Chicken", 150, "g");
        addRecipeIngredient(recipeId, "Lettuce", 50, "g");
        addRecipeIngredient(recipeId, "Tomato", 1, "pcs");
        addRecipeIngredient(recipeId, "Cucumber", 1, "pcs");
        addRecipeIngredient(recipeId, "Mayonnaise", 20, "g");


        // --------------------------------------------------------
        // 13. Banana Smoothie
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Banana Smoothie",
                "Blend the banana, milk and sugar until smooth. "
                        + "Serve immediately."
        );

        addRecipeIngredient(recipeId, "Banana", 1, "pcs");
        addRecipeIngredient(recipeId, "Milk", 250, "ml");
        addRecipeIngredient(recipeId, "Sugar", 15, "g");


        // --------------------------------------------------------
        // 14. Macaroni and Cheese
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Macaroni and Cheese",
                "Cook the macaroni. Prepare a simple cheese sauce "
                        + "using butter, milk and cheese. Combine with "
                        + "the macaroni and serve."
        );

        addRecipeIngredient(recipeId, "Macaroni", 200, "g");
        addRecipeIngredient(recipeId, "Cheese", 100, "g");
        addRecipeIngredient(recipeId, "Milk", 200, "ml");
        addRecipeIngredient(recipeId, "Butter", 20, "g");


        // --------------------------------------------------------
        // 15. Beef Stir Fry
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Beef Stir Fry",
                "Slice the beef into thin strips. Stir-fry the beef "
                        + "with onion, carrots and soy sauce until cooked."
        );

        addRecipeIngredient(recipeId, "Beef", 250, "g");
        addRecipeIngredient(recipeId, "Onion", 1, "pcs");
        addRecipeIngredient(recipeId, "Carrots", 1, "pcs");
        addRecipeIngredient(recipeId, "Soy Sauce", 30, "ml");


        // --------------------------------------------------------
        // 16. Tuna Sandwich
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Tuna Sandwich",
                "Mix tuna with mayonnaise. Place the mixture "
                        + "between slices of bread and add lettuce."
        );

        addRecipeIngredient(recipeId, "Bread", 2, "pcs");
        addRecipeIngredient(recipeId, "Tuna", 1, "pcs");
        addRecipeIngredient(recipeId, "Mayonnaise", 20, "g");
        addRecipeIngredient(recipeId, "Lettuce", 30, "g");


        // --------------------------------------------------------
        // 17. Potato Omelette
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Potato Omelette",
                "Cook the sliced potatoes and onion until tender. "
                        + "Add beaten eggs and cook until the eggs "
                        + "are set."
        );

        addRecipeIngredient(recipeId, "Potatoes", 2, "pcs");
        addRecipeIngredient(recipeId, "Eggs", 3, "pcs");
        addRecipeIngredient(recipeId, "Onion", 1, "pcs");


        // --------------------------------------------------------
        // 18. Chicken and Rice
        // --------------------------------------------------------

        recipeId = addRecipe(
                "Chicken and Rice",
                "Cook the rice separately. Season and cook the "
                        + "chicken until completely cooked. Serve "
                        + "the chicken with the rice."
        );

        addRecipeIngredient(recipeId, "Chicken", 250, "g");
        addRecipeIngredient(recipeId, "Rice", 200, "g");
    }


    // ============================================================
    // DATABASE CONFIGURATION
    // ============================================================

    @Override
    public void onConfigure(
            SQLiteDatabase db) {

        super.onConfigure(db);

        db.setForeignKeyConstraintsEnabled(
                true
        );
    }
}