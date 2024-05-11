package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.SpriteManager;


public class VehicleSpriteGenerator {

    /** The set of sprites. */
    private SpriteManager sprites = null;
    private final int numSprites;

    public VehicleSpriteGenerator(int numSprites) {
        this.numSprites = numSprites;
    }

    void addSprites(Graph graph) {
        sprites = new SpriteManager(graph);
        sprites.setSpriteFactory( new VehicleSpriteFactory() );

        for( int i = 0 ; i < numSprites; i++ ) {
            sprites.addSprite(i + "");
        }
        new VehiclePlacer().placeVehicleSprites(sprites, graph);
    }

    void moveSprites() {
        sprites.forEach( s -> ((VehicleSprite)s).move() );
    }
}
