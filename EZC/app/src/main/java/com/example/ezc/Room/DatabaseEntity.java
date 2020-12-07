package com.example.ezc.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName= "db_table")
public class DatabaseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;



    private String name;

    private int score;

    public DatabaseEntity(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseEntity)) return false;
        DatabaseEntity that = (DatabaseEntity) o;
        return id == that.id &&
                score == that.score &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, score);
    }
}
