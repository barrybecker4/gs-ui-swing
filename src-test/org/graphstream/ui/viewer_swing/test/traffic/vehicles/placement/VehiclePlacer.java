package org.graphstream.ui.viewer_swing.test.traffic.vehicles.placement;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

/**
 * Determines vehicle placements along all the edges of a provided graph.
 * We don't want the vehicles places perfectly uniformly because that would not be natural.
 * Instead, they should be placed randomly, but with no overlap.
 * Throw an error if there are so many vehicles that they cannot be placed without overlap.
 * Each edge will have a maximum number of vehicles that it can hold. Do not exceed that.
 *
 * Each edge can have an expected allocation assuming uniform distribution.
 *   expectedAllocation = edgeLen / totalLen * numVehicles.
 * Each edge can support a maximum number of vehicles
 *   maxVehicles = floor(edgeLen / (MIN_GAP + vehicleLen))
 * If expectedAllocation > maxVehicles for any edge, then throw error.
 *
 * The actual number of vehicles to put on an edge can be
 * delta = maxVehicles - expectedAllocation
 * random( expectedAllocation - delta, expectedAllocation + delta + 1)
 * Then if there are too few allocated, randomly add them from edges until numVehicles reached.
 * If too many allocation, randomly remove them from edges until numVehicles reached.
 *
 * Maintain a map from edgeId to num vehicles to allocate to it.
 * Allocation algorithm
 *  - Divide the edge into maxVehicles equal slots.
 *  - Create an array of that same length
 *  - Using vehicle hash mod maxVehicles for that edge,
 *     place each into the array using array hashing algorithm,
 *     which resolves conflicts by moving to the next available slot.
 *  - Place the sprites in the array into the edge at the corresponding position.
 */
public class VehiclePlacer {

    /** The minimum gap between vehicles */
    //private final double minGap;

    private static final double MIN_GAP = 5;
    private static final String[] COLORS = new String[] { "#aa5588;", "#11bb99;", "#5522aa;" };

    public void placeVehicleSprites(SpriteManager sprites, Graph graph) {

        for( int i = 0 ; i < sprites.getSpriteCount(); i++ ) {
            Sprite sprite = sprites.addSprite( i + "" );
            sprite.setAttribute("ui.style", "fill-color: " + COLORS[(int) (Math.random() * COLORS.length)]);
            // TODO: make sure that they are spaced out when adding. We don't want to overlap with another car
            sprite.setPosition(Math.random());
        }

        sprites.forEach ( s -> s.attachToEdge( randomEdge( graph ).getId() ));
    }

//    private double findTotaLengthOfAllEdges(Graph graph) {
//        return graph.edges()
//                .mapToDouble(edge -> edge.getAttribute("length", Double.class)) // Use a default value in case the attribute is not set
//                .sum();
//    }

    private Edge randomEdge(Graph graph) {
        int rand = (int) (Math.random() * graph.edges().count());
        return graph.getEdge(rand);
    }
}
