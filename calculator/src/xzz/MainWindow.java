package xzz;

import javax.swing.*;
import java.awt.*;

class MainWindow extends JFrame {
    JLabel label1 = new JLabel("Input:");
    JTextField expressionField = new JTextField();
    JButton calculateButton = new JButton("Calculate");
    JLabel label2 = new JLabel("compileResult:");
    JTextField compileResult = new JTextField();
    JLabel label3 = new JLabel("calculateResult:");
    JTextField calculateResult = new JTextField();

    //注册事件
    private void addEvent() {
        calculateButton.addActionListener(
            (_e)-> {
                String text = expressionField.getText();
                if (text.length() == 0) {
                    compileResult.setText("please input expression");
                    return;
                }
                //System.out.println(expressionField.getText());
                Result result = (new Handler(text)).getResult();
                compileResult.setText(result.compileResult);
                calculateResult.setText(result.calculateResult);
            }
        );
    }

    public MainWindow() {

        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //设置格式
        Font font = expressionField.getFont();
        Font newFont = font.deriveFont(30f);
        expressionField.setFont(newFont);
        expressionField.setColumns(40);


        font = compileResult.getFont();
        newFont = font.deriveFont(20f);
        compileResult.setFont(newFont);
        font = calculateResult.getFont();
        newFont = font.deriveFont(30f);
        calculateResult.setFont(newFont);


        // 布局管理添加控件
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(30, 10, 30, 10);

        //First row
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        add(label1, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 49;
        constraints.fill = GridBagConstraints.BOTH;
        add(expressionField, constraints);

        constraints.gridx = 50;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        add(calculateButton, constraints);

        // Second row
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.NONE;
        add(label2, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 0;
        constraints.fill = GridBagConstraints.BOTH;
        add(compileResult, constraints);

        // Third row
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        add(label3, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 0;
        constraints.fill = GridBagConstraints.BOTH;
        add(calculateResult, constraints);

        addEvent();
        compileResult.setEditable(false);
        calculateResult.setEditable(false);

        pack(); // Adjust the window size based on the components
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }
}
