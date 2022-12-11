package ch.hevs.cookingapp.database.pojo;

import java.util.List;

import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;

// On aumgente l'objet en ajoutant les recettes qu'il possède.
// Ce que on va aller chercher comme informations de la DB.
// 1 Objet java qui contient tout pour la selection de données de la DB. Acceder la DB en 1 fois au lieu de 2.

/**
 *  The object is enlarged by adding the recipes that it has.
 *  What we are going to get as information from the DB.
 *  1 Java object that contains everything for the selection of data from the DB.
 *  Access the DB in 1 time instead of 2.
 */
public class CookWithRecipes {
    // La table principale
    public CookEntity cook;

    // A laquelle il y aura une LISTE DE RECETTES en plus.
    public List<RecipeEntity> recipes;


}
