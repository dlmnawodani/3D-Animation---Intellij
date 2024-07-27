package furShop;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private File userDataFile = new File("userdata.txt");

    public LoginPage() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/login.jpg"));
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setLayout(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Cozy Comfort Furniture");
        titleLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 74));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        imagePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel, BorderLayout.WEST);

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginHeading = new JLabel("Login");
        loginHeading.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(loginHeading, gbc);

        gbc.gridy++;

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel, gbc);

        gbc.gridx++;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridy++;

        gbc.gridx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);

        gbc.gridx++;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        gbc.gridy++;

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateLogin();
            }
        });
        loginButton.setBackground(new Color(48, 17, 5));
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton, gbc);

        gbc.gridy++;

        JLabel registerLabel = new JLabel("<html><body>Don't have an account? <u>Join here</u></body></html>");
        registerLabel.setForeground(Color.BLUE.darker());
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navigateToRegisterPage();
            }
        });
        panel.add(registerLabel, gbc);

        return panel;
    }

    private void validateLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(userDataFile));
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username) && parts[2].equals(password)) {
                    found = true;
                    break;
                }
            }
            reader.close();
            if (found) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred during login!");
        }
    }

    private void navigateToRegisterPage() {
        dispose();
        new RegisterPage();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
