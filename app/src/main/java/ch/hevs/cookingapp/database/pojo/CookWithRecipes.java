package ch.hevs.cookingapp.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;

// On aumgente l'objet en ajoutant les recettes qu'il possède.
// Ce que on va aller chercher comme informations de la DB.
// 1 Objet java qui contient tout pour la selection de données de la DB. Acceder la DB en 1 fois au lieu de 2.

public class CookWithRecipes {
    // La table principale
    @Embedded
    public CookEntity cook;

    // A laquelle il y aura une LISTE DE RECETTES en plus.
    @Relation(parentColumn = "email", entityColumn = "creator", entity = RecipeEntity.class)
    public List<RecipeEntity> recipes;


}
