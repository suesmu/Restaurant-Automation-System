package gui;

import backend.OrderManager;
import backend.ReportGenerator;

import javax.swing.*;
import java.awt.*;

public class ReportScreen extends JFrame {
    public ReportScreen(OrderManager orderManager) {
        setTitle("📊 Daily Report");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ReportGenerator generator = new ReportGenerator();
        String report = generator.generateReport(orderManager.getOrders());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 248, 248));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Today’s Orders", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JTextArea reportArea = new JTextArea(report);
        reportArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        reportArea.setFocusable(false);
        reportArea.setBackground(Color.WHITE);
        reportArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(reportArea, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}