package org.example.gui;

import org.example.buisnessLogic.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputInterface extends JFrame{
    private JPanel MainPanel;
    private JButton ConfirmButton;
    private JLabel NrCLientsLabel;
    private JLabel NrQueueLabel;
    private JLabel SimTimeLabel;
    private JLabel ArrivalLabel;
    private JTextField nrClientsField;
    private JTextField nrQueuesField;
    private JTextField simTimeField;
    private JTextField arrivalMinField;
    private JComboBox comboStrategy;
    private JLabel StrategyLabel;
    private JLabel ServiceTimeLabel;
    private JTextField arrivalMaxField;
    private JTextField serviceMaxField;
    private JTextField serviceMinField;
    private JPanel ArrivalTimePanel;
    private JPanel ServiceTimePanel;

    public InputInterface(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Queue Input Box");
        setSize(300, 400);
        setContentPane(MainPanel);
        setVisible(true);
        pack();
        ConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmButton.setEnabled(false);
                try {
                    int timer = Integer.parseInt(simTimeField.getText());
                    int nrQueues = Integer.parseInt(nrQueuesField.getText());
                    int nrClients = Integer.parseInt(nrClientsField.getText());

                    if (timer < 0 || nrQueues < 0 || nrClients < 0)
                        throw new IllegalArgumentException("Incorrect format!");

                    int minArrivalTime = Integer.parseInt(arrivalMinField.getText());
                    int maxArrivalTime = Integer.parseInt(arrivalMaxField.getText());
                    if (minArrivalTime > maxArrivalTime || minArrivalTime < 0 || maxArrivalTime < 0)
                        throw new IllegalArgumentException("Incorrect format!");

                    int minServiceTime = Integer.parseInt(serviceMinField.getText());
                    int maxServiceTime = Integer.parseInt(serviceMaxField.getText());
                    if (minServiceTime > maxServiceTime || minArrivalTime < 0 || maxServiceTime < 0)
                        throw new IllegalArgumentException("Incorrect format!");

                    String strategyName = (String) comboStrategy.getSelectedItem();
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            SimulationManager simulationManager = new SimulationManager(nrClients, nrQueues, new int[]{minArrivalTime, maxArrivalTime},
                                    new int[]{minServiceTime, maxServiceTime}, strategyName, timer);
                            simulationManager.start();
                            dispose();
                            return null;
                        }

                        @Override
                        protected void done() {
                            ConfirmButton.setEnabled(true);
                        }
                    };

                    worker.execute();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Alert!", JOptionPane.INFORMATION_MESSAGE);
                    ConfirmButton.setEnabled(true);
                }
            }
        });
    }
}
