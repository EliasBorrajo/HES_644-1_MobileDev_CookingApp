package ch.hevs.cookingapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import ch.hevs.cookingapp.database.enumeration.*;

/**
 * Table Statique, ne changera jamais.
 */
@Entity(tableName = "category")
public class CategoryEntity
{
    @PrimaryKey(autoGenerate = true)
    private Long id;

    // Diet
    private boolean vegetarian;
    private boolean vegan;
    private boolean meat;
    private boolean fish;

    // Allergy
    private boolean peanut;
    private boolean lactose;
    private boolean nut;
    private boolean gluten;

    // Meal Time
    private boolean morning;
    private boolean midday;
    private boolean evening;

    // ENUMS ? @TODO
    //private Diet diet;
}
