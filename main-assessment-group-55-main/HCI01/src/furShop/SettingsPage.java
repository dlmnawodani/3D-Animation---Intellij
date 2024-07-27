package furShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPage extends JPanel {
    private Dashboard dashboard; // Reference to the Dashboard

    public SettingsPage(Dashboard dashboard) {
        this.dashboard = dashboard;
        setLayout(new BorderLayout());

        // Create the settings panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(5, 1));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add the settings options
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        settingsPanel.add(titleLabel);

        JButton accountButton = new JButton("Account");
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard.showAccountPanel(); // Call the method in Dashboard to switch to AccountPanel
            }
        });
        settingsPanel.add(accountButton);

        JButton privacyPolicyButton = new JButton("Privacy Policy");
        settingsPanel.add(privacyPolicyButton);

        JButton termsButton = new JButton("Terms and Conditions");
        settingsPanel.add(termsButton);

        JButton aboutUsButton = new JButton("About Us");
        settingsPanel.add(aboutUsButton);

        // Add the settings panel to the center of this panel
        add(settingsPanel, BorderLayout.CENTER);

        // Add a logout button at the bottom
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setPreferredSize(new Dimension(150, 40));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logoutButton, BorderLayout.SOUTH);
    }
}

