package ch.hevs.cookingapp.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.util.B64Converter;

/**
 * A Firebase query LiveData implementation.
 * It converts the DataSnapshot to a list of entities.
 * The class is generic, so it can be used with any class that extends {@link CookEntity}.
 *
 * @param <> The type of the entity to convert to.
 *           Must have a public constructor with a single DataSnapshot parameter.
 */
public class CookLiveData extends LiveData<CookEntity>
{
    private static final String TAG = "CooksLiveData";      // This is the tag for the logcat output

    private final DatabaseReference reference;              // This is the reference to the database node where the data is stored (e.g. "cooks")
    private final CookLiveData.MyValueEventListener listener = new CookLiveData.MyValueEventListener(); // This is the listener that will be called when the data changes in the database node (e.g. "cooks")

    /**
     * Constructor for the class that takes the reference to the database node and the owner of the data as parameters
     * @param ref The reference to the database node where the data is stored (e.g. "cooks")
     */
    public CookLiveData(DatabaseReference ref)
    {
        reference = ref;
    }

    /**
     * This method is called when the LiveData object is active (i.e. when the activity is visible)
     * and it adds the listener to the database node (e.g. "cooks")
     * so that it can be notified when the data changes in the database node (e.g. "cooks")
     */
    @Override
    protected void onActive()
    {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    /**
     * This method is called when the LiveData object is inactive (i.e. when the activity is not visible)
     * and it removes the listener from the database node (e.g. "cooks")
     * so that it is not notified when the data changes in the database node (e.g. "cooks")
     * This is done to save battery power and network bandwidth
     */
    @Override
    protected void onInactive()
    {
        Log.d(TAG, "onInactive");
    }

    /**
     * Internal class that implements the ValueEventListener interface
     * and is used to listen to changes in the database node (e.g. "cooks")
     * and convert the DataSnapshot to a list of entities
     * and set the value of the LiveData object to the list of entities
     * so that the observers of the LiveData object can be notified
     * when the data changes in the database node (e.g. "cooks")
     * and update the UI accordingly
     */
    private class MyValueEventListener implements ValueEventListener
    {
        /**
         * This method is called when the data changes in the database node (e.g. "cooks")
         * and it converts the DataSnapshot to a list of entities
         * and sets the value of the LiveData object to the list of entities
         * so that the observers of the LiveData object can be notified
         * @param dataSnapshot
         */
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            CookEntity entity = dataSnapshot.getValue(CookEntity.class); // This is the entity that will be created from the DataSnapshot
            entity.setEmail(B64Converter.decodeEmailB64(dataSnapshot.getKey())); // This is the key of the node (e.g. "user1")
            setValue(entity);                       // This sets the value of the LiveData object to the entity
        }

        /**
         * This method is called when there is an error in getting the data from the database node (e.g. "cooks")
         * @param databaseError : The error that occurred
         */
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}

