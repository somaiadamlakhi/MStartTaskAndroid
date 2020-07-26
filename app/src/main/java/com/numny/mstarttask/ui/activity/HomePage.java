package com.numny.mstarttask.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.ui.base.BaseActivity;
import com.numny.mstarttask.viewmodel.DepartmentsViewModel;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePage extends BaseActivity<DepartmentsViewModel> {

    @BindView(R.id.departements_btn)
    Button departements_btn;
    @BindView(R.id.employees_btn)
    Button employees_btn;

    boolean isDepartemntsZero;
    @NonNull
    @Override
    protected DepartmentsViewModel createViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(DepartmentsViewModel.class);
        opserveDepartments();

    }

    private void opserveDepartments() {

        viewModel.getAllDepartments().observe(this, new Observer<List<DepartmentModel>>() {
            @Override
            public void onChanged(@Nullable final List<DepartmentModel> departmentModels) {
                // Update the cached copy of the words in the adapter.
                System.out.println("dep number " + departmentModels.size() + "");

                if(departmentModels.size() == 0){
                    isDepartemntsZero = true;
                    employees_btn.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                }
                else {
                    employees_btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    isDepartemntsZero = false;
                }


            }
        });
    }

    public void openEmployeesPage(View view) {
        if(isDepartemntsZero){
            DynamicToast.makeError(this, getString(R.string.you_should_add_dapartments_first)).show();
        }
        else {
            goToActivity(this, EmployeesActivity.class);
        }
    }

    public void openDepartemntsPage(View view) {
        goToActivity(this, DepartemntsActivity.class);
    }

}