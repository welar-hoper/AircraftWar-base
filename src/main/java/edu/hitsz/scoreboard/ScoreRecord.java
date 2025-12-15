package edu.hitsz.scoreboard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreRecord implements Comparable<ScoreRecord> {
    // ScoreRecord.java
    private static final DateTimeFormatter FILE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DISPLAY_FMT = DateTimeFormatter.ofPattern("MM-dd HH:mm");
    private final String name;
    private final int score;
    private final LocalDateTime dateTime;

    public ScoreRecord(String name, int score, LocalDateTime dateTime) {
        this.name = name;
        this.score = score;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getScore() {
        return score;
    }

    // 控制台展示用
    public String toDisplayString() {
        return name + "," + score + "," + dateTime.format(DISPLAY_FMT);
    }

    public String toFile() {
        return name + "," + score + "," + dateTime.format(FILE_FMT);
    }

    public static ScoreRecord fromFile(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Bad score line: " + line);
        }
        String name = parts[0];
        int score = Integer.parseInt(parts[1]);
        LocalDateTime dt = LocalDateTime.parse(parts[2], FILE_FMT);
        return new ScoreRecord(name, score, dt);
    }

    // 排序：分数降序，分数相同按时间升序（更早排前）
    @Override
    public int compareTo(ScoreRecord other) {
        int byScore = Integer.compare(other.score, this.score);
        if (byScore != 0) return byScore;
        return this.dateTime.compareTo(other.dateTime);
    }
}