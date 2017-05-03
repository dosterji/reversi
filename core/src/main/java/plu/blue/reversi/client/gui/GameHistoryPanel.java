package plu.blue.reversi.client.gui;

import plu.blue.reversi.client.model.GameHistory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * This panel displays the history of moves that have taken place
 * throughout the game.  It should be updated after every move.
 */
public class GameHistoryPanel extends JPanel {

    private JTable historyTable;
    private GameHistory history;

    private class MoveHistoryTableModel extends AbstractTableModel {
        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "#";
            } else if (column == 1) {
                return "Move";
            } else if (column == 2) {
                return "Player";
            } else {
                return "";
            }
        }

        @Override
        public int getRowCount() {
            return history.getMoveHistory().size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex >= history.getMoveHistory().size()) {
                return null;
            }

            if (columnIndex == 0) {
                return "" + (rowIndex + 1);
            } else if (columnIndex == 1) {
                return history.getMoveHistory().get(rowIndex).getCoordinate().toString();
            } else if (columnIndex == 2) {
                return history.getMoveHistory().get(rowIndex).getPlayer().getName();
            } else {
                return null;
            }
        }
    }

    /**
     * Construct a new history panel.  Currently, this places some example
     * history into the panel.  This should be removed.
     */
    public GameHistoryPanel(GameHistory gh) {
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setLayout(new BorderLayout());
        historyTable = new JTable(new MoveHistoryTableModel());

        history = gh;
        history.addTableReference(historyTable);

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setPreferredSize(new Dimension(250,0));
        historyTable.setGridColor(new Color(220,220,220));
        historyTable.setShowGrid(true);
        historyTable.setFillsViewportHeight(true);

        TableColumnModel cmod = historyTable.getColumnModel();
        cmod.getColumn(0).setPreferredWidth(20);
        cmod.getColumn(1).setPreferredWidth(20);
        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JPanel borderPanel = new JPanel(new GridLayout(1,0));
        borderPanel.setBorder(BorderFactory.createTitledBorder("History"));
        borderPanel.add(scrollPane);

        this.add(borderPanel, BorderLayout.CENTER);
    }

    public void newGame() {
        historyTable.tableChanged(null);
    }
}
