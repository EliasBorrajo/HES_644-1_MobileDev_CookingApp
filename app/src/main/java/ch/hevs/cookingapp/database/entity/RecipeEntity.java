package ch.hevs.cookingapp.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity class for the Recipe table
 * Relation with the Cook table : Many to One (One Cook can have many Recipes).
 * Cook <-1----*-> Recipe
 */
public class RecipeEntity
{
    private String id;
    private String creator;
    private String name;
    private int prepTime;       //temps de préparation de la recette en minutes
    private String ingredients;
    private String preparation; //instructions de la recette

    private String diet;
    private String allergy;
    private String mealTime;
    // On veut que l'image soit stockée en BLOB dans la DB (byte[]) et non en String (text).
    private byte[] image;       // TODO : Ira dans le StorageFirebase, mais en attendant en B64 dans la DB

    // C O N S T R U C T E U R
    public RecipeEntity()
    {
    }


    // TODO Creator ?
    public RecipeEntity(String creator, String name, int prepTime, String ingredients, String preparation, String diet, String allergy, String mealTime, byte[] image)
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
    @Exclude
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

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

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

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

    public String getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(@NonNull String ingredients)
    {
        this.ingredients = ingredients;
    }

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

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("creator", creator);
        result.put("name", name);
        result.put("prepTime", prepTime);
        result.put("ingredients", ingredients);
        result.put("preparation", preparation);
        result.put("diet", diet);
        result.put("allergy", allergy);
        result.put("mealTime", mealTime);
        result.put("image", image);
        return result;
    }
}
