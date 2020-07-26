package com.numny.mstarttask.database.repositeries;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.numny.mstarttask.database.configuration.RoomDatabase;
import com.numny.mstarttask.database.dao.EmployeeDao;
import com.numny.mstarttask.database.models.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
public class EmployeesRepositery {

    private EmployeeDao employeeDao;
    private LiveData<List<EmployeeModel>> mAllEmployees;
    List<EmployeeModel> empList = new ArrayList<>();
    // Note that in order to unit test the Employees Repositery, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.

    public EmployeesRepositery(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        employeeDao = db.employeeDao();
//        empList =employeeDao.getEmplyeesList();
        mAllEmployees = new MutableLiveData<>();
        mAllEmployees = employeeDao.getOrderEmployees();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<EmployeeModel>> getAllEmployees() {
        return mAllEmployees;
    }
    public List<EmployeeModel> getEmployeesList() {
        return empList;
    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the employees thread, blocking the UI.
    public void insert(List<EmployeeModel> employeeModels) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
//            employeeDao.deleteAll();
            employeeDao.insertAll(employeeModels);
        });
    }

    public void getEmpsList() {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
//            employeeDao.deleteAll();
            empList = employeeDao.getEmplyeesList();
        });
    }

    public void insertSingle(EmployeeModel employeeModel) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
//            employeeDao.deleteAll();
            employeeDao.insert(employeeModel);
        });
    }


}
