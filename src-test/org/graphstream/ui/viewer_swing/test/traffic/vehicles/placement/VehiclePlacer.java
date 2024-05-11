package org.graphstream.ui.viewer_swing.test.traffic.vehicles.placement;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Determines vehicle placements along all the edges of a provided graph.
 * We don't want the vehicles places perfectly uniformly because that would not be natural.
 * Instead, they should be placed randomly, but with no overlap.
 * Throw an error if there are so many vehicles that they cannot be placed without overlap.
 * Each edge will have a maximum number of vehicles that it can hold. Do not exceed that.
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
    private static final double MIN_GAP = 5.0;

    /**
     * This will not be exact because the sprites size is relative to the window size.
     * Sprites get proportionally larger as the window size shrinks.
     * See traffic.css - size: 12px, 6px;
     */
    private static final double VEHICLE_LENGTH = 12;

    private static final String[] VEHICLE_COLORS = new String[] { "#aa5588;", "#11bb99;", "#5522aa;" };


    private final SpriteManager sprites;
    private final Graph graph;

    private final Random rnd;

    public VehiclePlacer(SpriteManager sprites, Graph graph) {
        rnd = new Random(0);
        this.sprites = sprites;
        this.graph = graph;
    }

    public void placeVehicleSprites() {

        //Map<String, Integer> edgeIdToNumVehicles = determineNumVehiclesOnEdges();
        ///allocateVehicles(edgeIdToNumVehicles);

        for( int i = 0 ; i < sprites.getSpriteCount(); i++ ) {
            Sprite sprite = sprites.addSprite( i + "" );
            sprite.setAttribute("ui.style", "fill-color: " + VEHICLE_COLORS[(int) (Math.random() * VEHICLE_COLORS.length)]);
            // TODO: make sure that they are spaced out when adding. We don't want to overlap with another car
            sprite.setPosition(rnd.nextDouble());
        }

        sprites.forEach ( s -> s.attachToEdge( randomEdge( graph ).getId() ));
    }

    private void allocateVehicles(Map<String, Integer> edgeIdToNumVehicles) {
    }

    /**
     * Each edge can have an expected allocation assuming uniform distribution.
     *   expectedAllocation = edgeLen / totalLen * numVehicles.
     * Each edge can support a maximum number of vehicles
     *   maxAllocation = floor(edgeLen / (MIN_GAP + vehicleLen))
     * If expectedAllocation > maxAllocation for any edge, then throw error.
     *
     * The actual number of vehicles to put on an edge can be
     * delta = maxAllocation - expectedAllocation
     * random( expectedAllocation - delta, expectedAllocation + delta + 1)
     * Then if there are too few allocated, randomly add them from edges until numVehicles reached.
     * If too many allocation, randomly remove them from edges until numVehicles reached.
     */
    private Map<String, Integer> determineNumVehiclesOnEdges() {
        int numVehicles = sprites.getSpriteCount();
        double totalLen = findTotaLengthOfAllEdges(graph);
        Map<String, Integer> edgeIdToNumVehicles = new HashMap<>();
        AtomicInteger totalAllocation = new AtomicInteger();
        graph.edges().forEach(edge -> {
            String edgeId = edge.getId();
            double edgeLen = getEdgeLen(edge);
            int expectedAllocation = (int) (numVehicles * edgeLen / totalLen);
            int maxAllocation = (int) (edgeLen / (MIN_GAP + VEHICLE_LENGTH));
            if (expectedAllocation > maxAllocation) {
                throw new IllegalArgumentException("Trying to allocate more vehicles (" +
                        expectedAllocation + ") than the streets will hold ("+ maxAllocation + ")!"
                );
            }
            int delta = maxAllocation - expectedAllocation;
            int randomAllocation = expectedAllocation - delta + rnd.nextInt(2 * delta + 1);
            assert randomAllocation <= maxAllocation;
            totalAllocation.addAndGet(randomAllocation);
            edgeIdToNumVehicles.put(edgeId, randomAllocation);
        });

        String[] edgeIds = edgeIdToNumVehicles.keySet().toArray(new String[0]);

        // now do some fine-tuning in the event that we have too many or too few vehicles allocated
        while (totalAllocation.get() > numVehicles) {
            String rndId = edgeIds[rnd.nextInt(edgeIds.length)];
            edgeIdToNumVehicles.put(rndId, edgeIdToNumVehicles.get(rndId) - 1);
        }
        while (totalAllocation.get() < numVehicles) {
            String rndId = edgeIds[rnd.nextInt(edgeIds.length)];
            edgeIdToNumVehicles.put(rndId, edgeIdToNumVehicles.get(rndId) + 1);
        }

        int sumAllocatedVehicles = edgeIdToNumVehicles.values()
                .stream()
                .mapToInt(Integer::intValue) // Map values to integers
                .sum();
        assert(numVehicles == sumAllocatedVehicles);

        return edgeIdToNumVehicles;
    }

    private double findTotaLengthOfAllEdges(Graph graph) {
        return graph.edges()
                .mapToDouble(this::getEdgeLen) // Use a default value in case the attribute is not set
                .sum();
    }

    private double getEdgeLen(Edge edge) {
        return edge.getAttribute("length", Double.class);
    }

    private Edge randomEdge(Graph graph) {
        int rand = (int) (Math.random() * graph.edges().count());
        return graph.getEdge(rand);
    }
}
