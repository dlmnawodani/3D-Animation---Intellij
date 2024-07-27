package furShop;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;

public class TableStyle3 extends JPanel implements GLEventListener {
    private float angleX = 0;
    private float angleY = 0;
    private float scaleFactor = 1.0f;
    private Color[] partColors = new Color[6];

    public TableStyle3() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        for (int i = 0; i < partColors.length; i++) {
            partColors[i] = Color.GRAY;
        }

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLJPanel panel = new GLJPanel(capabilities);
        panel.addGLEventListener(this);

        FPSAnimator animator = new FPSAnimator(panel, 60);
        animator.start();

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
        gl.glScalef(scaleFactor, scaleFactor, scaleFactor);

        drawStudyTable(gl);
        angleX += 0.5f;
        angleY += 0.5f;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double aspectRatio = (double) width / (double) height;
        gl.glFrustum(-1.0, 1.0, -1.0, 1.0, 1.5, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Cleanup resources...
    }

    private void drawStudyTable(GL2 gl) {
        // Draw the tabletop
        gl.glColor3fv(partColors[0].getRGBComponents(null), 0);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -1.0f, 0.0f);
        gl.glScalef(1.5f, 0.05f, 1.0f);
        drawCube(gl);
        gl.glPopMatrix();

        // Draw the legs
        gl.glColor3fv(partColors[1].getRGBComponents(null), 0);
        drawLeg(gl, -0.6f, -1.0f, -0.4f);
        drawLeg(gl, 0.6f, -1.0f, -0.4f);
        drawLeg(gl, -0.6f, -1.0f, 0.4f);
        drawLeg(gl, 0.6f, -1.0f, 0.4f);

        // Draw the locker
        gl.glColor3fv(Color.BLUE.getRGBComponents(null), 0);
        gl.glPushMatrix();
        gl.glTranslatef(0.4f, -0.9f, 0.0f);
        gl.glScalef(0.3f, 0.5f, 0.3f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawLeg(GL2 gl, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glScalef(0.05f, 1.0f, 0.05f);
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);

        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);
        gl.glVertex3f(0.5f, 0.5f, -0.5f);
        gl.glVertex3f(0.5f, -0.5f, -0.5f);

        gl.glVertex3f(-0.5f, 0.5f, -0.5f);
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0.5f, 0.5f, -0.5f);

        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);

        gl.glVertex3f(0.5f, -0.5f, -0.5f);
        gl.glVertex3f(0.5f, 0.5f, -0.5f);
        gl.glVertex3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0.5f, -0.5f, 0.5f);

        gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glVertex3f(-0.5f, 0.5f, -0.5f);
        gl.glEnd();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TableStyle3::new);
    }
}
