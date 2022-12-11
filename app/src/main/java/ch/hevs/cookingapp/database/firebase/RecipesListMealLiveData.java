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

public class RecipesListMealLiveData extends LiveData<List<RecipeEntity>>
{
    private static final String TAG = "RecipesListMealLiveData";

    private final DatabaseReference reference;
    private final String meal;
    private final MyValueEventListener listener = new MyValueEventListener();

    public RecipesListMealLiveData(DatabaseReference reference, String meal)
    {
        this.reference = reference;
        this.meal = meal;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

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

    private List<RecipeEntity> toRecipes(DataSnapshot dataSnapshot) {
        List<RecipeEntity> recipes = new ArrayList<>();
        for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
        {
            RecipeEntity entity = childSnapshot.getValue(RecipeEntity.class);
            entity.setId(childSnapshot.getKey());
            entity.setCreator(meal);
            recipes.add(entity);
        }
        return recipes;
    }
}
