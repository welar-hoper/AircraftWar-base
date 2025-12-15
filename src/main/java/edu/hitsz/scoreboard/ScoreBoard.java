package edu.hitsz.scoreboard;

import java.util.*;
import java.io.*;


public class ScoreBoard implements ScoreRecordDao {
    private List<ScoreRecord> records = new ArrayList<>();
    private final File dataFile ;

    public ScoreBoard(String mode) {
        this.dataFile = "EASY".equals(mode)? new File("src/main/resources/logs/score_easy.txt"):
                "NORMAL".equals(mode)? new File("src/main/resources/logs/score_normal.txt"):
                        new File("src/main/resources/logs/score_hard.txt");
        loadFromFile();
    }

    private void loadFromFile() {
        if (!dataFile.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    ScoreRecord record = ScoreRecord.fromFile(line);
                    records.add(record);
                } catch (Exception e) {
                    System.err.println("跳过错误记录: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (ScoreRecord record : records) {
                writer.append(record.toFile());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print() {
        System.out.println("************************************");
        System.out.println("              得分排行榜");
        System.out.println("************************************");
        int rank = 1;
        for (ScoreRecord scoreRecord : records) {
            System.out.printf("第%d名：%s%n", rank++, scoreRecord.toDisplayString());
        }
    }

    @Override
    public void add(ScoreRecord scoreRecord) {
        records.add(scoreRecord);
        Collections.sort(records);// 使用 compareTo：分数降序，时间升序
        saveToFile();
    }

    @Override
    public void delete(ScoreRecord scoreRecord) {
        records.remove(scoreRecord);
        Collections.sort(records);
        saveToFile();
    }

    @Override
    public List<ScoreRecord> getAllRecords() {
        return records;
    }
}