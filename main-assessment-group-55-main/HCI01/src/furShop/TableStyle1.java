package furShop;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableStyle1 extends JPanel implements GLEventListener {
    private GLU glu;
    private float angleX = 0;
    private float angleY = 0;
    private float scaleFactor = 1.0f;
    private Color[] partColors = new Color[6];
    private Color roomColor = Color.WHITE;
    private GLJPanel panel;
    private boolean rotate = true; // Flag to control rotation

    public TableStyle1() {
        setPreferredSize(new Dimension(1000, 600)); // Adjust panel size

        for (int i = 0; i < partColors.length; i++) {
            partColors[i] = Color.GRAY;
        }

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        panel = new GLJPanel(capabilities);
        panel.addGLEventListener(this);
        panel.setBackground(roomColor);

        FPSAnimator animator = new FPSAnimator(panel, 60);
        animator.start();

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        glu = new GLU();

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton rotateButton = new JButton("View 2D"); // Change label
        rotateButton.setPreferredSize(new Dimension(150, 40)); // Adjust button size
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotate = !rotate; // Toggle rotation flag
                rotateButton.setText(rotate ? "View 2D" : "View 3D"); // Update button text
            }
        });
        bottomPanel.add(rotateButton, BorderLayout.WEST);

        // Create a dropdown menu for selecting colors for each part
        JComboBox<String> partDropdown = new JComboBox<>(new String[]{"Table Top Color", "Legs Color 1", "Legs Color 2", "Legs Color 3"});
        partDropdown.setPreferredSize(new Dimension(150, 30)); // Adjust dropdown menu size
        partDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                int selectedIndex = comboBox.getSelectedIndex();
                Color selectedColor = JColorChooser.showDialog(null, "Choose Color", partColors[selectedIndex]);
                if (selectedColor != null) {
                    partColors[selectedIndex] = selectedColor;
                    panel.repaint(); // Repaint the GLJPanel to reflect color changes
                }
            }
        });
        bottomPanel.add(partDropdown, BorderLayout.CENTER);

        JSlider scaleSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        scaleSlider.setPreferredSize(new Dimension(300, 40)); // Adjust slider size
        scaleSlider.setMajorTickSpacing(10);
        scaleSlider.setPaintTicks(true);
        scaleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    scaleFactor = value / 100f;
                }
            }
        });
        JPanel adjustSizePanel = new JPanel();
        JLabel adjustSizeLabel = new JLabel("Adjust size: ");
        adjustSizePanel.add(adjustSizeLabel);
        adjustSizePanel.add(scaleSlider);
        bottomPanel.add(adjustSizePanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(roomColor.getRed() / 255f, roomColor.getGreen() / 255f,
                roomColor.getBlue() / 255f, 1.0f); // Set background color
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        if (rotate) { // Check if rotation is enabled
            gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
            angleX += 0.5f;
            angleY += 0.5f;
        }
        gl.glScalef(scaleFactor, scaleFactor, scaleFactor);
        drawTable(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double aspectRatio = (double) width / (double) height;
        glu.gluPerspective(45, aspectRatio, 1, 100);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Cleanup resources...
    }

    private void drawTable(GL2 gl) {
        gl.glColor3fv(partColors[0].getRGBComponents(null), 0);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 1.0f, 0.0f);
        gl.glScalef(2.0f, 0.1f, 1.5f);
        drawCube(gl);
        gl.glPopMatrix();

        for (int i = 1; i < partColors.length; i++) {
            gl.glColor3fv(partColors[i].getRGBComponents(null), 0);
            gl.glPushMatrix();
            float legX = (i % 2 == 0) ? -0.75f : 0.75f;
            float legZ = (i < 3) ? -0.75f : 0.75f;
            gl.glTranslatef(legX, 0.0f, legZ);
            gl.glScalef(0.2f, 1.0f, 0.2f);
            drawCube(gl);
            gl.glPopMatrix();
        }
    }

    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);

        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);

        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Table Style 1");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new TableStyle1(), BorderLayout.CENTER);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

}
