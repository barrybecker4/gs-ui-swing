package org.graphstream.ui.viewer_swing.test.traffic.vehicles;

import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.viewer_swing.test.traffic.vehicles.placement.VehiclePlacer;


public class VehicleSpriteGenerator {

    /** The set of sprites. */
    private SpriteManager sprites = null;
    private final int numSprites;

    public VehicleSpriteGenerator(int numSprites) {
        this.numSprites = numSprites;
    }

    public void addSprites(Graph graph) {
        sprites = new SpriteManager(graph);
        sprites.setSpriteFactory( new VehicleSpriteFactory() );

        for (int i = 0; i < numSprites; i++) {
            sprites.addSprite(i + "");
        }
        new VehiclePlacer(sprites, graph).placeVehicleSprites();
    }

    public void moveSprites() {
        sprites.forEach( s -> ((VehicleSprite)s).move() );
    }
}