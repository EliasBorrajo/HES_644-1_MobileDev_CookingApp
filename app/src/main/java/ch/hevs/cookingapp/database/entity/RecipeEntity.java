package ch.hevs.cookingapp.database.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity class for the Recipe table
 * Relation with the Cook table : Many to One (One Cook can have many Recipes).
 */
public class RecipeEntity
{
    private String id;
    private String creator;
    private String name;
    private int prepTime;       // time in minutes
    private String ingredients;
    private String preparation;

    private String diet;
    private String allergy;
    private String mealTime;
    private String image;       // Goes in the StorageFirebase, but in the meantime in B64 in the DB

    // C O N S T R U C T E U R
    public RecipeEntity()
    {
    }

    public RecipeEntity(String creator, String name, int prepTime, String ingredients, String preparation, String diet, String allergy, String mealTime, String image)
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
    // Exclude : Does not include the field in the database table (Firebase)
    @Exclude
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    /**
     * Method to convert the RecipeEntity object to a Map object (for Firebase) : to be able to push it to the DB.
     * @return Map<String, Object> : the RecipeEntity object converted to a Map object
     */
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
