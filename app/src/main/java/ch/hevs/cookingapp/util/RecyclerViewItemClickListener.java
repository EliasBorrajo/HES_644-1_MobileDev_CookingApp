package ch.hevs.cookingapp.util;

import android.view.View;

/**
 * Allows us to have interfaces that we implement, which will remind us to implement the necessary method
 */
public interface RecyclerViewItemClickListener
{
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
