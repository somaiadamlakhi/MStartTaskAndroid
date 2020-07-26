package com.numny.mstarttask.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.ui.adapters.DepartmentsAdapter;
import com.numny.mstarttask.ui.base.BaseActivity;
import com.numny.mstarttask.utils.AppConstatns;
import com.numny.mstarttask.viewmodel.DepartmentsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartemntsActivity extends BaseActivity<DepartmentsViewModel> implements DepartmentsAdapter.OnDepartmentsAdapter {
    DepartmentsAdapter departmentsAdapter;

    @BindView(R.id.departements_rv)
    RecyclerView departements_rv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @NonNull
    @Override
    protected DepartmentsViewModel createViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departemnts);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.departemnt));

        departmentsAdapter = new DepartmentsAdapter(this);
        departements_rv.setLayoutManager(new LinearLayoutManager(this));
        departements_rv.setAdapter(departmentsAdapter);



        viewModel = ViewModelProviders.of(this).get(DepartmentsViewModel.class);
        observeDepartments();


    }

    private void observeDepartments() {
        viewModel.getAllDepartments().observe(this, new Observer<List<DepartmentModel>>() {
            @Override
            public void onChanged(@Nullable final List<DepartmentModel> departmentModels) {
                // Update the cached copy of the words in the adapter.
                if (departmentModels.size() > 0) {
                    initAdapter(departmentModels);
                }
                else {
                    initAdapter(null);
                }
            }
        });

    }

    private void initAdapter(List<DepartmentModel> departmentModels) {
        departmentsAdapter.setItems(departmentModels);
    }

    @Override
    public void onRowClicked(DepartmentModel departmentModel) {
        Intent intent = new Intent(this,DepartmentDetails.class);
        intent.putExtra(AppConstatns.DEPARTMENT_KEY,departmentModel);
        startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                goToActivity(this, DepartmentDetails.class);
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}