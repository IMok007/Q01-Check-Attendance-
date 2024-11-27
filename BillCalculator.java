import javax.swing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BillCalculator {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bill Calculator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JTextField currentMeterField = createField(frame, "Current Meter:", 20, 20);
        JTextField lastMeterField = createField(frame, "Last Meter:", 20, 60);
        JComboBox<String> billTypeCombo = createComboBox(frame, "Bill Type:", new String[]{"WaterBill", "ElectBill"}, 20, 100);
        JComboBox<String> roomTypeCombo = createComboBox(frame, "Room Type:", new String[]{"None", "Single Bed", "Double Bed"}, 20, 140);

        JLabel resultLabel = createLabel(frame, "Result:", 20, 220);
        JProgressBar progressBar = createProgressBar(frame, 20, 250);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(50, 180, 100, 25);
        calculateButton.addActionListener(e -> calculateBill(
                frame, currentMeterField, lastMeterField, billTypeCombo, roomTypeCombo, resultLabel, progressBar));
        frame.add(calculateButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(200, 180, 100, 25);
        resetButton.addActionListener(e -> resetFields(currentMeterField, lastMeterField, billTypeCombo, roomTypeCombo, resultLabel, progressBar));
        frame.add(resetButton);

        frame.setVisible(true);
    }

    private static JTextField createField(JFrame frame, String label, int x, int y) {
        frame.add(createLabel(frame, label, x, y));
        JTextField field = new JTextField();
        field.setBounds(150, y, 200, 25);
        frame.add(field);
        return field;
    }

    private static JComboBox<String> createComboBox(JFrame frame, String label, String[] options, int x, int y) {
        frame.add(createLabel(frame, label, x, y));
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setBounds(150, y, 200, 25);
        frame.add(combo);
        return combo;
    }

    private static JLabel createLabel(JFrame frame, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 25);
        frame.add(label);
        return label;
    }

    private static JProgressBar createProgressBar(JFrame frame, int x, int y) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(x, y, 350, 20);
        frame.add(progressBar);
        return progressBar;
    }

    private static void calculateBill(JFrame frame, JTextField currentMeter, JTextField lastMeter,
                                      JComboBox<String> billType, JComboBox<String> roomType, JLabel resultLabel, JProgressBar progressBar) {
        try {
            double current = Double.parseDouble(currentMeter.getText());
            double last = Double.parseDouble(lastMeter.getText());
            if (current <= last) {
                showMessage(frame, "Current Meter must be greater than Last Meter");
                return;
            }

            double units = current - last;
            double result = units * ("WaterBill".equals(billType.getSelectedItem()) ? 5 : 6);
            progressBar.setValue(50);

            if ("Single Bed".equals(roomType.getSelectedItem())) result += 1500;
            else if ("Double Bed".equals(roomType.getSelectedItem())) result += 2000;

            resultLabel.setText(String.format("Result: %.2f Baht", result));
            progressBar.setValue(100);
        } catch (NumberFormatException ex) {
            showMessage(frame, "Please enter valid numbers");
        }
    }

    private static void resetFields(JTextField currentMeter, JTextField lastMeter, JComboBox<String> billType,
                                     JComboBox<String> roomType, JLabel resultLabel, JProgressBar progressBar) {
        currentMeter.setText("");
        lastMeter.setText("");
        billType.setSelectedIndex(0);
        roomType.setSelectedIndex(0);
        resultLabel.setText("Result:");
        progressBar.setValue(0);
    }

    private static void showMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    // Add test methods below for unit testing

    public static double calculateBill(double currentMeter, double lastMeter, String billType, String roomType) {
        if (currentMeter <= lastMeter) {
            return -1; // Invalid case
        }

        double units = currentMeter - lastMeter;
        double result = units * ("WaterBill".equals(billType) ? 5 : 6);

        if ("Single Bed".equals(roomType)) result += 1500;
        else if ("Double Bed".equals(roomType)) result += 2000;

        return result;
    }

    // JUnit test cases

    @Test
    void testCalculateWaterBill() {
        double currentMeter = 100;
        double lastMeter = 50;
        String billType = "WaterBill";
        String roomType = "None";

        double expectedBill = (currentMeter - lastMeter) * 5; // 50 * 5 = 250 Baht
        double actualBill = BillCalculator.calculateBill(currentMeter, lastMeter, billType, roomType);
        
        assertEquals(expectedBill, actualBill, "Water bill calculation failed.");
    }

    @Test
    void testCalculateElectricBill() {
        double currentMeter = 100;
        double lastMeter = 50;
        String billType = "ElectBill";
        String roomType = "None";

        double expectedBill = (currentMeter - lastMeter) * 6; // 50 * 6 = 300 Baht
        double actualBill = BillCalculator.calculateBill(currentMeter, lastMeter, billType, roomType);
        
        assertEquals(expectedBill, actualBill, "Electricity bill calculation failed.");
    }

    @Test
    void testCalculateWaterBillSingleBed() {
        double currentMeter = 100;
        double lastMeter = 50;
        String billType = "WaterBill";
        String roomType = "Single Bed";

        double expectedBill = (currentMeter - lastMeter) * 5 + 1500; // 50 * 5 + 1500 = 2000 Baht
        double actualBill = BillCalculator.calculateBill(currentMeter, lastMeter, billType, roomType);
        
        assertEquals(expectedBill, actualBill, "Water bill with single bed room calculation failed.");
    }

    @Test
    void testCalculateWaterBillDoubleBed() {
        double currentMeter = 100;
        double lastMeter = 50;
        String billType = "WaterBill";
        String roomType = "Double Bed";

        double expectedBill = (currentMeter - lastMeter) * 5 + 2000; // 50 * 5 + 2000 = 2500 Baht
        double actualBill = BillCalculator.calculateBill(currentMeter, lastMeter, billType, roomType);
        
        assertEquals(expectedBill, actualBill, "Water bill with double bed room calculation failed.");
    }

    @Test
    void testInvalidCurrentMeter() {
        double currentMeter = 50;
        double lastMeter = 100;
        String billType = "WaterBill";
        String roomType = "None";

        double actualBill = BillCalculator.calculateBill(currentMeter, lastMeter, billType, roomType);
        
        assertEquals(-1, actualBill, "Error message for invalid current meter is incorrect.");
    }
}
