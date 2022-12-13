package ch.hevs.cookingapp.database.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.firebase.CookLiveData;
import ch.hevs.cookingapp.database.firebase.CooksListLiveData;
import ch.hevs.cookingapp.util.B64Converter;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

/**
 * Repository class that works with local data source.
 * This class is used to access the database.
 * It is used by the ViewModel classes.
 */
public class CookRepository
{
    private static final String TAG = "CookRepository";
    private static CookRepository instance;

    private CookRepository()
    {
    }

    public static CookRepository getInstance()
    {
        if (instance == null)
        {
            synchronized (CookRepository.class)
            {
                if (instance == null)
                {
                    instance = new CookRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Register a new cook in the database.
     * The cook is added to the "cooks" node in the database.
     * @param newCook : the new cook to add to the database
     * @param onAsyncEventListener : the listener that will be called when the operation is completed (success or failure)
     */
    public void register(CookEntity newCook, OnAsyncEventListener onAsyncEventListener)
    {
        // Authentication with Firebase Authentication (email/password)
        FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(newCook.getEmail(),
                                                    newCook.getPassword())
                    .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Log.d(TAG, "createUserWithEmail:success");
                        newCook.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        insert(newCook, onAsyncEventListener);
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        onAsyncEventListener.onFailure(task.getException());
                    }
                });
    }


    /**
     * Authenticate a cook in the database.
     * @param email : the email of the cook to authenticate
     * @param password : the password of the cook to authenticate
     * @param listener : the listener that will be called when the operation is completed (success or failure)
     */
    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener)
    {
        FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(listener);

    }

    /**
     * Get a cook from the database.
     * The cook is retrieved from the "cooks" node in the database, using the cook's email as key.
     * @param email : the email of the cook to retrieve from the database . Will be encoded in Base64 and used as key in the database.
     * @return : the LiveData object that will be used to observe the changes in the database for the cook.
     */
    public LiveData<CookEntity> getCook(final String email)
    {
        // Encode the email to Base64
        String encodedEmail = B64Converter.encodeEmailB64(email);

        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                      .getReference("cooks")
                                                      .child(encodedEmail);
        return new CookLiveData(reference);
    }

    /**
     * Get all the cooks from the database.
     * The cooks are retrieved from the "cooks" node in the database.
     * @return : the LiveData object that will be used to observe the changes in the database for the cooks.
     */
    public LiveData<List<CookEntity>> getCooks()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                      .getReference("cooks");
        return new CooksListLiveData(reference);
    }

    /**
     * Insert a new cook in the database and in the Firebase Authentication system (email/password)
     * @param cook the new cook to insert in the database and in the Firebase Authentication system (email/password)
     */
    public void insert(final CookEntity cook, final OnAsyncEventListener callback)
    {
        // Database insertion in the "cooks" node in the database (using the cook's email as key)
        FirebaseDatabase.getInstance()
                        .getReference("cooks")
                        .child(B64Converter.encodeEmailB64(cook.getEmail()))
                        .setValue(cook, (databaseError, databaseReference) ->
                            {
                                if (databaseError != null)
                                {
                                    callback.onFailure(databaseError.toException());

                                    // Remove the cook from the Firebase Authentication system (email/password)
                                    FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .delete()
                                                .addOnCompleteListener(task ->
                                                    {
                                                        if (task.isSuccessful())
                                                        {
                                                            callback.onFailure(null);
                                                            Log.d(TAG, "Rollback successful: Cook account deleted");
                                                        }
                                                        else
                                                        {
                                                            callback.onFailure(task.getException());
                                                            Log.d(TAG, "Rollback failed: signInWithEmail:failure",
                                                                  task.getException());
                                                        }
                                                    });

                                }
                                else
                                {
                                    callback.onSuccess();
                                }
                            });
    }

    /**
     * Update a cook in the database.
     * @param cook : the cook to update in the database
     * @param callback : the listener that will be called when the operation is completed (success or failure)
     */
    public void update(final CookEntity cook, final OnAsyncEventListener callback)
    {
        // Database update in the "cooks" node in the database (using the cook's email as key) and Encode the email to Base64
        FirebaseDatabase.getInstance()
                        .getReference("cooks")
                        .child(B64Converter.encodeEmailB64(cook.getEmail()))
                        .updateChildren(cook.toMap(), (databaseError, databaseReference) ->
                            {
                                if (databaseError != null)
                                {
                                    callback.onFailure(databaseError.toException());
                                }
                                else
                                {
                                    callback.onSuccess();
                                }
                            });
        // Update the cook's email in the Firebase Authentication system (email/password)
        FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .updatePassword(cook.getPassword())
                    .addOnFailureListener(e -> Log.d(TAG, "updatePassword:failure", e));

    }

    /**
     * Delete a cook from the database and from the Firebase Authentication system (email/password)
     * @param cook : The cook to delete from the database and from the Firebase Authentication system (email/password)
     *               The cook's email will be used as key in the database and in the Firebase Authentication system (email/password).
     * @param callback : the listener that will be called when the operation is completed (success or failure)
     */
    public void delete(final CookEntity cook, OnAsyncEventListener callback)
    {
        // Database deletion in the "cooks" node in the database (using the cook's email as key) and Encode the email to Base64
        FirebaseDatabase.getInstance()
                        .getReference("cooks")
                        .child(B64Converter.encodeEmailB64(cook.getEmail()))
                        .removeValue((databaseError, databaseReference) ->
                            {
                                if (databaseError != null)
                                {
                                    callback.onFailure(databaseError.toException());
                                }
                                else
                                {
                                    callback.onSuccess();
                                }
                            });
        // Delete the cook from the Firebase Authentication system (email/password)
        FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .delete()
                    .addOnFailureListener(e -> Log.d(TAG, "delete:failure", e));
    }




}
