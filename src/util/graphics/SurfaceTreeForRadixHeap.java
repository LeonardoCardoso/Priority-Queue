package util.graphics;

import heaps.element.Pair;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SurfaceTreeForRadixHeap extends JPanel {

    private static final long serialVersionUID = -9054075219988539709L;
    private Pair[] nodes;
    private ArrayList<Pair>[] nodesSet;
    private String title = "";
    private int screenWidth, screenHeight;

    public static final int DIMENSION_TOP = 66, DIMENSION_LEFT = 35, DIMENSION_WIDTH = 100, DIMENSION_HEIGHT = 60;

    Node selection = null;

    Point linkEnd = null;
    Node linkTarget = null;

    public SurfaceTreeForRadixHeap(String title, ArrayList<Pair>[] nodesSet) {
        setDimensions();
        this.title = title;
        this.nodesSet = nodesSet;

        setDimensions();
        setLayout(null);

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        setPreferredSize(new Dimension(screenWidth, screenHeight));

        setSelection(null);

        buildData();

        JFrame frame = makeFrame();
        frame.setVisible(true);
    }

    private void setDimensions() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
    }

    public JFrame makeFrame() {

        JFrame f = new JFrame(title);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(screenWidth - DIMENSION_WIDTH, DIMENSION_WIDTH * nodes.length));
        JScrollPane scrollFrame = new JScrollPane(this);
        setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension(screenWidth, screenHeight));

        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollFrame, BorderLayout.CENTER);
        f.pack();

        return f;
    }

    private void buildData() {
        for (int i = 0; i < nodesSet.length; i++) {
            nodes = new Pair[nodesSet[i] != null ? nodesSet[i].size() : 1];
            nodes = nodesSet[i].toArray(nodes);
            Pair root = null;
            if (nodes != null && nodes.length > 0 && nodes[0] != null) {
                root = nodes[0];
            }
            printAll(root, i, 0, null, null, getRectNextLeft(i + 1), 0, 0);
        }
    }

    public class Node extends JLabel {

        private static final long serialVersionUID = -3405121483030773951L;
        Node parent = null;
        private Set<Node> children = new HashSet<Node>();
        public static final String COLOR_LEFT = "red", COLOR_RIGHT = "blue", COLOR_PARENT = "black";

        public Node(String text, Point location, String color, int index) {
            super("<html>"
                    + (color == COLOR_PARENT ? "<div style='border: 1px dashed black; border-bottom: 0px; text-align: center;'>"
                    + index + "<br />(2^" + (index + 1) + ")</div>" : "")
                    + "<div style='width: 35px; height: 35px; color: " + color
                    + "; font-family: Arial; padding-top: 10px; text-align: center; background: white; "
                    + "border: 1px solid black; margin: auto;'>" + text + "<div></html>");
            setOpaque(true);
            setLocation(location);
            setSize(getPreferredSize());
        }

        public void setText(String text) {
            super.setText(text);
            setSize(getPreferredSize());
        }

        public void setParent(Node newParent) {
            if (parent != null) {
                parent.getChildren().remove(this);
            }
            this.parent = newParent;
            if (newParent != null) {
                newParent.getChildren().add(this);
            }
        }

        public boolean hitsLinkHotspot(Point pt) {
            Rectangle r = getBounds();
            int x = pt.x - r.x;
            int y = pt.y - r.y;
            x -= (r.width / 2 - 4);
            return (-2 < x && x < 10 && -2 < y && y < 10);
        }

        public void paintLink(Graphics g) {
            Rectangle myRect = getBounds();
            int x1 = myRect.x + myRect.width / 2;
            int y1 = myRect.y + 4;

            int x2;
            int y2;
            if (parent != null) {
                Rectangle parentRect = parent.getBounds();
                x2 = parentRect.x + parentRect.width / 2;
                y2 = parentRect.y + parentRect.height;
            } else if (selection == this && linkEnd != null) {
                x2 = linkEnd.x;
                y2 = linkEnd.y;
            } else {
                return;
            }

            g.drawLine(x1, y1, x2, y2);
        }

        public Set<Node> getChildren() {
            return children;
        }

        public void setChildren(Set<Node> children) {
            this.children = children;
        }

    } // end of Node class

    private void printAll(Pair root, int arrayIndex, int i, Pair parent, Node parentNode, int parentPosition, int depthLeft, int depthRight) {
        Node p = null;

        if (root != null && parent == null) {
            p = makeNode("&lt;" + root.p + "," + root.v + "&gt;", new Point(parentPosition, getRectNextTop(depthRight)), Node.COLOR_PARENT, arrayIndex);
        } else {
            p = parentNode;
        }

        if (root == null) {
            makeNode("", new Point(parentPosition, getRectNextTop(depthRight)), Node.COLOR_PARENT, arrayIndex);
            return;
        }

        recursivePrint(arrayIndex, i, p, parentPosition, depthLeft + 1, depthRight + 1);
    }

    private void recursivePrint(int arrayIndex, int i, Node parentNode, int parentPosition, int depthLeft, int depthRight) {

        int newLeftPosition = 2 * i + 1;
        Pair left = null;
        Node nodeLeft = null;
        int parentLeftPosition = parentPosition;
        if (newLeftPosition < nodes.length) {
            left = nodes[newLeftPosition];
            parentLeftPosition = getRectNextLeft(parentLeftPosition, heightFromLeft(i));
            if (parentNode != null) {
                depthLeft++;
                nodeLeft = makeNode("&lt;" + left.p + "," + left.v + "&gt;", new Point(parentLeftPosition, getRectNextTop(depthLeft)), Node.COLOR_LEFT, newLeftPosition);
                nodeLeft.setParent(parentNode);
            }
        }

        int newRightPosition = 2 * i + 2;
        Pair right = null;
        Node nodeRight = null;
        int parentRightPosition = parentPosition;
        if (newRightPosition < nodes.length) {
            right = nodes[newRightPosition];
            parentRightPosition = getRectNextRight(parentRightPosition, heightFromRight(i));
            if (parentNode != null) {
                depthRight++;
                nodeRight = makeNode("&lt;" + right.p + "," + right.v + "&gt;", new Point(parentRightPosition, getRectNextTop(depthRight)), Node.COLOR_RIGHT, newRightPosition);
                nodeRight.setParent(parentNode);
            }
        }

        if (left != null) {
            recursivePrint(arrayIndex, newLeftPosition, nodeLeft, parentLeftPosition, depthLeft, depthRight);
        }
        if (right != null) {
            recursivePrint(arrayIndex, newRightPosition, nodeRight, parentRightPosition, depthLeft, depthRight);
        }
    }

    private int getRectNextTop(int i) {
        return DIMENSION_TOP + i * DIMENSION_HEIGHT;
    }

    private int getRectNextLeft(int i) {
        return DIMENSION_LEFT + i * (DIMENSION_WIDTH * 2 + DIMENSION_HEIGHT);
    }

    private int getRectNextLeft(int parentPosition, int division) {
        return -DIMENSION_WIDTH / division + parentPosition;
    }

    private int getRectNextRight(int parentPosition, int division) {
        return DIMENSION_WIDTH / division + parentPosition;
    }

    private int heightFromLeft(int i) {
        int height = 1;

        while (i > 0 && nodes[i] != null) {
            i = (i - 1) / 2;
            height++;
        }

        return height;
    }

    private int heightFromRight(int i) {
        int height = 1;

        while (i > 0 && nodes[i] != null) {
            i = (i - 2) / 2;
            height++;
        }

        return height;
    }

    // paint links and nodes
    public void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        int n = getComponentCount();
        for (int i = 0; i < n; ++i) {
            Component c = getComponent(i);
            if (c instanceof Node) {
                ((Node) c).paintLink(g2);
            }
        }

        super.paintChildren(g);
    }

    // make a new node
    public Node makeNode(String text, Point pt, String color, int index) {
        Node n = new Node(text, pt, color, index);
        add(n);
        return n;
    }

    // Mouse listener
    // for selecting nodes, moving nodes, and dragging out links
    private MouseInputListener mouseListener = new MouseInputAdapter() {
        boolean dragging = false;
        int offsetX;
        int offsetY;

        public void mousePressed(MouseEvent e) {
            Component c = getComponentAt(e.getPoint());
            if (c instanceof Node) {
                setSelection((Node) c);
            } else {
                setSelection(null);
            }
        }

        public void mouseDragged(MouseEvent e) {
            if (selection == null) {
                return;
            }

            if (dragging) {
                selection.setLocation(e.getX() + offsetX, e.getY() + offsetY);
                repaint();
            } else {
                dragging = true;
                offsetX = selection.getX() - e.getX();
                offsetY = selection.getY() - e.getY();
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (dragging) {
                dragging = false;
            } else if (linkEnd != null) {
                if (linkTarget != null) {
                    selection.setParent(linkTarget);
                }
                linkEnd = null;
                linkTarget = null;
                repaint();
            }
        }
    };

    public void setSelection(Node n) {

        if (selection != null) {
            selection.repaint();
        }

        selection = n;
    }


}
