package ch.hevs.cookingapp.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.database.pojo.CookWithRecipes;

public class CooksListLiveData extends LiveData<List<CookEntity>> // TODO : Changer le POJO en ENTITY normale ici :)
{
    private static final String TAG = "CooksListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final CooksListLiveData.MyValueEventListener listener = new CooksListLiveData.MyValueEventListener();

    public CooksListLiveData(DatabaseReference ref, String owner)
    {
        reference = ref;
        this.owner = owner;
    }

    @Override
    protected void onActive()
    {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive()
    {
        Log.d(TAG, "onInactive");
    }



    private List<CookWithRecipes> toCookWithRecipesList (DataSnapshot snapshot)
    {
        List<CookWithRecipes> cooksWithRecipesList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren())
        {
            if( !childSnapshot.getKey().equals(owner) )
            {
                CookWithRecipes cookWithRecipes = new CookWithRecipes();
                cookWithRecipes.cook = childSnapshot.getValue(CookEntity.class);
                cookWithRecipes.cook.setEmail(childSnapshot.getKey());
                cookWithRecipes.recipes = toRecipes(childSnapshot.child("recipes"), childSnapshot.getKey());

                cooksWithRecipesList.add(cookWithRecipes);
            }
        }
        return cooksWithRecipesList;
    }

    private List<RecipeEntity> toRecipes (DataSnapshot snapshot, String cookEmail)
    {
        List<RecipeEntity> recipesList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren())
        {
            RecipeEntity recipe = childSnapshot.getValue(RecipeEntity.class);
            recipe.setId(childSnapshot.getKey());
            recipe.setCreator(owner);
            recipesList.add(recipe);
        }
        return recipesList;
    }

    private class MyValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            setValue(toCookWithRecipesList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

}
