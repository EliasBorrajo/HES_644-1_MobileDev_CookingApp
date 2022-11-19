package ch.hevs.cookingapp.util;

import android.view.View;

/**
 * Allows us to have interfaces that we implement, which will remind us to implement the necessary method
 * it uses the view and the position of the item in the recycler view to be able to do something with it when clicked
 */
public interface RecyclerViewItemClickListener
{
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
