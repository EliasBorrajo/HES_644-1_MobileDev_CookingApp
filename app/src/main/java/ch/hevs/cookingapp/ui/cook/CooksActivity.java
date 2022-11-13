package ch.hevs.cookingapp.ui.cook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ch.hevs.cookingapp.R;
import ch.hevs.cookingapp.adapter.RecyclerAdapter;
import ch.hevs.cookingapp.database.entity.CookEntity;
import ch.hevs.cookingapp.database.entity.RecipeEntity;
import ch.hevs.cookingapp.ui.BaseActivity;
import ch.hevs.cookingapp.ui.recipe.RecipeDetailActivity;
import ch.hevs.cookingapp.ui.recipe.RecipesActivity;
import ch.hevs.cookingapp.util.RecyclerViewItemClickListener;
import ch.hevs.cookingapp.viewmodel.cook.CookListViewModel;
import ch.hevs.cookingapp.viewmodel.recipe.RecipeListViewModel;

public class CooksActivity extends BaseActivity
{
    private static final String TAG = "CooksActivity";

    private List<CookEntity> cooks;
    private RecyclerAdapter<CookEntity> adapter;
    private CookListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_cooks, frameLayout);
        setTitle(getString(R.string.title_activity_cooks));
        navigationView.setCheckedItem(position);

        // On va créer nos différents élements graphiques, et les lier entre eux pour créer une interface

        RecyclerView recyclerView = findViewById(R.id.cooksRecyclerView);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Sera le sépareteur entre elements
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                                                                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);



        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String user = settings.getString(BaseActivity.PREFS_USER, null);

        //String meals = getIntent().getStringExtra(String.valueOf(R.string.meals));

        cooks = new ArrayList<>();

        // On crée un boutton Listener pour chaque élément de la liste
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + (cooks.get(position).getFirstName() + " " + cooks.get(position).getLastName()));

                Intent intent = new Intent(CooksActivity.this, CookActivity.class);
                intent.setFlags(
                                Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                                );
                intent.putExtra(String.valueOf(R.string.selectedCook), cooks.get(position).getEmail());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        CookListViewModel.Factory factory = new CookListViewModel.Factory(getApplication());
        viewModel = new ViewModelProvider((FragmentActivity) this, (ViewModelProvider.Factory) factory).get(CookListViewModel.class);

        LiveData<List<CookEntity>> cook = viewModel.getCooks();
        cook.observe(this, cookEntities ->
            {
                if (cookEntities != null)
                {
                    cooks = cookEntities;
                    adapter.setData(cooks);
                }
            });
        recyclerView.setAdapter(adapter);
    }

}