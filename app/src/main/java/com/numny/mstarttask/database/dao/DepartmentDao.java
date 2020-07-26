package com.numny.mstarttask.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.numny.mstarttask.database.models.DepartmentModel;
import java.util.List;

@Dao
public interface DepartmentDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from departmentstable ORDER BY ID DESC")
    LiveData<List<DepartmentModel>> getAllDepartments();

    @Query("SELECT * from departmentstable WHERE ID = :departmnetID")
    LiveData<DepartmentModel>getDepartmentByID(int departmnetID);

    @Query("SELECT * from departmentstable")
    List<DepartmentModel> getDepartmentsList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DepartmentModel departmentModel);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(DepartmentModel departmentModel);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<DepartmentModel> departmentModels);

    @Query("DELETE FROM departmentstable")
    void deleteAll();


    @Delete
    void deleteSingleDepartmnet(DepartmentModel departmentModel);
}
