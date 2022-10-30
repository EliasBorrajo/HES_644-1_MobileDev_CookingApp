package ch.hevs.cookingapp.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.hevs.cookingapp.database.entity.CategoryEntity;

public interface CategoryDao
{
    //TODO demander si insert, delete update utile comme il peut quand meme choissir une des options
    @Query("SELECT * FROM category WHERE id = :id")   // REQUETE SQL obligatoire
    LiveData<CategoryEntity> getById(Long id);        // Metode liée à la requete SQL

    @Query("SELECT * FROM category")
    LiveData<List<CategoryEntity>> getAll();

    @Insert
    long insert(CategoryEntity category) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CategoryEntity> categorys);

    @Update
    void update(CategoryEntity category);

    @Delete
    void delete(CategoryEntity category);

    @Query("DELETE FROM category")
    void deleteAll();
}
