package ch.hevs.cookingapp.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

import ch.hevs.cookingapp.database.enumeration.*;

/**
 * Table Statique, ne changera jamais.
 */
@Entity(tableName = "category")
public class CategoryEntity
{
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Diet[] diets;
    private Allergy[] allergies;
    private Meal[] meals;

    // C O N S T R U C T E U R
    @Ignore
    public CategoryEntity()
    {
    }

    public CategoryEntity(Diet[] diets, Allergy[] allergies, Meal[] meals)
    {
        this.diets = diets;
        this.allergies = allergies;
        this.meals = meals;
    }

    // G E T T E R S   S E T T E R S

    public Long getId() {
        return id;
    }

    public Diet[] getDiets() {
        return diets;
    }

    public void setDiets(Diet[] diets) {
        this.diets = diets;
    }

    public Allergy[] getAllergies() {
        return allergies;
    }

    public void setAllergies(Allergy[] allergies) {
        this.allergies = allergies;
    }

    public Meal[] getMeals() {
        return meals;
    }

    public void setMeals(Meal[] meals) {
        this.meals = meals;
    }

    // O V E R R I D E
    @Override
    public boolean equals (Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CategoryEntity)) return false;

        // Dans les autres cas, on compare l'email qui est la Primary Key
        CategoryEntity o = (CategoryEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", diets=" + Arrays.toString(diets) +
                ", allergies=" + Arrays.toString(allergies) +
                ", meals=" + Arrays.toString(meals) +
                '}';
    }

    /* Diet
    private boolean vegetarian;
    private boolean vegan;
    private boolean meat;
    private boolean fish;

    Allergy
    private boolean peanut;
    private boolean lactose;
    private boolean nut;
    private boolean gluten;

    Meal Time
    private boolean morning;
    private boolean midday;
    private boolean evening;
     */
    // ENUMS ? @TODO
    //private Diet diet;
}
