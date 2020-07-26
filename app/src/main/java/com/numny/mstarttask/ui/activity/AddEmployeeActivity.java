package com.numny.mstarttask.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.database.models.EmployeeModel;
import com.numny.mstarttask.ui.adapters.DapartmentsSpinnerAdapter;
import com.numny.mstarttask.ui.base.BaseActivity;
import com.numny.mstarttask.utils.DateHelper;
import com.numny.mstarttask.utils.DecryptedString;
import com.numny.mstarttask.utils.EmailUtil;
import com.numny.mstarttask.utils.PhoneNumberUtil;
import com.numny.mstarttask.utils.StringUtil;
import com.numny.mstarttask.viewmodel.DepartmentsViewModel;
import com.numny.mstarttask.viewmodel.EmployeeViewModel;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddEmployeeActivity extends BaseActivity<EmployeeViewModel> implements View.OnClickListener {

    @BindView(R.id.update_btn)
    Button saveBtn;
    @BindView(R.id.male_rb)
    RadioButton male_rb;
    @BindView(R.id.genderGroup)
    RadioGroup genderGroup;
    @BindView(R.id.female_rb)
    RadioButton female_rb;
    @BindView(R.id.first_name_et)
    EditText first_name_et;
    @BindView(R.id.last_name_et)
    EditText last_name_et;
    @BindView(R.id.address_et)
    EditText address_et;
    @BindView(R.id.mobile_number_et)
    EditText mobile_number_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.email_et)
    EditText email_et;

    @BindView(R.id.departments_spinner)
    Spinner departments_spinner;


    String gender = "", departmentName ="";
    int departmentID;
    boolean isValidEmail, isValidMobileNumber;
    EmployeeModel employeeModel = new EmployeeModel();

    private static final int PICK_FROM_GALLERY = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_employee);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_new_employee));

        DepartmentsViewModel departmentsViewModel = ViewModelProviders.of(this).get(DepartmentsViewModel.class);

        departmentsViewModel.getAllDepartments().observe(this, new Observer<List<DepartmentModel>>() {
            @Override
            public void onChanged(@Nullable final List<DepartmentModel> departmentModels) {
                // Update the cached copy of the words in the adapter.
                if (departmentModels.size() > 0) {
                    addDepartmentsToSpinner(departmentModels);
                }
            }
        });

        genderGroup
                .setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (radioGroup, id) -> {
                    if (id == R.id.female_rb)
                        gender = getString(R.string.female);
                    else
                        gender = getString(R.string.male);

                });
        saveBtn.setOnClickListener(this);
        viewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);

        emailValidty();
        phoneValidty();

    }

    private void emailValidty() {
        email_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 0) {
                    email_et.setError(getString(R.string.this_field_required));
                } else {
                    if (EmailUtil.isValidEmail(s)) {
                        isValidEmail = true;
                        email_et.setError(null);

                    } else {
                        email_et.setError(getString(R.string.invalid_email));
                        isValidEmail = true;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
    }

    private void phoneValidty() {
        email_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 0) {
                    mobile_number_et.setError(getString(R.string.this_field_required));
                } else {
                    if (PhoneNumberUtil.validCellPhone(s.toString())) {
                        isValidMobileNumber = true;
                        mobile_number_et.setError(null);

                    } else {
                        mobile_number_et.setError(getString(R.string.invalid_mobile_number));

                        isValidMobileNumber = true;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
    }

    @NonNull
    @Override
    protected EmployeeViewModel createViewModel() {
        return null;
    }

    boolean checkFieldsValidty() {
        String reauiredField = getString(R.string.this_field_required);
//        saveBtn.setTextAppearance(this, R.style.MyButtonStyle);

        if (StringUtil.isStringEmpty(first_name_et.getText().toString())) {
            first_name_et.setError(reauiredField);
            return false;
        } else if (StringUtil.isStringEmpty(last_name_et.getText().toString())) {
            first_name_et.setError(null);
            last_name_et.setError(reauiredField);
            return false;
        } else if (StringUtil.isStringEmpty(email_et.getText().toString())) {
            email_et.setError(reauiredField);
            first_name_et.setError(null);
            last_name_et.setError(null);
            return false;
        } else if (StringUtil.isStringEmpty(mobile_number_et.getText().toString())) {
            mobile_number_et.setError(reauiredField);
            email_et.setError(null);
            first_name_et.setError(null);
            last_name_et.setError(null);
            return false;
        } else if (StringUtil.isStringEmpty(password_et.getText().toString())) {
            mobile_number_et.setError(null);
            email_et.setError(null);
            first_name_et.setError(null);
            last_name_et.setError(null);
            password_et.setError(reauiredField);
            return false;
        } else if (StringUtil.isStringEmpty(address_et.getText().toString())) {
            address_et.setError(null);
            mobile_number_et.setError(null);
            email_et.setError(null);
            first_name_et.setError(null);
            last_name_et.setError(null);
            password_et.setError(reauiredField);
            return false;
        } else if (!isValidEmail) {
            email_et.setError(getString(R.string.invalid_email));
            return false;
        } else if (!isValidMobileNumber) {
            mobile_number_et.setError(getString(R.string.invalid_mobile_number));
            return false;
        } else {
            address_et.setError(null);
            mobile_number_et.setError(null);
            email_et.setError(null);
            first_name_et.setError(null);
            last_name_et.setError(null);
            password_et.setError(null);

            return true;
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_btn:
                if (checkFieldsValidty()) {
                    addEmployee();
                }
                break;
        }
    }

    private void addEmployee() {
        employeeModel.setFirst_Name(first_name_et.getText().toString());
        employeeModel.setLast_Name(last_name_et.getText().toString());
        employeeModel.setEmail(email_et.getText().toString());
        employeeModel.setAddress(address_et.getText().toString());
        employeeModel.setDateTime_UTC(DateHelper.getCurrentDate().toString());
        employeeModel.setMobile_Number(mobile_number_et.getText().toString());
        employeeModel.setGender(gender);
        employeeModel.setDepartment_ID(departmentID);
        employeeModel.setDepartmentName(departmentName);

        try {
            SecretKey key = DecryptedString.generateKey(getString(R.string.secret_password));

            byte[] encryptedKey = DecryptedString.encryptMsg(password_et.getText().toString(), key);
            employeeModel.setPassword(encryptedKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidParameterSpecException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        viewModel.insertSingleEmployee(employeeModel);
        DynamicToast.makeSuccess(this, getString(R.string.added_successfully)).show();

        finish();


    }


    private void addDepartmentsToSpinner(List<DepartmentModel> departmentModels) {

        DapartmentsSpinnerAdapter adapter = new DapartmentsSpinnerAdapter(AddEmployeeActivity.this,
                R.layout.spinner_department,
                departmentModels);
        departments_spinner.setAdapter(adapter);


        departments_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                departmentID = departmentModels.get(position).getID();
                departmentName = departmentModels.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void pickImage(View view) {
        if (ActivityCompat.checkSelfPermission(AddEmployeeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddEmployeeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);


        } else {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, PICK_FROM_GALLERY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {

                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.profile_img);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            employeeModel.setPhoto(selectedImage.toString());

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
