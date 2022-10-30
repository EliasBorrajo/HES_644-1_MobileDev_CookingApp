package ch.hevs.cookingapp.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.hevs.cookingapp.database.entity.RecipeEntity;

public interface CategoryDao
{
    //TODO demander si insert, delete update utile comme il peut quand meme choissir une des options
    @Query("SELECT * FROM recipe WHERE id = :id")   // REQUETE SQL obligatoire
    LiveData<RecipeEntity> getById(Long id);        // Metode liée à la requete SQL

    @Query("SELECT * FROM recipe")
    LiveData<List<RecipeEntity>> getAll();

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
