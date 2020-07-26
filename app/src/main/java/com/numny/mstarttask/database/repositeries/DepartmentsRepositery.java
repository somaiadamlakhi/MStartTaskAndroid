package com.numny.mstarttask.database.repositeries;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.numny.mstarttask.database.configuration.RoomDatabase;
import com.numny.mstarttask.database.dao.DepartmentDao;
import com.numny.mstarttask.database.models.DepartmentModel;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
public class DepartmentsRepositery {

    private DepartmentDao departmentDao;
    private LiveData<List<DepartmentModel>> mAllDepartments;

    // Note that in order to unit test the DepartmentsRepositery, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.

    public DepartmentsRepositery(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        departmentDao = db.departmentsDao();
        mAllDepartments = departmentDao.getAllDepartments();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<DepartmentModel>> getAllDepartements() {
        return mAllDepartments;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the departments thread, blocking the UI.
    public void insert(List<DepartmentModel> departmentModels) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
//            departmentDao.deleteAll();
            departmentDao.insertAll(departmentModels);
        });
    }

    public void insertSingleDepartmnet(DepartmentModel departmentModel) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
//            departmentDao.deleteAll();
            departmentDao.insert(departmentModel);
        });
    }

    public void updateSingleDepartmnet(DepartmentModel departmentModel) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            departmentDao.update(departmentModel);
        });
    }

    public void deleteSingleDepartmnet(DepartmentModel departmentModel) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            departmentDao.deleteSingleDepartmnet(departmentModel);
        });
    }
}
