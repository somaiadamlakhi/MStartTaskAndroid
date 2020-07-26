package com.numny.mstarttask.ui.activity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.databinding.DepartmentDetailsPageBinding;
import com.numny.mstarttask.ui.base.BaseActivity;
import com.numny.mstarttask.utils.AppConstatns;
import com.numny.mstarttask.utils.DateHelper;
import com.numny.mstarttask.utils.StringUtil;
import com.numny.mstarttask.viewmodel.DepartmentsViewModel;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartmentDetails extends BaseActivity<DepartmentsViewModel> {

    DepartmentModel departmentModel;
    DepartmentDetailsPageBinding departmentDetailsPageBinding;

    @NonNull
    @Override
    protected DepartmentsViewModel createViewModel() {
        return null;
    }

    @BindView(R.id.update_btn)
    Button update_btn;

    @BindView(R.id.delete_btn)
    Button delete_btn;

    @BindView(R.id.departements_et)
    EditText departements_et;

    boolean isNewDepartment, isValidDepartmnetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        departmentDetailsPageBinding = DataBindingUtil.setContentView(this, R.layout.department_details_page);
        departmentDetailsPageBinding.setLifecycleOwner(this);
        departmentDetailsPageBinding.executePendingBindings();
        ButterKnife.bind(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            departmentModel = (DepartmentModel) getIntent().getSerializableExtra(AppConstatns.DEPARTMENT_KEY);
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (departmentModel != null) {
            departmentDetailsPageBinding.setSingleDepartment(departmentModel);
            update_btn.setText(getString(R.string.update));
            delete_btn.setVisibility(View.VISIBLE);
            isNewDepartment = false;
        } else {
            update_btn.setText(getString(R.string.save));
            delete_btn.setVisibility(View.GONE);

            isNewDepartment = true;
        }


        viewModel = ViewModelProviders.of(this).get(DepartmentsViewModel.class);

    }

    public void saveDepartemnt(View view) {

        String departmnetName = departements_et.getText().toString();
        isValidDepartmnetName = isValidDepartmnetName();

        if (isNewDepartment) {
            if (isValidDepartmnetName) {
                DepartmentModel departmentModel = new DepartmentModel();
                departmentModel.setName(departmnetName);
                departmentModel.setDateTime_UTC(DateHelper.getCurrentDate().toString());

                viewModel.insertSingleDepartmnet(departmentModel);

                DynamicToast.makeSuccess(this, getString(R.string.added_successfully)).show();
                finish();
            }
        } else {

            if (isValidDepartmnetName) {
                departmentModel.setUpdate_DateTime_UTC(DateHelper.getCurrentDate().toString());
                departmentModel.setName(departmnetName);
                viewModel.updateSingleDepartmnet(departmentModel);
                DynamicToast.makeSuccess(this, getString(R.string.updated_successfully)).show();
                finish();
            }
        }
    }

    public void deleteDepartemnt(View view){
        viewModel.deleteSingleDepartmnet(departmentModel);
        DynamicToast.makeWarning(this, getString(R.string.deleted_succuessfully)).show();
        finish();
    }

    boolean isValidDepartmnetName() {
        String departmnetName = departements_et.getText().toString();

        if (StringUtil.isStringEmpty(departmnetName)) {
            departements_et.setError(getString(R.string.this_field_required));

            return false;
        } else {
            departements_et.setError(null);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}