package ch.hevs.cookingapp.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import ch.hevs.cookingapp.database.entity.RecipeEntity;

public class CookWithRecipes {
    @Embedded
    public RecipeEntity recipe;

    @Relation(parentColumn = "email", entityColumn = "creator", entity = RecipeEntity.class)
    public List<RecipeEntity> recipes;
}
