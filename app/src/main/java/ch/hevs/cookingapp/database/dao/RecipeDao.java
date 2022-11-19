package ch.hevs.cookingapp.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.pojo.CookWithRecipes;



/**
 *  DAOs abstract access to the database in a clean way.
 *
 *  This class is used to access the RecipeEntity table.
 *  It contains all the methods to access the database.
 *  The methods are annotated with the SQL queries.
 *  The methods are used in the RecipeRepository class.
 *  The methods are called in the RecipeViewModel class.
 */
@Dao
public interface RecipeDao
{
    @Query("SELECT * FROM recipe WHERE id = :id")   // REQUETE SQL obligatoire
    LiveData<RecipeEntity> getById(Long id);        // Metode liée à la requete SQL

    @Query("SELECT * FROM recipe")
    LiveData<List<RecipeEntity>> getAll();

    @Query("SELECT * FROM recipe WHERE creator=:creator")
    LiveData<List<RecipeEntity>> getOwned(String creator);

    @Query("SELECT * FROM recipe WHERE mealTime LIKE '%'||:mealTime||'%'")
    LiveData<List<RecipeEntity>> getByMeal(String mealTime);

    @Insert
    long insert(RecipeEntity recipe) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecipeEntity> recipes); // TODO :Supprimer

    @Update
    void update(RecipeEntity recipe);

    @Delete
    void delete(RecipeEntity recipe);

    @Query("DELETE FROM recipe")
    void deleteAll();
}
