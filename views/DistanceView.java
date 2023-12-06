package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
/**
 * Concrete class DistanceView that inherits from abstract class PathView
 *  Creates the popup that displays the Journey that player has made thus far in the AdventureGame
 */
public class DistanceView extends PathView {

    /**
     * Constructor for DistanceView
     * Set the title and text of the layout specific to Journey
     */
    public DistanceView(String distance2str) {
        super();
        setTitle("Journey thus far");
        super.getLabel1().setText(distance2str);

    }
}