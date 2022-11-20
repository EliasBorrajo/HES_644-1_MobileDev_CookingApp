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

import ch.hevs.cookingapp.database.entity.CookEntity;


/**
 * DAOs abstract access to the database in a clean way
 *
 * This class is used to access the CookEntity table.
 * It contains all the methods to access the database.
 * The methods are annotated with the SQL queries.
 * The methods are used in the CookRepository class.
 * The methods are called in the CookViewModel class.
 */
@Dao
public interface CookDao
{
    @Query("SELECT * FROM cook WHERE email = :id")  // REQUETE SQL obligatoire
    LiveData<CookEntity> getById(String id);        // Metode liée à la requete SQL

    @Query("SELECT * FROM cook")
    LiveData<List<CookEntity>> getAll();

    @Insert
    long insert(CookEntity cooks) throws SQLiteConstraintException;

    @Update
    void update(CookEntity cook);

    @Delete
    void delete(CookEntity cook);

    @Query("DELETE FROM cook")
    void deleteAll();
}
