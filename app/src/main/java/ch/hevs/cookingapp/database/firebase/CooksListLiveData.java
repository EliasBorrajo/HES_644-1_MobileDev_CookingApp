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

public class CooksListLiveData extends LiveData<List<CookEntity>>
{
    private static final String TAG = "CooksListLiveData";

    private final DatabaseReference reference;
    private final CooksListLiveData.MyValueEventListener listener = new CooksListLiveData.MyValueEventListener();

    public CooksListLiveData(DatabaseReference ref)
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



    private List<CookEntity> toCookList(DataSnapshot snapshot)
    {
        List<CookEntity> cooksList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren())
        {

            CookEntity entity = childSnapshot.getValue(CookEntity.class); // This is the entity that will be created from the DataSnapshot
            entity.setEmail(childSnapshot.getKey()); // This is the key of the node (e.g. "user1")

            cooksList.add(entity);

        }
        return cooksList;
    }

    private class MyValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            setValue(toCookList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

}
