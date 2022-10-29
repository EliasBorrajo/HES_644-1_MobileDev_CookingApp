package ch.hevs.cookingapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe",
        foreignKeys = {
            @ForeignKey(
                    entity = CookEntity.class,
                    parentColumns = "email",    // Sera la clé primaire de la table COOK
                    childColumns  = "creator"   // Sera le nom de la clé étrangère dans la table Recipe
                        ),
            @ForeignKey(
                    entity = CategoryEntity.class,
                    parentColumns = "id",    // Sera la clé primaire de la table COOK
                    childColumns  = "category"   // Sera le nom de la clé étrangère dans la table Recipe
                        )}
        )
public class RecipeEntity
{
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String creator;
    private int category;
    @NonNull
    private String name;
    private int prepTime;   //temps de préparation de la recette en minutes
    @NonNull
    private String ingredients;
    @NonNull
    private String preparation; //instructions de la recette


    // C O N S T R U C T E U R
    @Ignore
    public RecipeEntity()
    {
    }

    // TODO : Ajouter les FOREING KEY en paramètres ?
    public RecipeEntity(String creator, @NonNull String name, int prepTime, @NonNull String ingredients, @NonNull String preparation)
    {
        this.creator = creator;
        this.name = name;
        this.prepTime = prepTime;
        this.ingredients = ingredients;
        this.preparation = preparation;
    }

    // G E T T E R S   S E T T E R S

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
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
    public boolean equals (Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof RecipeEntity)) return false;

        // Dans les autres cas, on compare l'email qui est la Primary Key
        RecipeEntity o = (RecipeEntity) obj;
        return o.id.equals(this.id);
    }

    @Override
    public String toString()
    {
        return "RecipeEntity{" +
                "creator='" + creator + '\'' +
                ", name='" + name + '\'' +
                ", prepTime=" + prepTime +
                ", ingredients='" + ingredients + '\'' +
                ", preparation='" + preparation + '\'' +
                '}';
    }
}
