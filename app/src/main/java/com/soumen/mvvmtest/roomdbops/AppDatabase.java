package com.soumen.mvvmtest.roomdbops;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.soumen.mvvmtest.roomdbops.daos.AllUserListDao;
import com.soumen.mvvmtest.roomdbops.daos.RegisterUserDao;
import com.soumen.mvvmtest.roomdbops.daos.SimpleLoginDao;
import com.soumen.mvvmtest.roomdbops.entities.UserEntity;

/**
 * Created by Soumen on 03-01-2018.
 */
@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract SimpleLoginDao simpleLoginDao();
    public abstract RegisterUserDao registerUserDao();
    public abstract AllUserListDao allUserListDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}