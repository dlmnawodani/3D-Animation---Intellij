package furShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JPanel navigationBar;
    private JPanel contentPanel;
    private JButton[] navButtons;

    public Dashboard() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create navigation bar
        navigationBar = createNavigationBar();
        add(navigationBar, BorderLayout.NORTH);

        // Create content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Default content: Home
        showHomeContent();

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createNavigationBar() {
        JPanel navBar = new JPanel(new GridLayout(1, 5)); // 5 tabs
        navBar.setBackground(new Color(100, 100, 100)); // Dark gray background

        navButtons = new JButton[5]; // Array to hold the navigation buttons

        String[] buttonNames = {"Home", "Design", "Create Design", "Orders", "Settings"};

        for (int i = 0; i < 5; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setFocusPainted(false);
            button.setBackground(new Color(100, 100, 100)); // Dark gray background
            button.setForeground(Color.WHITE); // White text
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleNavButtonClick(button);
                }
            });
            navBar.add(button);
            navButtons[i] = button;
        }

        return navBar;
    }

    public void showAccountPanel() {
        contentPanel.removeAll();
        AccountPanel accountPanel = new AccountPanel();
        contentPanel.add(accountPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void handleNavButtonClick(JButton clickedButton) {
        for (JButton button : navButtons) {
            button.setBackground(new Color(100, 100, 100)); // Reset background color of all buttons
        }
        clickedButton.setBackground(Color.GRAY); // Highlight the clicked button

        switch (clickedButton.getText()) {
            case "Home":
                showHomeContent();
                break;
            case "Design":
                showDesignContent();
                break;
            case "Create Design":
                showCreateDesignContent();
                break;
            case "Orders":
                showOrdersContent();
                break;
            case "Settings":
                showSettingsPage();
                break;
            default:
                // Do nothing
        }
    }

    private void showHomeContent() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Home Content");
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showDesignContent() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Design Content");
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCreateDesignContent() {
        contentPanel.removeAll();
        ChairStyleTiles chairStyleTiles = new ChairStyleTiles();
        contentPanel.add(chairStyleTiles, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showOrdersContent() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Orders Content");
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showSettingsPage() {
        contentPanel.removeAll();
        SettingsPage settingsPage = new SettingsPage(this); // Pass Dashboard reference to SettingsPage
        contentPanel.add(settingsPage, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}
