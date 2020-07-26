package com.numny.mstarttask.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.numny.mstarttask.database.models.EmployeeModel;
import com.numny.mstarttask.database.repositeries.EmployeesRepositery;
import com.numny.mstarttask.ui.base.BaseViewModel;

import java.util.List;


public class EmployeeViewModel extends BaseViewModel {

    private MutableLiveData<List<EmployeeModel>> employeeModels;
    private MutableLiveData<Boolean> isLoading;
    private EmployeesRepositery mRepository;
    private LiveData<List<EmployeeModel>> mAllEmployees;



    public EmployeeViewModel(Application application) {
        super(application);
        mRepository = new EmployeesRepositery(application);
//        mAllEmployees = mRepository.getAllEmployees();
        employeeModels = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        mAllEmployees = new MutableLiveData<>();
        mAllEmployees = mRepository.getAllEmployees();
//        insertEmployeeToDB();
    }

    public void insertEmployeeToDB(EmployeeModel employeeModel) {



        insertSingleEmployee(employeeModel);


    }

    MutableLiveData<List<EmployeeModel>> getEmployeeModels() {
        return employeeModels;
    }



    MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }



    public void loadEmployees() {
        setIsLoading(true);
        mRepository.getAllEmployees();
    }



    public void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }


    public void setEmployeeModels(List<EmployeeModel> employeeModels) {
        setIsLoading(false);
        this.employeeModels.postValue(employeeModels);
    }



    public LiveData<List<EmployeeModel>> getAllEmployees() {
        return mAllEmployees;
    }

    public List<EmployeeModel> getEmployeesList() {
        return mRepository.getEmployeesList();
    }




    public void insertList(List<EmployeeModel> employeeModels) {
        mRepository.insert(employeeModels);
    }

    public void insertSingleEmployee(EmployeeModel employeeModel) {
        mRepository.insertSingle(employeeModel);
    }


}
