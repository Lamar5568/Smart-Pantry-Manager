Smart Pantry Manager
Project Overview

Smart Pantry Manager is an Android mobile application developed to help users manage pantry ingredients and discover recipes based on the ingredients they currently have available.

The application allows users to add, edit, and delete pantry ingredients, store ingredient information using a local SQLite database, and receive recipe suggestions when all required ingredients and quantities are available.

The project was developed as part of a Mobile App Development 700 practical assignment.

Features
Pantry Management
Add pantry ingredients.
Edit existing ingredients.
Delete ingredients.
Store ingredient name, quantity, unit, and optional expiry date.
Display pantry ingredients using a RecyclerView.
Pantry data persists between application sessions.
Recipe Management
Includes 18 preloaded recipes.
Recipes contain:
Recipe name
Required ingredients
Required quantities and units
Preparation instructions
Recipes are stored in the SQLite database.
Suggested Recipes

The application checks the user's pantry against recipe requirements.

A recipe is suggested only when:

Every required ingredient is available.
The available quantity is equal to or greater than the required quantity.
The ingredient name and unit match the recipe requirement.

The application uses strict matching rather than displaying recipes that are only partially possible.

If no recipe can currently be prepared, the application displays a clear message informing the user that no recipes can be made with the current pantry.

Recipe Details

Users can select a suggested recipe to view:

Recipe name
Required ingredients
Required quantities
Cooking instructions
Settings

The application includes a Settings screen containing:

Expiring Soon Alerts setting
Application information
Navigation

The application provides multiple navigation options, including:

Pantry screen buttons
Toolbar Menu
Suggested Recipes
Settings
Technologies Used
Programming Language: Java
Development Environment: Android Studio
Database: SQLite
UI: Android XML layouts
RecyclerView: Used for displaying pantry ingredients and recipes
Android Intents: Used for navigation between activities
Git/GitHub: Used for version control and project management
Application Screens

The application contains the following main screens:

Pantry List
Displays the user's stored pantry ingredients.
Add Ingredient
Allows users to add new pantry items.
Edit Ingredient
Allows users to update existing pantry items.
Suggested Recipes
Displays recipes that can be prepared using the available pantry ingredients.
Recipe Details
Displays the full ingredients and preparation instructions for a selected recipe.
Settings
Provides application settings and information.
Database Design

Smart Pantry Manager uses SQLite for local data storage.

The database contains three main tables:

Ingredients

Stores the user's pantry items.

Main fields include:

id
name
quantity
unit
expiry_date
Recipes

Stores the available recipes.

Main fields include:

id
name
instructions
Recipe Ingredients

Stores the ingredients required by each recipe.

Main fields include:

id
recipe_id
name
quantity
unit

The recipe_id field connects recipe ingredients to their corresponding recipe.

Recipe Matching Logic

The application uses strict recipe matching.

For each recipe, the application checks every required ingredient against the user's pantry.

For example, if a recipe requires:

Flour: 200 g
Milk: 250 ml
Eggs: 2 pcs
Sugar: 30 g

the recipe will only be suggested if the pantry contains all four ingredients with sufficient quantities.

If the pantry contains only 100 g of flour, the recipe will not be suggested.

Ingredient names and units are normalized to make matching more reliable when there are simple differences such as capitalization or extra spaces.

Default Recipes

The application is seeded with 18 recipes:

Pancakes
Omelette
Tomato Pasta
Chicken Pasta
Fried Rice
Grilled Cheese Sandwich
Chicken Sandwich
French Toast
Vegetable Rice
Scrambled Eggs
Tomato Soup
Chicken Salad
Banana Smoothie
Macaroni and Cheese
Beef Stir Fry
Tuna Sandwich
Potato Omelette
Chicken and Rice
Data Persistence

SQLite is used to ensure that pantry information remains available after the application is closed and reopened.

The database is managed through the DatabaseHelper class, which provides functionality for:

Creating database tables
Adding ingredients
Updating ingredients
Deleting ingredients
Retrieving pantry ingredients
Storing recipes
Retrieving recipes
Matching recipes against pantry contents
Project Structure

The main application components include:

SmartPantryManager
│
├── app
│   └── src
│       └── main
│           ├── java
│           │   └── com.example.smartpantrymanager
│           │       ├── MainActivity.java
│           │       ├── AddIngredientActivity.java
│           │       ├── EditIngredientActivity.java
│           │       ├── SuggestedRecipesActivity.java
│           │       ├── RecipeDetailsActivity.java
│           │       ├── SettingsActivity.java
│           │       ├── DatabaseHelper.java
│           │       ├── Ingredient.java
│           │       ├── IngredientAdapter.java
│           │       ├── Recipe.java
│           │       ├── RecipeIngredient.java
│           │       └── RecipeAdapter.java
│           │
│           └── res
│               ├── layout
│               ├── menu
│               └── values
│
└── README.md
How to Run the Application
Requirements
Android Studio
Android SDK
Java/JDK
Android emulator or physical Android device
Steps
Clone the repository from GitHub.
Open the project in Android Studio.
Allow Gradle to synchronize.
Connect an Android device or start an Android emulator.
Build the project.
Run the application using Android Studio.
Version Control

Git and GitHub were used throughout the development process to track changes and maintain different stages of the application.

The repository contains more than the required minimum of 10 meaningful commits, documenting the development of the application's major features.

Project Purpose

The purpose of Smart Pantry Manager is to demonstrate the development of a functional Android application using Java, XML layouts, SQLite database operations, RecyclerView components, Intents, and object-oriented programming principles.

The project demonstrates how a mobile application can combine persistent data storage with application logic to provide useful recipe suggestions based on the user's available pantry ingredients.

Author

Lamar5568

Mobile App Development 700
Android Application Development Project