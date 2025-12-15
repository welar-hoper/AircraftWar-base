package edu.hitsz.scoreboard;

import java.util.List;

public interface ScoreRecordDao {
    void add(ScoreRecord scoreRecord);
    void delete(ScoreRecord scoreRecord);
    void print();
    List<ScoreRecord> getAllRecords();
}
