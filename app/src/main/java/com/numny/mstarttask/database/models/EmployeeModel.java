package com.numny.mstarttask.database.models;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.numny.mstarttask.R;
import com.numny.mstarttask.utils.DateHelper;
import com.numny.mstarttask.utils.StringUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "EmployeesTable", foreignKeys = @ForeignKey(entity = DepartmentModel.class,
        parentColumns = "ID",
        childColumns = "Department_ID",
        onDelete = ForeignKey.CASCADE))
public class EmployeeModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "Department_ID")
    private int Department_ID;

    @ColumnInfo(name = "Server_Date_Time")
    private String Server_Date_Time;

    @ColumnInfo(name = "DateTime_UTC")
    public String DateTime_UTC;

    @ColumnInfo(name = "Update_DateTime_UTC")
    private String Update_DateTime_UTC;

    @ColumnInfo(name = "First_Name")
    private String First_Name;

    @ColumnInfo(name = "Last_Name")
    private String Last_Name;


    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "Email")
    private String Email;


    @ColumnInfo(name = "Password", typeAffinity = ColumnInfo.BLOB)
    private byte[] Password;

    @ColumnInfo(name = "Mobile_Number")
    private String Mobile_Number;


    @ColumnInfo(name = "Gender")
    private String Gender;

    @ColumnInfo(name = "Address")
    private String Address;

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    @ColumnInfo(name = "DepartmentName")
    private String DepartmentName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDepartment_ID() {
        return Department_ID;
    }

    public void setDepartment_ID(int department_ID) {
        Department_ID = department_ID;
    }

    public String getServer_Date_Time() {
        return Server_Date_Time;
    }

    public void setServer_Date_Time(String server_Date_Time) {
        Server_Date_Time = server_Date_Time;
    }

    public String getDateTime_UTC() {
        return DateTime_UTC;
    }

    public void setDateTime_UTC(String dateTime_UTC) {
        DateTime_UTC = dateTime_UTC;
    }

    public String getUpdate_DateTime_UTC() {
        return Update_DateTime_UTC;
    }

    public void setUpdate_DateTime_UTC(String update_DateTime_UTC) {
        Update_DateTime_UTC = update_DateTime_UTC;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public byte[] getPassword() {
        return Password;
    }

    public void setPassword(byte[] password) {
        Password = password;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    @Nullable
    @ColumnInfo(name = "Photo")
    private String Photo;


    @BindingAdapter("loadUserImage")
    public static void loadImage(ImageView view, String imageUrl) {
//        Glide.with(view.getContext())
//                .load(imageUrl).apply(new RequestOptions().circleCrop())
//                .into(view);

        if(!StringUtil.isStringEmpty(imageUrl)) {
            Uri selectedImage = Uri.parse(imageUrl);
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = view.getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            view.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @BindingAdapter("formatedDate")
    public static void formatedDate(TextView view, String dateOfInsert) {


        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            Date date = format.parse(dateOfInsert);
            view.setText(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
