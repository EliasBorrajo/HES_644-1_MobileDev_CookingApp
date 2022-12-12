package ch.hevs.cookingapp.database.repository;

import android.app.Application;
import android.util.Base64;
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


    public void register(CookEntity newCook, OnAsyncEventListener onAsyncEventListener)
    {
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


    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener)
    {
        FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(listener);

    }

    public LiveData<CookEntity> getCook(final String email)
    {
        // Encode the email to Base64
        String encodedEmail = B64Converter.encodeEmailB64(email);

        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                      .getReference("cooks")
                                                      .child(encodedEmail);
        return new CookLiveData(reference);
    }

    public LiveData<List<CookEntity>> getCooks()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                                                      .getReference("cooks");
        return new CooksListLiveData(reference);
    }

    /**
     * Insert a cook in the database. If the cook already exists, replace it.
     * If it does not exist, insert it.
     * @param cook : the cook to be inserted in the database.
     * @param callback : the callback to be called when the operation is done.
     */
    public void insert2(final CookEntity cook, final OnAsyncEventListener callback)
    {
        String key = cook.getEmail(); // Use the email address as the key
        FirebaseDatabase.getInstance()
                        .getReference("cooks")
                        .child(key)
                        .setValue(cook, (databaseError, databaseReference) ->
                        {
                            if (databaseError != null)
                            {
                                callback.onFailure(databaseError.toException());
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


    public void insert(final CookEntity cook, final OnAsyncEventListener callback)
    {
        FirebaseDatabase.getInstance()
                        .getReference("cooks")
                        .child(B64Converter.encodeEmailB64(cook.getEmail()))
                        .setValue(cook, (databaseError, databaseReference) ->
                            {
                                if (databaseError != null)
                                {
                                    callback.onFailure(databaseError.toException());
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

    public void update(final CookEntity cook, final OnAsyncEventListener callback)
    {
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
        FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .updatePassword(cook.getPassword())
                    .addOnFailureListener(e -> Log.d(TAG, "updatePassword:failure", e));

    }

    public void delete(final CookEntity cook, OnAsyncEventListener callback)
    {
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
        FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .delete()
                    .addOnFailureListener(e -> Log.d(TAG, "delete:failure", e));
    }




}
