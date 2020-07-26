package com.numny.mstarttask.database.configuration;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.numny.mstarttask.database.dao.DepartmentDao;
import com.numny.mstarttask.database.dao.EmployeeDao;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.database.models.EmployeeModel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {DepartmentModel.class, EmployeeModel.class}, version = 3, exportSchema = false)

abstract public class RoomDatabase  extends androidx.room.RoomDatabase {
    public abstract DepartmentDao departmentsDao();
    public abstract EmployeeDao employeeDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            RoomDatabase.class, "mstart_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static androidx.room.RoomDatabase.Callback sRoomDatabaseCallback = new androidx.room.RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                DepartmentDao dao = INSTANCE.departmentsDao();
//                dao.deleteAll();
//
//            });
        }
    };
}
