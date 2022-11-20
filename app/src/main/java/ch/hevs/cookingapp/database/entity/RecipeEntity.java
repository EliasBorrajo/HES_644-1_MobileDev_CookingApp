package ch.hevs.cookingapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entity class for the Recipe table
 * Relation with the Cook table : Many to One (One Cook can have many Recipes).
 * Cook <-1----*-> Recipe
 */
@Entity(tableName = "recipe",
        foreignKeys = {
            @ForeignKey(
                    entity = CookEntity.class,
                    parentColumns = "email",    // Sera la clé primaire de la table COOK
                    childColumns  = "creator",   // Sera le nom de la clé étrangère dans la table Recipe
                    onDelete = ForeignKey.CASCADE   // On suprime le COOK et tous ses Recettes en cascade
                        )},
        indices = {
                @Index(
                        value = {"creator"}
                )}
        )
public class RecipeEntity
{
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String creator;
    @NonNull
    private String name;
    private int prepTime;   //temps de préparation de la recette en minutes
    @NonNull
    private String ingredients;
    @NonNull
    private String preparation; //instructions de la recette

    private String diet;
    private String allergy;
    private String mealTime;
    // On veut que l'image soit stockée en BLOB dans la DB (byte[]) et non en String (text).
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    // C O N S T R U C T E U R
    @Ignore
    public RecipeEntity()
    {
    }


    public RecipeEntity(String creator, @NonNull String name, int prepTime, @NonNull String ingredients, @NonNull String preparation, String diet, String allergy, String mealTime, byte[] image)
    {
        this.creator = creator;
        this.name = name;
        this.prepTime = prepTime;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.diet = diet;
        this.allergy = allergy;
        this.mealTime = mealTime;
        this.image = image;
    }

    // G E T T E R S   S E T T E R S
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDiet()
    {
        return diet;
    }

    public void setDiet(String diet)
    {
        this.diet = diet;
    }

    public String getAllergy()
    {
        return allergy;
    }

    public void setAllergy(String allergy)
    {
        this.allergy = allergy;
    }

    public String getMealTime()
    {
        return mealTime;
    }

    public void setMealTime(String mealTime)
    {
        this.mealTime = mealTime;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    @NonNull
    public String getName()
    {
        return name;
    }

    public void setName(@NonNull String name)
    {
        this.name = name;
    }

    public int getPrepTime()
    {
        return prepTime;
    }

    public void setPrepTime(int prepTime)
    {
        this.prepTime = prepTime;
    }

    @NonNull
    public String getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(@NonNull String ingredients)
    {
        this.ingredients = ingredients;
    }

    @NonNull
    public String getPreparation()
    {
        return preparation;
    }

    public void setPreparation(@NonNull String preparation)
    {
        this.preparation = preparation;
    }

    // O V E R R I D E
    @Override
    public String toString()
    {
        return "RecipeEntity{" +
                "creator='" + creator + '\'' +
                ", name='" + name + '\'' +
                ", prepTime=" + prepTime +
                ", ingredients='" + ingredients + '\'' +
                ", preparation='" + preparation + '\'' +
                ", diet='" + diet + '\'' +
                ", allergy='" + allergy + '\'' +
                ", mealTime='" + mealTime + '\'' +
                '}';
    }
}
