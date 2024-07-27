package furShop;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RegisterPage extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private File userDataFile = new File("userdata.txt");

    public RegisterPage() {
        setTitle("Register Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main panel to hold all components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setBackground(Color.WHITE);

        // Center panel to hold left and right panels
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setPreferredSize(new Dimension(700, 600));

        // Left Panel with Shaded Background
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Define the gradient colors
                Color color1 = new Color(0, 0, 0);
                Color color2 = new Color(166, 165, 164);

                // Create a vertical gradient
                GradientPaint gradient = new GradientPaint(
                        0, 0, color1,
                        0, getHeight(), color2
                );

                // Apply the gradient
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(200, 200));

        // Add label with white color text over the background
        JLabel leftLabel = new JLabel("Cozy Comfort Furniture");
        leftLabel.setFont(new Font("Monotype Corsiva", Font.BOLD, 30));
        leftLabel.setForeground(Color.WHITE);
        leftPanel.add(leftLabel, new GridBagConstraints());

        // Right Panel (Register Form)
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setForeground(new Color(0, 0, 0)); // Deep Sky Blue color
        registerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 20)); // Adjusted width of input field
        usernameField.setToolTipText("Username");
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 20)); // Adjusted width of input field
        emailField.setToolTipText("Email");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20)); // Adjusted width of input field
        passwordField.setToolTipText("Password");
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 0, 0)); // Deep Sky Blue color
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(100, 30));
        GridBagConstraints gbcInsets = new GridBagConstraints();
        gbcInsets.insets = new Insets(5, 0, 5, 0);
        registerPanel.add(registerLabel, gbcInsets);
        gbcInsets.gridy = 1;
        registerPanel.add(new JLabel("Username:"), gbcInsets);
        gbcInsets.gridy = 2;
        registerPanel.add(usernameField, gbcInsets);
        gbcInsets.gridy = 3;
        registerPanel.add(new JLabel("Email:"), gbcInsets);
        gbcInsets.gridy = 4;
        registerPanel.add(emailField, gbcInsets);
        gbcInsets.gridy = 5;
        registerPanel.add(new JLabel("Password:"), gbcInsets);
        gbcInsets.gridy = 6;
        registerPanel.add(passwordField, gbcInsets);
        gbcInsets.gridy = 7;
        gbcInsets.insets = new Insets(10, 0, 0, 0);
        registerPanel.add(registerButton, gbcInsets);

        rightPanel.add(registerPanel, BorderLayout.CENTER);

        // Add left and right panels to center panel
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        // Add center panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(centerPanel, gbc);

        // Add main panel to the frame
        add(mainPanel);

        // Add action listener to register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform registration logic
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(username, email, password);
                    JOptionPane.showMessageDialog(null, "Registration successful! Please login.");
                    dispose(); // Close the registration form
                    new LoginPage(); // Open the login page
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                }
            }
        });

        pack(); // Pack components
        setLocationRelativeTo(null); // Center the window
        setVisible(true); // Show the window
    }

    private void registerUser(String username, String email, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userDataFile, true))) {
            writer.write(username + "," + email + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterPage::new);
    }
}
