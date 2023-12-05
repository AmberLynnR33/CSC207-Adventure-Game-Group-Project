package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DistanceView extends javax.swing.JFrame {
    public Point pointView;


    /**
     * Constructor
     * Creates and displays the Journey thus far for the AdventureGame
     * @param distance2str the string representation of the journey that player has been on
     */
    public DistanceView(String distance2str){

        JScrollPane scrollPane1 = new JScrollPane();
        JLabel label1 = new JLabel();

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

//        label1.setText(distance2str);
        java.awt.Font f = new java.awt.Font("Arial", java.awt.Font.PLAIN, 20);
        label1.setFont(f);
        label1.setVerticalAlignment(1);

        label1.setText(distance2str);

        scrollPane1.setViewportView(label1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

                                  .addGap(0, 0, 0))
        ));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                        .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
        ));
        pack();
        setLocationRelativeTo(null);
        setTitle("Journey thus far: ");
        setFont(f);

    }
}
