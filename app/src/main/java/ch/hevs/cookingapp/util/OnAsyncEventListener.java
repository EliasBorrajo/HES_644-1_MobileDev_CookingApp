package ch.hevs.cookingapp.util;

/**
 * This generic interface is used as custom callback for async tasks.
 * Allows us to have interfaces that we implement, which will remind us to implement the necessary method
 */
public interface OnAsyncEventListener
{
    void onSuccess();
    void onFailure();
}
