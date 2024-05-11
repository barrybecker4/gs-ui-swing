package org.graphstream.ui.viewer_swing.test.traffic.vehicles;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

/**
 * Determines vehicle placements along a line of totalLength
 */
public class VehiclePlacer {

    /** The minimum gap between vehicles */
    //private final double minGap;

    private static final double MIN_GAP = 5;
    private static final String[] COLORS = new String[] { "#aa5588;", "#11bb99;", "#5522aa;" };

    public void placeVehicleSprites(SpriteManager sprites, Graph graph) {
        double totalLength = findTotaLengthOfAllEdges(graph);

        for( int i = 0 ; i < sprites.getSpriteCount(); i++ ) {
            Sprite sprite = sprites.addSprite( i + "" );
            sprite.setAttribute("ui.style", "fill-color: " + COLORS[(int) (Math.random() * COLORS.length)]);
            // TODO: make sure that they are spaced out when adding. We don't want to overlap with another car
            sprite.setPosition(Math.random());
        }

        sprites.forEach ( s -> s.attachToEdge( randomEdge( graph ).getId() ));
    }

    private double findTotaLengthOfAllEdges(Graph graph) {
        return graph.edges()
                .mapToDouble(edge -> edge.getAttribute("length", Double.class)) // Use a default value in case the attribute is not set
                .sum();
    }

    private Edge randomEdge(Graph graph) {
        int rand = (int) (Math.random() * graph.edges().count());
        return graph.getEdge(rand);
    }
}
