package com.numny.mstarttask.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.database.models.EmployeeModel;

import java.util.List;

@Dao
public interface EmployeeDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from employeestable")
    LiveData<List<EmployeeModel>> getOrderEmployees();


    @Query("SELECT * from employeestable")
    List<EmployeeModel> getEmplyeesList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EmployeeModel employeeModel);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<EmployeeModel> employeeModels);

    @Query("DELETE FROM employeestable")
    void deleteAll();
}
