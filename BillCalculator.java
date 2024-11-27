import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillCalculator {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bill Calculator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel currentMeterLabel = new JLabel("Current Meter:");
        currentMeterLabel.setBounds(20, 20, 120, 25);
        JTextField currentMeterField = new JTextField();
        currentMeterField.setBounds(150, 20, 200, 25);

        JLabel lastMeterLabel = new JLabel("Last Meter:");
        lastMeterLabel.setBounds(20, 60, 120, 25);
        JTextField lastMeterField = new JTextField();
        lastMeterField.setBounds(150, 60, 200, 25);

        JLabel billTypeLabel = new JLabel("Bill Type:");
        billTypeLabel.setBounds(20, 100, 120, 25);
        String[] billTypes = {"WaterBill", "ElectBill"};
        JComboBox<String> billTypeCombo = new JComboBox<>(billTypes);
        billTypeCombo.setBounds(150, 100, 200, 25);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(20, 140, 120, 25);
        String[] roomTypes = {"None", "Single Bed", "Double Bed"};
        JComboBox<String> roomTypeCombo = new JComboBox<>(roomTypes);
        roomTypeCombo.setBounds(150, 140, 200, 25);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(50, 180, 100, 25);
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(200, 180, 100, 25);

        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setBounds(20, 220, 350, 25);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(20, 250, 350, 20);

        frame.add(currentMeterLabel);
        frame.add(currentMeterField);
        frame.add(lastMeterLabel);
        frame.add(lastMeterField);
        frame.add(billTypeLabel);
        frame.add(billTypeCombo);
        frame.add(roomTypeLabel);
        frame.add(roomTypeCombo);
        frame.add(calculateButton);
        frame.add(resetButton);
        frame.add(resultLabel);
        frame.add(progressBar);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double currentMeter = Double.parseDouble(currentMeterField.getText());
                    double lastMeter = Double.parseDouble(lastMeterField.getText());
                    String billType = (String) billTypeCombo.getSelectedItem();
                    String roomType = (String) roomTypeCombo.getSelectedItem();

                    if (currentMeter <= lastMeter) {
                        JOptionPane.showMessageDialog(frame, "Current Meter must be greater than Last Meter", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double units = currentMeter - lastMeter;
                    double billResult = 0;

                    if ("WaterBill".equals(billType)) {
                        billResult = units * 5;
                    } else if ("ElectBill".equals(billType)) {
                        billResult = units * 6;
                    }

                    progressBar.setValue(50); 
                    resultLabel.setText(String.format("Result: %.2f Baht", billResult));

                    if ("Single Bed".equals(roomType)) {
                        billResult += 1500;
                        resultLabel.setText(String.format("Result: %.2f Baht (including room)", billResult));
                        progressBar.setValue(100);
                    } else if ("Double Bed".equals(roomType)) {
                        billResult += 2000;
                        resultLabel.setText(String.format("Result: %.2f Baht (including room)", billResult));
                        progressBar.setValue(100);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMeterField.setText("");
                lastMeterField.setText("");
                billTypeCombo.setSelectedIndex(0);
                roomTypeCombo.setSelectedIndex(0);
                resultLabel.setText("Result:");
                progressBar.setValue(0);
            }
        });

        frame.setVisible(true);
    }
}
