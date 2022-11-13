package ch.hevs.cookingapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.util.RecyclerViewItemClickListener;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                                              .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    // TODO: contrôler si ok ou pas
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(RecipeEntity.class))
            holder.mTextView.setText(((RecipeEntity) item).getName());
        if (item.getClass().equals(CookEntity.class))
            holder.mTextView.setText(((CookEntity) item).getFirstName() + " " + ((CookEntity) item).getLastName());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    //TODO contrôler si ok ou pas
    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof RecipeEntity) {
                        return ((RecipeEntity) mData.get(oldItemPosition)).getId().equals(((RecipeEntity) data.get(newItemPosition)).getId());
                    }
                    if (mData instanceof CookEntity) {
                        return ((CookEntity) mData.get(oldItemPosition)).getEmail().equals(
                                ((CookEntity) data.get(newItemPosition)).getEmail());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof RecipeEntity) {
                        RecipeEntity newRecipe = (RecipeEntity) data.get(newItemPosition);
                        RecipeEntity oldRecipe = (RecipeEntity) mData.get(newItemPosition);
                        return newRecipe.getId().equals(oldRecipe.getId())
                                && Objects.equals(newRecipe.getName(), oldRecipe.getName())
                                && newRecipe.getCreator().equals(oldRecipe.getCreator());
                    }
                    if (mData instanceof CookEntity) {
                        CookEntity newCook = (CookEntity) data.get(newItemPosition);
                        CookEntity oldCook = (CookEntity) mData.get(newItemPosition);
                        return Objects.equals(newCook.getEmail(), oldCook.getEmail())
                                && Objects.equals(newCook.getFirstName(), oldCook.getFirstName())
                                && Objects.equals(newCook.getLastName(), oldCook.getLastName())
                                && newCook.getPassword().equals(oldCook.getPassword());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
