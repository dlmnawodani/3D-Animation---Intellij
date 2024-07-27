package furShop;

import javax.swing.*;
import java.awt.*;

public class DesignContentPanel extends JPanel {

    public DesignContentPanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
        JLabel label = new JLabel("Hello World");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }

}
