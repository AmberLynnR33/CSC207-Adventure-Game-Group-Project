package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
<<<<<<< HEAD

/**
 * Abstract class PathView
 * Creates the popup that displays the different paths in the AdventureGame
 */
abstract class PathView extends javax.swing.JFrame {
    public Point pointView;
    private JScrollPane scrollPane1;
    private JLabel label1;
    private javax.swing.GroupLayout layout;

    public PathView(){
        setUpPathView();
    }

    /**
     * Getter method for the scroll pane attribute
     */
    public JScrollPane getScrollPane1() {
        return this.scrollPane1;
    }

    /**
     * Getter method for the label attribute
     */public JLabel getLabel1() {
        return this.label1;
    }

    @Override
    /**
     * Getter method for the layout attribute
     */public GroupLayout getLayout() {
        return this.layout;
    }

    /**
     * Constructor
     * Sets up the layout for the popup window
     */
    private void setUpPathView() {
        this.scrollPane1 = new JScrollPane();
        this.label1 = new JLabel();
=======
/**
 * Concrete class DistanceView that inherits from abstract class PathView
 *  Creates the popup that displays the Journey that player has made thus far in the AdventureGame
 */
public class DistanceView extends PathView {

<<<<<<<< HEAD:views/DistanceView.java
    /**
     * Constructor for DistanceView
     * Set the title and text of the layout specific to Journey
     */
    public DistanceView(String distance2str) {
        super();
        setTitle("Journey thus far");
        super.getLabel1().setText(distance2str);
========
public class PathView extends javax.swing.JFrame {
    public Point pointView;


    /**
     * Constructor
     * Creates and displays the Path for the AdventureGame
     * @param path2str the string representation of the path that player has been on
     */
    public PathView(String path2str){

        JScrollPane scrollPane1 = new JScrollPane();
        JLabel label1 = new JLabel();
>>>>>>> main

        label1.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                Point dragEventPoint = e.getPoint();
                JViewport viewport = (JViewport) label1.getParent();

                Point viewPos = viewport.getViewPosition();
                int maxViewPosX = label1.getWidth() - viewport.getWidth();
                int maxViewPosY = label1.getHeight() - viewport.getHeight();

                if (label1.getWidth() > viewport.getWidth()) {
                    viewPos.x -= dragEventPoint.x - pointView.x;

                    if (viewPos.x < 0) {
                        viewPos.x = 0;
                        pointView.x = dragEventPoint.x;
                    }

                    if (viewPos.x > maxViewPosX) {
                        viewPos.x = maxViewPosX;
                        pointView.x = dragEventPoint.x;
                    }
                }

                if (label1.getHeight() > viewport.getHeight()) {
                    viewPos.y -= dragEventPoint.y - pointView.y;

                    if (viewPos.y < 0) {
                        viewPos.y = 0;
                        pointView.y = dragEventPoint.y;
                    }

                    if (viewPos.y > maxViewPosY) {
                        viewPos.y = maxViewPosY;
                        pointView.y = dragEventPoint.y;
                    }
                }

                viewport.setViewPosition(viewPos);

            }
        });

        label1.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                label1.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                pointView = e.getPoint();
            }
            public void mouseReleased(MouseEvent e){
                label1.setCursor(null);
            }
        });

<<<<<<< HEAD
=======
//        label1.setText(distance2str);
>>>>>>> main
        java.awt.Font f = new java.awt.Font("Arial", java.awt.Font.PLAIN, 20);
        label1.setFont(f);
        label1.setVerticalAlignment(1);

<<<<<<< HEAD
        scrollPane1.setViewportView(label1);

        this.layout = new javax.swing.GroupLayout(getContentPane());
=======
        label1.setText(path2str);

        scrollPane1.setViewportView(label1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
>>>>>>> main
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

<<<<<<< HEAD
                                        .addGap(0, 0, 0))
                        ));
=======
                                  .addGap(0, 0, 0))
        ));
>>>>>>> main
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
<<<<<<< HEAD
                                                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        ));
        pack();
        setLocationRelativeTo(null);
        setFont(f);

    }
}
=======
                                        .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
        ));
        pack();
        setLocationRelativeTo(null);
        setTitle("Journey thus far: ");
        setFont(f);
>>>>>>>> main:views/PathView.java

    }
}
>>>>>>> main
