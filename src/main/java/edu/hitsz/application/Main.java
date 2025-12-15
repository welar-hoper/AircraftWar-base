package edu.hitsz.application;

import edu.hitsz.layout.ScoreBoardLayout;
import edu.hitsz.layout.StartMenu;
import edu.hitsz.scoreboard.ScoreBoard;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final CardLayout cardLayout = new CardLayout(0,0);
    public static JPanel cardPanel = new JPanel(cardLayout);
    public static Game game = null;
    public static ScoreBoard scoreBoard;
    private static Timer overWatcher;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(cardPanel);
        StartMenu start = new StartMenu();
        cardPanel.add(start.getMainPanel());
        frame.setVisible(true);
        overWatcher = new Timer(200, e -> {
            if (game != null && game.isGameOver()) {
                overWatcher.stop();
                // 切换到排行榜界面
                scoreBoard = new ScoreBoard(game.getMode());
                ScoreBoardLayout scoreBoardLayout = new ScoreBoardLayout(scoreBoard, game.getScore(), game.getMode());
                cardPanel.add(scoreBoardLayout.getMainPanel(), "scoreBoardLayout");
                cardLayout.show(cardPanel, "scoreBoardLayout");
            }
        });
        overWatcher.start();
    }
    public static void restartWatcher(){
        overWatcher.restart();
    }
}
