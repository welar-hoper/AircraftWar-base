package edu.hitsz.layout;

import edu.hitsz.application.Main;
import edu.hitsz.scoreboard.ScoreBoard;
import edu.hitsz.scoreboard.ScoreRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.System.exit;

public class ScoreBoardLayout {
    private JPanel mainPanel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JLabel modeLabel;
    private JLabel scoreTableNameLabel;
    private JScrollPane scoreTableScorllPane;
    private JButton backButton;
    private JButton quitButton;

    private final ScoreBoard scoreBoard;

    private final String[] columnNames = {"名次", "玩家名", "得分", "记录时间"};

    private final DefaultTableModel model = new DefaultTableModel(null, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // 所有单元格不可编辑
        }
    };

    public ScoreBoardLayout(ScoreBoard scoreBoard, int newScore, String mode) {
        this.scoreBoard = scoreBoard;

        initUI();

        scoreTable.setModel(model);
        scoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoreTable.setAutoCreateRowSorter(true); // 可排序，便于名次变化时视图/模型索引转换
        modeLabel.setText("难度：" + mode);

        // 1) 先显示旧的排行榜
        setTableData(scoreBoard.getAllRecords());

        // 2) 等面板真的显示后，再提示输入用户名（只执行一次）
        mainPanel.addHierarchyListener(new HierarchyListener() {
            private boolean prompted = false;

            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if (!prompted
                        && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0
                        && mainPanel.isShowing()) {
                    prompted = true;
                    SwingUtilities.invokeLater(() -> promptAndRefresh(newScore));
                }
            }
        });

        // 删除按钮逻辑
        deleteButton.addActionListener(e -> {
            int viewRow = scoreTable.getSelectedRow();
            if (viewRow == -1) {
                JOptionPane.showMessageDialog(mainPanel, "请先选择一条记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(mainPanel, "是否确定删除选中的玩家？", "选择一个选项", JOptionPane.YES_NO_CANCEL_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            int modelRow = scoreTable.convertRowIndexToModel(viewRow);
            List<ScoreRecord> all = scoreBoard.getAllRecords();
            if (modelRow < 0 || modelRow >= all.size()) {
                JOptionPane.showMessageDialog(mainPanel, "所选行无效", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ScoreRecord toDelete = all.get(modelRow);
            scoreBoard.delete(toDelete);

            setTableData(scoreBoard.getAllRecords());
            scoreTable.clearSelection();
        });

        backButton.addActionListener(e -> {
            Main.cardPanel.remove(mainPanel);
            Main.game = null;
            Main.cardLayout.show(Main.cardPanel, "startMenu");
            Main.restartWatcher();
        });

        quitButton.addActionListener(e -> exit(0));
    }

    private void initUI() {
        mainPanel = new JPanel(new GridBagLayout());

        modeLabel = new JLabel("难度：EASY");
        Font modeFont = modeLabel.getFont().deriveFont(Font.BOLD, 16f);
        modeLabel.setFont(modeFont);
        modeLabel.setPreferredSize(new Dimension(modeLabel.getPreferredSize().width, 50));

        scoreTableNameLabel = new JLabel("排行榜");
        scoreTableNameLabel.setFont(scoreTableNameLabel.getFont().deriveFont(16f));

        scoreTable = new JTable();
        scoreTableScorllPane = new JScrollPane(scoreTable);
        scoreTableScorllPane.setBorder(null);
        scoreTableScorllPane.setMaximumSize(new Dimension(512, 300));

        deleteButton = new JButton("删除选中记录");
        deleteButton.setPreferredSize(new Dimension(256, deleteButton.getPreferredSize().height));

        backButton = new JButton("返回");
        backButton.setPreferredSize(new Dimension(256, backButton.getPreferredSize().height));

        quitButton = new JButton("退出游戏");
        quitButton.setPreferredSize(new Dimension(256, quitButton.getPreferredSize().height));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // row 0: modeLabel (左上)
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 5, 10);
        mainPanel.add(modeLabel, gbc);

        // row 1: "排行榜"
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 5, 10);
        mainPanel.add(scoreTableNameLabel, gbc);

        // row 2: table
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 10, 10);
        mainPanel.add(scoreTableScorllPane, gbc);

        // row 3: delete
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 10, 8, 10);
        mainPanel.add(deleteButton, gbc);

        // row 4: back
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 8, 10);
        mainPanel.add(backButton, gbc);

        // row 5: quit
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 10, 10);
        mainPanel.add(quitButton, gbc);

        // row 6: spacer（对应 form 里的 vspacer）
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        gbc.gridy = 6;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(filler, gbc);
    }

    private void promptAndRefresh(int newScore) {
        String msg = "游戏结束，你的得分为 " + newScore + "，\n请输入名字记录得分：";
        String inputName = JOptionPane.showInputDialog(mainPanel, msg, "输入", JOptionPane.QUESTION_MESSAGE);
        if (inputName != null) {
            String name = inputName.trim();
            if (!name.isEmpty()) {
                scoreBoard.add(new ScoreRecord(name, newScore, LocalDateTime.now()));
            }
        }
        // 3) 用户确认（或取消）后，刷新排行榜
        setTableData(scoreBoard.getAllRecords());
    }

    public void setTableData(List<ScoreRecord> records) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        model.setRowCount(0); // 清空旧数据
        int rank = 1;
        for (ScoreRecord r : records) {
            model.addRow(new Object[]{
                    rank++,
                    r.getName(),
                    r.getScore(),
                    r.getDateTime().format(fmt)
            });
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}