package com.example.ezc.Room;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DatabaseTest extends TestCase {

    private Database db;
    private Dao dao;

    @Before
    public void setUp() throws Exception {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),Database.class).allowMainThreadQueries().build();
        dao = db.DatabaseDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void testInsertScore(){
    dao.deleteAll();

    DatabaseEntity score1 = new DatabaseEntity("Third", 234);
    score1.setId(1);
    DatabaseEntity score2 = new DatabaseEntity("Aasd", 32);
    score2.setId(2);
    DatabaseEntity score3 = new DatabaseEntity("324dd", 12);
    score3.setId(3);
    DatabaseEntity score4 = new DatabaseEntity("Noname", 10000);
    score4.setId(4);
    DatabaseEntity score5 = new DatabaseEntity("NameNo", 465);
    score5.setId(5);
    DatabaseEntity score6 = new DatabaseEntity("More", 212);
    score6.setId(6);
    DatabaseEntity score7 = new DatabaseEntity("Yep", 36);
    score7.setId(7);
    DatabaseEntity score8 = new DatabaseEntity("Da", 34);
    score8.setId(8);
    DatabaseEntity score9 = new DatabaseEntity("ThanTwo", 5);
    score9.setId(9);
    DatabaseEntity score10 = new DatabaseEntity("Less", 111);
    score10.setId(10);


    dao.insert(score1);
    dao.insert(score2);
    dao.insert(score3);
    dao.insert(score4);
    dao.insert(score5);
    dao.insert(score6);
    dao.insert(score7);
    dao.insert(score8);
    dao.insert(score9);
    dao.insert(score10);


    List<DatabaseEntity> scorelist = dao.getEverything();
    List<DatabaseEntity> equlist = Arrays.asList(score1,score2,score3,score4,score5,score6,score7,score8,score9,score10);
    Assert.assertEquals(equlist,scorelist);

    }

    @Test
    public void testDeleteScore(){
        dao.deleteAll();

        DatabaseEntity score1 = new DatabaseEntity("Third", 234);
        score1.setId(1);
        dao.insert(score1);

        dao.delete(score1);

        List<DatabaseEntity> scorelist = dao.getEverything();

        Assert.assertTrue(scorelist.isEmpty());

    }

    @Test
    public void testTopScores(){
        dao.deleteAll();
        DatabaseEntity score1 = new DatabaseEntity("Third", 234);
        score1.setId(1);
        DatabaseEntity score2 = new DatabaseEntity("Aasd", 32);
        score2.setId(2);
        DatabaseEntity score3 = new DatabaseEntity("324dd", 12);
        score3.setId(3);
        DatabaseEntity score4 = new DatabaseEntity("Noname", 10000);
        score4.setId(4);
        DatabaseEntity score5 = new DatabaseEntity("NameNo", 465);
        score5.setId(5);
        DatabaseEntity score6 = new DatabaseEntity("More", 212);
        score6.setId(6);
        DatabaseEntity score7 = new DatabaseEntity("Yep", 36);
        score7.setId(7);
        DatabaseEntity score8 = new DatabaseEntity("Da", 34);
        score8.setId(8);
        DatabaseEntity score9 = new DatabaseEntity("Asd", 5);
        score9.setId(9);
        DatabaseEntity score10 = new DatabaseEntity("57th", 111);
        score10.setId(10);
        DatabaseEntity score11 = new DatabaseEntity("Iwannabefirst", 1141);
        score11.setId(11);
        DatabaseEntity score12 = new DatabaseEntity("Hopeless", 1115);
        score12.setId(12);


        dao.insert(score1);
        dao.insert(score2);
        dao.insert(score3);
        dao.insert(score4);
        dao.insert(score5);
        dao.insert(score6);
        dao.insert(score7);
        dao.insert(score8);
        dao.insert(score9);
        dao.insert(score10);
        dao.insert(score11);
        dao.insert(score12);

        List<DatabaseEntity> descList = dao.getTopScoreByOrder();
        Assert.assertEquals(10, descList.size());

    }

}