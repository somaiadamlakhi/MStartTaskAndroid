package com.numny.mstarttask.viewmodel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.numny.App;
import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.database.repositeries.DepartmentsRepositery;
import com.numny.mstarttask.ui.base.BaseViewModel;
import com.numny.mstarttask.utils.DateHelper;
import java.util.ArrayList;
import java.util.List;



public class DepartmentsViewModel extends BaseViewModel {

    private MutableLiveData<List<DepartmentModel>> departmentsModels;
    private MutableLiveData<Boolean> isLoading;
    private DepartmentsRepositery mRepository;
    private LiveData<List<DepartmentModel>> mAllDepartments;



    public DepartmentsViewModel(Application application) {
        super(application);
        mRepository = new DepartmentsRepositery(application);
        mAllDepartments = mRepository.getAllDepartements();
        departmentsModels = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
//        insertDepartmentsToDB();
    }

    public void insertDepartmentsToDB() {


        String[] departments = getApplication().getApplicationContext().getResources().getStringArray(R.array.departments_names);
        String currentDateTime = DateHelper.getCurrentDate().toString();
        ArrayList<DepartmentModel> departmentModels = new ArrayList<>();

        for (String departmentName : departments) {
            DepartmentModel departmentModel = new DepartmentModel();
            departmentModel.setName(departmentName);
            departmentModel.setDateTime_UTC(currentDateTime);
            departmentModels.add(departmentModel);
        }

        insert(departmentModels);


    }

    MutableLiveData<List<DepartmentModel>> getDepartmetns() {
        return departmentsModels;
    }



    MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }



    public void loadDepartments() {
        setIsLoading(true);
        mRepository.getAllDepartements();
    }



    public void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }


    public void setDepartmentsModels(List<DepartmentModel> departmentsModels) {
        setIsLoading(false);
        this.departmentsModels.postValue(departmentsModels);
    }



    public LiveData<List<DepartmentModel>> getAllDepartments() {
        return mAllDepartments;
    }


    public void insert(List<DepartmentModel> departmentModels) {
        mRepository.insert(departmentModels);
    }
    public void insertSingleDepartmnet(DepartmentModel departmentModel) {
        mRepository.insertSingleDepartmnet(departmentModel);
    }
    public void updateSingleDepartmnet(DepartmentModel departmentModel) {
        mRepository.updateSingleDepartmnet(departmentModel);
    }

    public void deleteSingleDepartmnet(DepartmentModel departmentModel) {
        mRepository.deleteSingleDepartmnet(departmentModel);
    }
}
