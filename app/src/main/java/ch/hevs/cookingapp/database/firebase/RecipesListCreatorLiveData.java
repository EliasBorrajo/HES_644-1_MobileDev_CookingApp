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

import ch.hevs.cookingapp.database.entity.RecipeEntity;

/**
 * A class that implements the LiveData class to be able to observe the changes in the database.
 * This class is used to observe the changes in the database for the cooks.
 * It converts the DataSnapshot to a list of entities.
 */
public class RecipesListCreatorLiveData extends LiveData<List<RecipeEntity>>
{
    private static final String TAG = "RecipesListCreatorLiveData";

    private final DatabaseReference reference;
    private final String creator;
    private final MyValueEventListener listener = new MyValueEventListener();

    public RecipesListCreatorLiveData(DatabaseReference ref, String creator)
    {
        reference = ref;
        this.creator = creator;
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

    /**
     * This class implements the ValueEventListener interface to be able to observe the changes in the database.
     * This class is used to observe the changes in the database for the recipes.
     * It converts the DataSnapshot to a list of entities.
     */
    private class MyValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            setValue(toRecipes(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    /**
     * Converts the DataSnapshot to a list of entities.
     * @param dataSnapshot The DataSnapshot to convert.
     * @return The list of entities.
     * @param dataSnapshot
     * @return
     */
    private List<RecipeEntity> toRecipes(DataSnapshot dataSnapshot)
    {
        List<RecipeEntity> recipes = new ArrayList<>();
        for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
        {
            RecipeEntity entity = childSnapshot.getValue(RecipeEntity.class);
            entity.setId(childSnapshot.getKey());
            if (entity.getCreator().equals(creator)) {
                recipes.add(entity);
            }
        }
        return recipes;
    }
}
