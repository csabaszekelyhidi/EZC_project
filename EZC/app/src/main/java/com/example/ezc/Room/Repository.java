package com.example.ezc.Room;


import android.app.Application;

import java.util.List;

public class Repository {


    private Dao dao;
    private List<DatabaseEntity> everythingList;




    public Repository(Application application) {

        Database db = Database.getDatabase(application);
        dao = db.DatabaseDao();
        everythingList = dao.getEverything();

    }


    public List<DatabaseEntity> getEverything() {
        return everythingList;

    }


    public void deleteAll(){

    }

    public void insert(DatabaseEntity data) {
        Database.databaseWriterExecutor.execute(()-> dao.insert(data));
    }

    public void delete(DatabaseEntity data) {
        Database.databaseWriterExecutor.execute(()-> dao.delete(data));
    }

    public void update(DatabaseEntity data) { Database.databaseWriterExecutor.execute(()-> dao.update(data));
    }





}
