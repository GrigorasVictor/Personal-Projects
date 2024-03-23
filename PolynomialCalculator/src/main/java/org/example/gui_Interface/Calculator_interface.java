package org.example.gui_Interface;

import org.example.businessLogic.Operations;
import org.example.dataModels.Polynomial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator_interface extends JFrame {
    private JPanel MainPanel;
    private JTextField inputField1;
    private JTextField inputField2;
    private JButton callButton;
    private JList Historic;
    private JLabel Title;
    private JLabel title1;
    private JLabel title2;
    private JButton buttonPlus;
    private JButton buttonMinus;
    private JButton buttonMul;
    private JButton buttonDivide;
    private JButton dDxButton;
    private JButton buttonIntegral;
    private JTextField OperationType;
    private JTextField Result1;
    private JTextField Result2;
    private int operationOrder;
    private int index= 1;
    private static final int MAX_CAPACITY = 10;
    ImageIcon logo = new ImageIcon("src/main/resources/calculator.png");

    public Calculator_interface() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Polynomial calculator");
        setSize(600, 400);
        setContentPane(MainPanel);
        setVisible(true);
        setIconImage(logo.getImage());

        DefaultListModel<String> listHistoric = new DefaultListModel<>();

        OperationType.setBorder(null);
        Font font = new Font("Arial", Font.BOLD, 30);
        OperationType.setFont(font);
        OperationType.setHorizontalAlignment(JTextField.CENTER);

        updateOperationText();
        buttonPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationOrder = 0;
                updateOperationText();
            }
        });
        buttonMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationOrder = 1;
                updateOperationText();
            }
        });
        buttonMul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationOrder = 2;
                updateOperationText();
            }
        });
        buttonDivide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationOrder = 3;
                updateOperationText();
            }
        });
        dDxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationOrder = 4;
                updateOperationText();
            }
        });
        buttonIntegral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationOrder = 5;
                updateOperationText();
            }
        });
        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynomial p1 = new Polynomial();
                Polynomial p2 = new Polynomial();

                String answer1 = "";
                String answer2 = "";

                String input1 = inputField1.getText();
                String input2 = inputField2.getText();

                Pattern p = Pattern.compile("(\\+?-?\\d*)(X?\\^?)?(\\d*)");
                Matcher m = p.matcher(input1);
                extractInput(m, p1);
                m=p.matcher(input2);
                extractInput(m, p2);

                try{
                    switch (operationOrder) {
                        case 0:
                            answer1 = Operations.addition(p1, p2).getFormat();
                            break;
                        case 1:
                            answer1 = Operations.subtraction(p1, p2).getFormat();
                            break;
                        case 2:
                            answer1 = Operations.multiplication(p1, p2).getFormat();
                            break;
                        case 3:
                            answer1 = Operations.divide(p1, p2)[0].getFormat();
                            answer2 = Operations.divide(p1, p2)[1].getFormat();
                            break;
                        case 4:
                            answer1 = Operations.derivate(p1).getFormat();
                            break;
                        case 5:
                            answer1 = Operations.integration(p1).getFormat() + " +C";
                            break;
                    }
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Exception Alert!", JOptionPane.INFORMATION_MESSAGE);
                }
                Result1.setText(answer1);
                Result2.setText(answer2);
                updatePanel(listHistoric, p1, p2, answer1, answer2);
            }
        });
    }
    private void updateOperationText() {
        switch (operationOrder) {
            case 0:
                OperationType.setText("+");
                break;
            case 1:
                OperationType.setText("-");
                break;
            case 2:
                OperationType.setText("*");
                break;
            case 3:
                OperationType.setText("/");
                break;
            case 4:
                OperationType.setText("d/dx");
                break;
            case 5:
                OperationType.setText("∫");
                break;
        }
    }
    private void updatePanel(DefaultListModel<String> listHistoric, Polynomial p1, Polynomial p2, String answer1, String answer2){
        //OverWrite when it hit maximum capacity
        if (listHistoric.size() >= MAX_CAPACITY)
            listHistoric.remove(0);

        switch (operationOrder) {
            case 0:
                listHistoric.addElement(index + ": " + p1.getFormat() + " + " + p2.getFormat() + " = " + answer1);
                break;
            case 1:
                listHistoric.addElement(index + ": " + p1.getFormat() + " - " + p2.getFormat() + " = " + answer1);
                break;
            case 2:
                listHistoric.addElement(index + ": " + p1.getFormat() + " * " + p2.getFormat() + " = " + answer1);
                break;
            case 3:
                listHistoric.addElement(index + ": " + p1.getFormat() + " / " + p2.getFormat() + " = " + answer1 + " with R: " +answer2);
                break;
            case 4:
                listHistoric.addElement(index + ": " + p1.getFormat() + "d/dx = " + answer1);
                break;
            case 5:
                listHistoric.addElement(index + ": ∫" + p1.getFormat() + "dx = " + answer1);
                break;
        }
        index++;
        Historic.setModel(listHistoric);
    }

    public void extractInput(Matcher matcher, Polynomial pol){
        try{
            while (matcher.find()) {
                int coefficinet = 0, pow = 0;
                if (!matcher.group(1).isEmpty()) {
                    coefficinet = switch (matcher.group(1)) {
                        case "-" -> -1;
                        case "+" -> 1;
                        default -> Integer.parseInt(matcher.group(1));
                    };
                    //in the case of the first element in the pole that has power, and has no coefficient
                } else if (!matcher.group(2).isEmpty()){
                    coefficinet =1;
                }
                if (!matcher.group(3).isEmpty()) {
                    pow = Integer.parseInt(matcher.group(3));
                    //the power is not registered if I have the character "X"
                } else if (!matcher.group(2).isEmpty() && matcher.group(2).equals("X"))
                    pow = 1;

                if (coefficinet != 0)
                    pol.addElement(pow, coefficinet);
            }
        }catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Incorrect format!", "Exception Alert!", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
