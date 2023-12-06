package views;

/**
 * Concrete class DisplacementView that inherits from abstract class PathView
 *  Creates the popup that displays the Progress that player has made thus far in the AdventureGame
 */
public class DisplacementView extends PathView {

    /**
     * Constructor for DisplacementView
     * Set the title and text of the layout specific to Progress
     */
    public DisplacementView(String displacement2str) {
        super();
        setTitle("Progress thus far");
        super.getLabel1().setText(displacement2str);

    }
}
