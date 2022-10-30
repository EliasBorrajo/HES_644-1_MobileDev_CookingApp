package ch.hevs.cookingapp.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.hevs.cookingapp.BaseApp;
import ch.hevs.cookingapp.database.async.category.CreateCategory;
import ch.hevs.cookingapp.database.async.category.DeleteCategory;
import ch.hevs.cookingapp.database.async.category.UpdateCategory;
import ch.hevs.cookingapp.database.entity.CategoryEntity;
import ch.hevs.cookingapp.util.OnAsyncEventListener;

public class CategoryRepository
{
    private static CategoryRepository instance;

    private CategoryRepository() {
    }

    public static CategoryRepository getInstance() {
        if (instance == null) {
            synchronized (CategoryRepository.class) {
                if (instance == null) {
                    instance = new CategoryRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CategoryEntity> getCategory(final Long categoryId, Application application) {
        return ((BaseApp) application).getDatabase().categoryDao().getById(categoryId);
    }

    public LiveData<List<CategoryEntity>> getCategorys(Application application) {
        return ((BaseApp) application).getDatabase().categoryDao().getAll();
    }

    public void insert(final CategoryEntity category, OnAsyncEventListener callback,
                       Application application) {
        new CreateCategory(application, callback).execute(category);
    }

    public void update(final CategoryEntity category, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCategory(application, callback).execute(category);
    }

    public void delete(final CategoryEntity category, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCategory(application, callback).execute(category);
    }
}
