import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimpleDrawingApp extends JFrame {
    private DrawingPanel drawingPanel;
    private JButton colorButton, clearButton;
    private Color currentColor = Color.BLACK;

    public SimpleDrawingApp() {
        setTitle("Simple Drawing App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Drawing panel
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        colorButton = new JButton("Choose Color");
        clearButton = new JButton("Clear");

        colorButton.addActionListener(e -> {
            Color selected = JColorChooser.showDialog(this, "Pick a Color", currentColor);
            if (selected != null) {
                currentColor = selected;
                drawingPanel.setCurrentColor(currentColor);
            }
        });

        clearButton.addActionListener(e -> drawingPanel.clear());

        buttonPanel.add(colorButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Custom panel for drawing
    class DrawingPanel extends JPanel {
        private ArrayList<Line> lines = new ArrayList<>();
        private Point startPoint;

        public DrawingPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    Point endPoint = e.getPoint();
                    lines.add(new Line(startPoint, endPoint, currentColor));
                    startPoint = endPoint;
                    repaint();
                }
            });
        }

        public void setCurrentColor(Color color) {
            currentColor = color;
        }

        public void clear() {
            lines.clear();
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Line line : lines) {
                g.setColor(line.color);
                g.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
            }
        }
    }

    // Line class to store points and color
    class Line {
        Point start, end;
        Color color;

        Line(Point s, Point e, Color c) {
            start = s;
            end = e;
            color = c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleDrawingApp::new);
    }
}
