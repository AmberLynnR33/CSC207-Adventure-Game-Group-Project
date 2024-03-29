package views;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.*;

/**
 * Class ZoomFrame
 * Creates the popup that displays the ZoomOption window in the AdventureGame
 */
public class ZoomFrame extends javax.swing.JFrame implements Serializable {
    public int w;
    public int h;
    public Point pointView;

    /**
     * Constructor
     * Creates and displays the ZoomOption window for the AdventureGame
     * @param roomImageDir the directory to the image file of the room the player is currently in
     */
    public ZoomFrame(String roomImageDir) {

        JScrollPane scrollPane1 = new JScrollPane();
        JLabel label1 = new JLabel();
        JButton zoomInButton = new JButton();
        JButton zoomOutButton = new JButton();
        ImageIcon img = new ImageIcon(roomImageDir);


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
        scrollPane1.setViewportView(label1);

        zoomInButton.setText("Zoom in");
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h = h + 30;
                w = w + 30;
                ImageIcon icon = new ImageIcon(zoom(h, w, img.getImage()));
                label1.setIcon(icon);
            }
        });

        zoomOutButton.setText("Zoom out");
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((h > 31) && (w > 31)){
                    h = h - 30;
                    w = w - 30;}
                ImageIcon icon = new ImageIcon(zoom(h, w, img.getImage()));
                label1.setIcon(icon);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(zoomInButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(zoomOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(zoomInButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(zoomOutButton))
                                        .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);

        w = scrollPane1.getWidth();
        h = scrollPane1.getHeight();
        ImageIcon icon = new ImageIcon(zoom(h, w, img.getImage()));
        label1.setIcon(icon);
        setTitle("Room Visuals");
    }
    /**
     * Private helper method to render the BufferedImage room image for the constructor
     * @param h height of the image
     * @param w width of the image
     * @param img the room image
     */
    private Image zoom(int h, int w, Image img) {
        BufferedImage bufferedImg = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);
        Graphics2D gp = bufferedImg.createGraphics();
        gp.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gp.drawImage(img, 0, 0, w, h, null);
        gp.dispose();
        return bufferedImg;
    }
}
