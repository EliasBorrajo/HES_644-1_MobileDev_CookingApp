package ch.hevs.cookingapp.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ch.hevs.cookingapp.database.entity.RecipeEntity;

public class RecipeLiveData extends LiveData<RecipeEntity>
{
    private static final String TAG = "RecipeLiveData";

    private final DatabaseReference reference;
    private final RecipeLiveData.MyValueEventListener listener = new RecipeLiveData.MyValueEventListener();

    public RecipeLiveData(DatabaseReference ref)
    {
        reference = ref;
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

    private class MyValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            RecipeEntity entity = dataSnapshot.getValue(RecipeEntity.class);
            if(entity != null) {
                entity.setId(dataSnapshot.getKey());
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
