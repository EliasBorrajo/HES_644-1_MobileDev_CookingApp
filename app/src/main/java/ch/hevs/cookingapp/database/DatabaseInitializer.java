package ch.hevs.cookingapp.database;

import android.os.AsyncTask;
import android.util.Log;

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

    private static void addRecipe(final AppDatabase db, final String creator, final String name,
                                  final int prepTime,   final String ingredients, final String preparation, final String diet, final String allergy, final String mealTime, final byte[] image)
    {
        RecipeEntity recipe = new RecipeEntity(creator, name, prepTime, ingredients, preparation, diet, allergy, mealTime, image);
        db.recipeDao().insert(recipe);
    }


    private static void populateWithTestData (AppDatabase db)
    {
        db.recipeDao().deleteAll();
        db.cookDao().deleteAll();

        // Remplir Cook
        addCook(db, "xolo@gmail.com",     "Xolo",     "Survivor",  "12345",     "0788206430");
        addCook(db, "elias@gmail.com",    "Elias",    "Borrajo",   "coding",    "0788206431");
        addCook(db, "milena@gmail.com",   "Milena",   "Lonfat",    "killing",   "0788206432");
        addCook(db, "jonathan@gmail.com", "Jonathan", "Bourquin",  "myPassword","0788206433");
        addCook(db, "zach@gmail.com",     "Zacharie", "Rennaz",    "brain",     "0788206434");
        addCook(db, "arthur@gmail.com",   "Arthur",   "Avez",      "slowly",    "0788206435");

        try {
            // Let's ensure that the cooks are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Remplir Recipe
        // Matin
        addRecipe(db, "xolo@gmail.com", "Crêpes", 15, "Oeufs, Lait, Beurre", "Melanger fortement le tout", Diet.VEGETARIAN.toString(), Allergy.GLUTEN.toString(), Meal.BREAKFAST.toString(),null);
        addRecipe(db, "xolo@gmail.com", "Sandiwch", 10, "Pain, Beurre, Jambon, tomate", "Empiler par tranches shouaités",Diet.FISH.toString(), Allergy.GLUTEN.toString(), Meal.BREAKFAST.toString(),null);
        addRecipe(db, "milena@gmail.com", "Cookies", 45, "Oeufs, Lait, Beurre, Chocolat, Un tas de bonnes choses, Agent chimique X", "Melanger fortement le tout, puis lancer dans le four.",Diet.MEAT.toString(), Allergy.GLUTEN.toString(), Meal.BREAKFAST.toString(),null);

        // Midi
        addRecipe(db, "arthur@gmail.com", "Burger Roi", 20, "Pain, Mayonaise, Salade, STEAK, tranche de tomate, pain", "N'oublie pas de mettre cette tranche de tomate frère", Diet.VEGETARIAN.toString(), Allergy.GLUTEN.toString(), Meal.BREAKFAST.toString() +  Meal.LUNCH.toString() +Meal.DINNER.toString(),null);
        addRecipe(db, "arthur@gmail.com", "Sandiwch", 10, "Pain, Beurre, Jambon, tomate", "Empiler par tranches shouaités",Diet.FISH.toString(), Allergy.GLUTEN.toString(), Meal.LUNCH.toString(),null);
        addRecipe(db, "elias@gmail.com", "Raclette", 45, "Fromage toi même tu sais lequel", "Fais moi griller cte chose la",Diet.MEAT.toString(), Allergy.GLUTEN.toString(), Meal.LUNCH.toString(),null);


        // Soi
        addRecipe(db, "elias@gmail.com", "Darcula", 15, "Oeufs, Lait, Beurre", "Melanger fortement le tout", Diet.VEGETARIAN.toString(), Allergy.GLUTEN.toString(), Meal.DINNER.toString(),null);
        addRecipe(db, "zach@gmail.com", "Sirop aux fruits", 10, "Pain, Beurre, Jambon, tomate", "Empiler par tranches shouaités",Diet.FISH.toString(), Allergy.GLUTEN.toString(), Meal.DINNER.toString(),null);
        addRecipe(db, "zach@gmail.com", "Paleo", 45, "Oeufs, Lait, Beurre, Chocolat, Un tas de bonnes choses, Agent chimique X", "Melanger fortement le tout, puis lancer dans le four.",Diet.MEAT.toString(), Allergy.GLUTEN.toString(), Meal.DINNER.toString(),null);


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
        protected Void doInBackground(final Void... params)
        {
            populateWithTestData(DATABASE);
            return null;
        }
    }
}
