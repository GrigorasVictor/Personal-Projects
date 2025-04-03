package org.example.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SimulationInterface extends JFrame{
    private JTextField timerField;
    private JTextField queueNumber;
    private JTextField clientsField;
    private JPanel MainPanel;
    private JPanel QueuePanel;
    private JButton EndButton;
    private JTable queueTable;
    private JTextField clientsRemaining;
    private JScrollPane tableScrollPane;
    private JTextArea logArea;
    private static int simulationTimer;

    public SimulationInterface(int timer, int nrQueues, int clients, String strategyName){
        clientsField.setText(clients + "");
        queueNumber.setText(nrQueues + "");
        initiateWindow(strategyName);
        createQueues(nrQueues);
        EndButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                InputInterface inputInterface = new InputInterface();
                dispose();
            }
        });
    }
    public void updateInterface(LinkedList<String> tasks, LinkedList<String> status, String queue, int remainingTasks, int time, boolean isFinished){
        if(isFinished){
            JOptionPane.showMessageDialog(null, "Simulation Done!", "Alert!", JOptionPane.INFORMATION_MESSAGE);

        }
        simulationTimer=time - 1;
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Queue", "Clients", "Status"}, 0);
        int size =Integer.parseInt(queueNumber.getText());

        queueTable = new JTable(tableModel);
        DefaultTableModel model = (DefaultTableModel) queueTable.getModel();
        for (int i = 0; i < size; i++)
            model.addRow(new Object[]{"Queue " + (i + 1),  tasks.poll(), status.poll()});

        tableScrollPane.setViewportView(queueTable);
        clientsRemaining.setText(remainingTasks + "");
        timerField.setText(time + "");
        logArea.setText(queue);
        updateColumn();
    }
    private void createQueues(int nrQueues) {
        tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Queue", "Clients", "Status"}, 0);
        queueTable = new JTable(tableModel);
        DefaultTableModel model = (DefaultTableModel) queueTable.getModel();
        for (int i = 0; i < nrQueues; i++)
            model.addRow(new Object[]{"Queue " + (i + 1),  "[]", "Inactive"});
        tableScrollPane.setViewportView(queueTable);
        updateColumn();
    }

    private void initiateWindow(String strategyName){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Simulation Window: " + strategyName);
        setSize(950, 530);
        setContentPane(MainPanel);
        setVisible(true);
        pack();
    }

    private void updateColumn(){
        queueTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if ("Inactive".equals(value)) {
                    cellComponent.setBackground(Color.GREEN);
                } else {
                    cellComponent.setBackground(Color.RED);
                }
                return cellComponent;
            }
        });
    }
}
