package com.numny.mstarttask.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.database.models.EmployeeModel;
import com.numny.mstarttask.ui.adapters.EmployeesAdapter;
import com.numny.mstarttask.ui.base.BaseActivity;
import com.numny.mstarttask.viewmodel.DepartmentsViewModel;
import com.numny.mstarttask.viewmodel.EmployeeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeesActivity extends BaseActivity<DepartmentsViewModel> implements EmployeesAdapter.OnEmployeeAdapter {

    private DepartmentsViewModel viewModel;
    EmployeesAdapter employeesAdapter;
    EmployeeViewModel employeeViewModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_employees)
    RecyclerView rv_employees;

    @NonNull
    @Override
    protected DepartmentsViewModel createViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.employees));

        viewModel = ViewModelProviders.of(this).get(DepartmentsViewModel.class);

        viewModel.getAllDepartments().observe(this, new Observer<List<DepartmentModel>>() {
            @Override
            public void onChanged(@Nullable final List<DepartmentModel> departmentModels) {
                // Update the cached copy of the words in the adapter.
                System.out.println("dep number " + departmentModels.size() + "");


            }
        });


        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
//         employeeViewModel.getEmployeesList();
        employeeViewModel.getEmployeesList();
        employeesAdapter = new EmployeesAdapter(this);
        rv_employees.setAdapter(employeesAdapter);
        rv_employees.setLayoutManager(new LinearLayoutManager(this));
        observeeEmployees();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                goToActivity(this, AddEmployeeActivity.class);
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onRowClicked(EmployeeModel employeeModel) {

    }


    void observeeEmployees(){
        employeeViewModel.getAllEmployees().observe(this, new Observer<List<EmployeeModel>>() {
            @Override
            public void onChanged(@Nullable final List<EmployeeModel> employeeModels) {
                // Update the cached copy of the words in the adapter.

                if (employeeModels.size() > 0) {


                    employeesAdapter.setItems(employeeModels);
                }
            }
        });
    }
}