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
    //Diet
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

    // C O N S T R U C T E U R
    @Ignore
    public CategoryEntity()
    {
    }

    public CategoryEntity(boolean vegetarian, boolean vegan, boolean meat, boolean fish, boolean peanut, boolean lactose, boolean nut, boolean gluten, boolean morning, boolean midday, boolean evening) {
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.meat = meat;
        this.fish = fish;
        this.peanut = peanut;
        this.lactose = lactose;
        this.nut = nut;
        this.gluten = gluten;
        this.morning = morning;
        this.midday = midday;
        this.evening = evening;
    }

    // G E T T E R S   S E T T E R S
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isMeat() {
        return meat;
    }

    public void setMeat(boolean meat) {
        this.meat = meat;
    }

    public boolean isFish() {
        return fish;
    }

    public void setFish(boolean fish) {
        this.fish = fish;
    }

    public boolean isPeanut() {
        return peanut;
    }

    public void setPeanut(boolean peanut) {
        this.peanut = peanut;
    }

    public boolean isLactose() {
        return lactose;
    }

    public void setLactose(boolean lactose) {
        this.lactose = lactose;
    }

    public boolean isNut() {
        return nut;
    }

    public void setNut(boolean nut) {
        this.nut = nut;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isMidday() {
        return midday;
    }

    public void setMidday(boolean midday) {
        this.midday = midday;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
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
                "vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", meat=" + meat +
                ", fish=" + fish +
                ", peanut=" + peanut +
                ", lactose=" + lactose +
                ", nut=" + nut +
                ", gluten=" + gluten +
                ", morning=" + morning +
                ", midday=" + midday +
                ", evening=" + evening +
                '}';
    }
}
