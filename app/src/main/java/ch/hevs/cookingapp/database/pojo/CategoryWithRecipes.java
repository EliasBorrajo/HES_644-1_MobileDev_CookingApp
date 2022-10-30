package ch.hevs.cookingapp.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import ch.hevs.cookingapp.database.entity.RecipeEntity;

public class CategoryWithRecipes {
    @Embedded
    public RecipeEntity recipe;

    @Relation(parentColumn = "id", entityColumn = "category", entity = RecipeEntity.class)
    public List<RecipeEntity> recipes;
}
