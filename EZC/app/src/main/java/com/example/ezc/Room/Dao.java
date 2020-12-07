package com.example.ezc.Room;

import androidx.room.*;

import java.util.List;

@androidx.room.Dao
public interface Dao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DatabaseEntity dbEntity);


    //geteverything
    @Query("select * from db_table")
    List<DatabaseEntity> getEverything();

    //deleteall
    @Query("delete from db_table")
    void deleteAll();

    @Delete()
    void delete(DatabaseEntity dbEntity);

    @Update()
    void update(DatabaseEntity dbEntity);

    //geteverythingbyscore
    @Query("select * from db_table order by score desc limit 10")
    List<DatabaseEntity> getTopScoreByOrder();

}
