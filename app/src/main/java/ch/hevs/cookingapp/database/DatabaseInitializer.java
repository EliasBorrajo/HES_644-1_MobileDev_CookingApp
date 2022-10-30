package ch.hevs.cookingapp.database;

import android.os.AsyncTask;
import android.util.Log;

import ch.hevs.cookingapp.database.entity.CategoryEntity;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.enumeration.Allergy;
import ch.hevs.cookingapp.database.enumeration.Diet;
import ch.hevs.cookingapp.database.enumeration.Meal;

/**
 * Generates dummy data
 */
public class DatabaseInitializer
{
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db)
    {
        Log.i(TAG, "Inserting demo data.");

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();

    }

    private static void addCook(final AppDatabase db,  final String email,    final String firstName,
                                final String lastName, final String password, final String phoneNumber )
    {
        // Créer l'objet Cook, puis l'ajouter à la DB
        CookEntity cook = new CookEntity(email, firstName, lastName, password, phoneNumber);
        db.cookDao().insert(cook);
    }

    private static void addRecipe(final AppDatabase db, final String creator, final long category, final String name,
                                  final int prepTime,   final String ingredients, final String preparation)
    {
        RecipeEntity recipe = new RecipeEntity(creator, category, name, prepTime, ingredients, preparation);
        db.recipeDao().insert(recipe);
    }

    private static void addCategory(final AppDatabase db, final boolean vegetarian, final boolean vegan, final boolean meat, final boolean fish, final boolean peanut, final boolean lactose, final boolean nut, final boolean gluten, final boolean morning, final boolean midday, final boolean evening)
    {
        CategoryEntity category = new CategoryEntity(vegetarian, vegan, meat, fish, peanut, lactose, nut, gluten, morning, midday, evening);
        db.categoryDao().insert(category);
    }

    private static void populateWithTestData (AppDatabase db)
    {
        db.cookDao().deleteAll();
        db.recipeDao().deleteAll(); // TODO : Supprimer si on delete en cascade
        db.categoryDao().deleteAll();

        // Remplir Cook
        addCook(db, "xolo@gmail.com",     "Xolo",     "Survivor",  "RIA",       "078 820 64 30");
        addCook(db, "elias@gmail.com",    "Elias",    "Borrajo",   "coding",    "078 820 64 31");
        addCook(db, "milena@gmail.com",   "Milena",   "Lonfat",    "killing",   "078 820 64 32");
        addCook(db, "jonathan@gmail.com", "Jonathan", "Bourquin",  "my",        "078 820 64 33");
        addCook(db, "zach@gmail.com",     "Zacharie", "Rennaz",    "brain",     "078 820 64 34");
        addCook(db, "arthur@gmail.com",   "Arthur",   "Avez",      "slowly",    "078 820 64 35");

        try {
            // Let's ensure that the cooks are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Remplir Category
        addCategory(db, true, false, false, false, false, false, true, true, true, false, true);
        addCategory(db, false, false, true, true, false, false, true, false, true, true, false);
        addCategory(db, false, false, true, true, false, false, true, false, true, true, false);
        addCategory(db, false, false, true, true, false, false, true, false, true, true, false);

        // Remplir Recipe
        // TODO : Changer Category dans le seed ici en bas
        addRecipe(db, "xolo@gmail.com", 1, "Crêpes", 15, "Oeufs, Lait, Beurre", "Melanger fortement le tout");
        addRecipe(db, "xolo@gmail.com", 2, "Sandiwch", 10, "Pain, Beurre, Jambon, tomate", "Empiler par tranches shouaités");
        addRecipe(db, "milena@gmail.com", 3, "Cookies", 45, "Oeufs, Lait, Beurre, Chocolat, Un tas de bonnes choses, Agent chimique X", "Melanger fortement le tout, puis lancer dans le four.");
        addRecipe(db, "milena@gmail.com", 4, "CrockScooby", 55, "Chien, agent secret, Sucre", "Melanger fortement le tout et mettre dans le burger à Zach");
    }


    // C L A S S E   I N T E R N E
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {

        private final AppDatabase DATABASE;

        PopulateDbAsync(AppDatabase database)
        {
            DATABASE = database;
        }

        @Override
        protected Void doInBackground(final Void... voidsParam)
        {
            populateWithTestData(DATABASE);
            return null;
        }
    }
}
