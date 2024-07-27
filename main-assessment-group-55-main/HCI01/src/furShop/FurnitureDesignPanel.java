package furShop;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class FurnitureDesignPanel extends JFrame {
    private ArrayList<CustomShape> shapes;
    private CustomShape currentShape;
    private Color currentColor;
    private JComboBox<String> shapeComboBox;
    private JButton colorButton;
    private JButton addButton;
    private JButton view3DButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton rotateButton;
    private JLabel widthLabel;
    private JTextField widthField;
    private JLabel heightLabel;
    private JTextField heightField;
    private JPanel drawingPanel;
    private Point mouseLocation;

    public FurnitureDesignPanel() {
        setTitle("Furniture Design Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shapes = new ArrayList<>();
        currentColor = Color.BLACK;

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                for (CustomShape shape : shapes) {
                    g2d.setColor(shape.getColor());
                    g2d.fill(shape.getShape());
                }
                if (currentShape != null) {
                    g2d.setColor(Color.RED);
                    g2d.draw(currentShape.getShape());
                }
            }
        };
        drawingPanel.setPreferredSize(new Dimension(800, 500));
        drawingPanel.setLayout(null);
        drawingPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseLocation = e.getPoint();
                for (CustomShape shape : shapes) {
                    if (shape.getShape().contains(mouseLocation)) {
                        currentShape = shape;
                        break;
                    }
                }
                drawingPanel.repaint();
            }
        });

        shapeComboBox = new JComboBox<>();
        shapeComboBox.addItem("Rectangle");
        shapeComboBox.addItem("Ellipse");
        // Add more shapes as needed

        widthLabel = new JLabel("Width:");
        widthField = new JTextField(5);
        heightLabel = new JLabel("Height:");
        heightField = new JTextField(5);

        colorButton = new JButton("Choose Color");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = JColorChooser.showDialog(FurnitureDesignPanel.this, "Choose Color", currentColor);
            }
        });

        addButton = new JButton("Add Shape");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String shapeType = (String) shapeComboBox.getSelectedItem();
                double width = Double.parseDouble(widthField.getText());
                double height = Double.parseDouble(heightField.getText());

                Shape shape;
                if (shapeType.equals("Rectangle")) {
                    shape = new Rectangle2D.Double(mouseLocation.x, mouseLocation.y, width, height);
                } else { // Assume ellipse for other shapes
                    shape = new Ellipse2D.Double(mouseLocation.x, mouseLocation.y, width, height);
                }

                CustomShape customShape = new CustomShape(shape, currentColor);
                shapes.add(customShape);
                drawingPanel.repaint();
            }
        });

        view3DButton = new JButton("View 3D");
        view3DButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement 3D view functionality
            }
        });

        deleteButton = new JButton("Delete Shape");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentShape != null) {
                    shapes.remove(currentShape);
                    currentShape = null;
                    drawingPanel.repaint();
                }
            }
        });

        editButton = new JButton("Edit Shape");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentShape != null) {
                    double width = Double.parseDouble(widthField.getText());
                    double height = Double.parseDouble(heightField.getText());
                    currentShape.setShape(createShape(width, height));
                    currentShape.setColor(currentColor);
                    drawingPanel.repaint();
                }
            }
        });

        rotateButton = new JButton("Rotate Shape");
        rotateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentShape != null) {
                    double angle = 45; // Rotate by 45 degrees
                    AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), currentShape.getShape().getBounds().getCenterX(), currentShape.getShape().getBounds().getCenterY());
                    currentShape.setShape(transform.createTransformedShape(currentShape.getShape()));
                    drawingPanel.repaint();
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(drawingPanel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(shapeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(widthLabel)
                        .addComponent(widthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(heightLabel)
                        .addComponent(heightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(colorButton)
                        .addComponent(addButton)
                        .addComponent(view3DButton)
                        .addComponent(deleteButton)
                        .addComponent(editButton)
                        .addComponent(rotateButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(drawingPanel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(shapeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(widthLabel)
                        .addComponent(widthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(heightLabel)
                        .addComponent(heightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(colorButton)
                        .addComponent(addButton)
                        .addComponent(view3DButton)
                        .addComponent(deleteButton)
                        .addComponent(editButton)
                        .addComponent(rotateButton))
        );

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FurnitureDesignPanel();
            }
        });
    }

    private Shape createShape(double width, double height) {
        String shapeType = (String) shapeComboBox.getSelectedItem();
        if (shapeType.equals("Rectangle")) {
            return new Rectangle2D.Double(0, 0, width, height);
        } else { // Assume ellipse for other shapes
            return new Ellipse2D.Double(0, 0, width, height);
        }
    }

    private class CustomShape {
        private Shape shape;
        private Color color;

        public CustomShape(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public Shape getShape() {
            return shape;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }
    }
    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Your custom drawing code here
            g2d.setColor(Color.BLUE);
            g2d.fillRect(100, 100, 200, 200);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }
    }
}
