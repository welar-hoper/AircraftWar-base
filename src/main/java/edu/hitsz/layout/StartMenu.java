package edu.hitsz.layout;

import edu.hitsz.application.*;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class StartMenu {
    private JButton easyMode;
    private JButton normalMode;
    private JButton hardMode;
    private JComboBox<String> musicSwitch;
    private JPanel mainPanel;
    private JLabel musicLabel;

    public StartMenu() {
        initUI();
        bindActions();
    }

    private void initUI() {
    mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setName("startMenu");

    easyMode = createModeButton("简单模式");
    normalMode = createModeButton("普通模式");
    hardMode = createModeButton("困难模式");

    musicLabel = new JLabel("音效");
    musicSwitch = new JComboBox<>(new String[]{"开", "关"});
    musicSwitch.setSelectedItem("开");
    musicSwitch.setPreferredSize(new Dimension(125, 40));

    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    bottom.setOpaque(false);
    bottom.add(musicLabel);
    bottom.add(musicSwitch);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.weightx = 1;
    gbc.weighty = 1; // 关键：每一行都分到同等的垂直空间
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;

    gbc.gridy = 0;
    gbc.insets = new Insets(10, 0, 10, 0);
    mainPanel.add(easyMode, gbc);

    gbc.gridy = 1;
    gbc.insets = new Insets(10, 0, 10, 0);
    mainPanel.add(normalMode, gbc);

    gbc.gridy = 2;
    gbc.insets = new Insets(10, 0, 10, 0);
    mainPanel.add(hardMode, gbc);

    gbc.gridy = 3;
    gbc.insets = new Insets(10, 0, 10, 0);
    mainPanel.add(bottom, gbc);
}

    private JButton createModeButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 75));
        Font base = button.getFont();
        button.setFont(base.deriveFont(16f));
        return button;
    }

    private void bindActions() {
        easyMode.addActionListener(e -> startGame(EasyGame::new));
        normalMode.addActionListener(e -> startGame(NormalGame::new));
        hardMode.addActionListener(e -> startGame(HardGame::new));
    }

    private void startGame(Supplier<? extends Game> gameSupplier) {
        Main.game = gameSupplier.get();
        Main.cardPanel.add(Main.game);
        Main.cardLayout.last(Main.cardPanel);

        boolean musicOn = "开".equals(musicSwitch.getSelectedItem());
        Main.game.musicController.musicOn(musicOn);
        Main.game.musicController.playBackgroundMusic();
        Main.game.action();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}