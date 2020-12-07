package com.example.ezc.Room;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {DatabaseEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase
{

    public abstract Dao DatabaseDao();

    private static Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static Database getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (Database.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), Database.class, "db_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();

                }

            }

        }

        return INSTANCE;

    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {

            //onopen akkor amikor megnyilik a db, oncreate pedig amikor létrejön
            super.onOpen(db);


            databaseWriterExecutor.execute(() -> {

                Dao dao = INSTANCE.DatabaseDao();
                //delete ha kell
                //dao.deleteAll();
                DatabaseEntity data = new DatabaseEntity("First",1);
                dao.insert(data);
                DatabaseEntity data2 = new DatabaseEntity("Second",2);
                dao.insert(data2);

            });
        }
    };
}
