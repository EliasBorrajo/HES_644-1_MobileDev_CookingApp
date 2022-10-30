package ch.hevs.cookingapp.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import ch.hevs.cookingapp.database.entity.RecipeEntity;
//TODO regarder si on peut la supprimer, d'apèrs nous pas besoin de le garder parce qu'il a autemps de catégorie que de recette. On pense ne plus avoir besoin...
public class CategoryWithRecipes {
    @Embedded
    public RecipeEntity recipe;

    @Relation(parentColumn = "id", entityColumn = "category", entity = RecipeEntity.class)
    public List<RecipeEntity> recipes;
}
