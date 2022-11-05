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

@Dao
public interface RecipeDao
{
    @Query("SELECT * FROM recipe WHERE id = :id")   // REQUETE SQL obligatoire
    LiveData<RecipeEntity> getById(Long id);        // Metode liée à la requete SQL

    @Query("SELECT * FROM recipe")
    LiveData<List<RecipeEntity>> getAll();

    @Query("SELECT * FROM recipe WHERE creator=:creator")
    LiveData<List<RecipeEntity>> getOwned(String creator);

    @Insert
    long insert(RecipeEntity recipe) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecipeEntity> recipes);

    @Update
    void update(RecipeEntity recipe);

    @Delete
    void delete(RecipeEntity recipe);

    @Query("DELETE FROM recipe")
    void deleteAll();
}
