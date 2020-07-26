package com.numny.mstarttask.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "DepartmentsTable")
public class DepartmentModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "Server_Date_Time")
    private String Server_Date_Time;

    @ColumnInfo(name = "DateTime_UTC")
    private String DateTime_UTC;

    @ColumnInfo(name = "Update_DateTime_UTC")
    private String Update_DateTime_UTC;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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


}
