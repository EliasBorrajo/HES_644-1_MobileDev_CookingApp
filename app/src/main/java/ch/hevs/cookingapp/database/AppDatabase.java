package ch.hevs.cookingapp.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.Executors;

import ch.hevs.cookingapp.database.dao.CategoryDao;
import ch.hevs.cookingapp.database.dao.CookDao;
import ch.hevs.cookingapp.database.dao.RecipeDao;
import ch.hevs.cookingapp.database.entity.CategoryEntity;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;

// CREATION DE LA DB
@Database(entities = {CookEntity.class, RecipeEntity.class, CategoryEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    // A T T R I B U T S
    private static final String DATABASE_NAME = "cooking-database"; // Nom dans Inspecteur d'APP, et dans SQL
    private static final String TAG           = "AppDatabase";      // Nom de la DB dans les LOG
    private static AppDatabase instance;

    // M E T H O D E S
    // Les DAO qu'il utilisera pour parler avec la ROOM
    public abstract CookDao     cookDao();
    public abstract RecipeDao   recipeDao();
    public  abstract CategoryDao categoryDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();    //TODO : a quoi ça set ?

    // SINGLETON PATTERN
    public static AppDatabase getInstance(final Context context)
    {
        if (instance == null)
        {
            synchronized (AppDatabase.class)
            {
                if (instance == null)
                {
                    instance = builDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context)
    {
        if (context.getDatabasePath(DATABASE_NAME).exists())
        {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase builDatabase(final Context appContext)
    {
        Log.i(TAG, "database will be initialised.");

        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback()
                    {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db)
                        {
                            super.onCreate(db);
                            // My own implementation here
                            Executors.newSingleThreadExecutor().execute( () ->
                                {
                                    AppDatabase database = AppDatabase.getInstance(appContext);
                                    initializeData(database);
                                    // notify that the database was created and it's ready to be used
                                    database.setDatabaseCreated();
                                });
                        }
                    })
                .build();
    }

    // Apelle la DB_INITIALIZER
    private static void initializeData(final AppDatabase database)
    {
        Executors.newSingleThreadExecutor().execute( () ->
            {
                database.runInTransaction( () ->
                    {
                        Log.i(TAG, "Wipe database.");

                        database.cookDao().deleteAll();
                        database.recipeDao().deleteAll();
                        database.categoryDao().deleteAll();
                    });
            });
    }


}
