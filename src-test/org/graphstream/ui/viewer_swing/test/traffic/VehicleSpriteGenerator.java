package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

public class VehicleSpriteGenerator {

    private static final String[] COLORS = new String[] { "#aa5588;", "#11bb99;", "#5522aa;" };

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
            Sprite sprite = sprites.addSprite( i + "" );
            sprite.setAttribute("ui.style", "fill-color: " + COLORS[(int) (Math.random() * COLORS.length)]);
            sprite.setPosition(Math.random());
        }

        sprites.forEach ( s -> s.attachToEdge( randomEdge( graph ).getId() ));
    }

    void moveSprites() {
        sprites.forEach( s -> ((VehicleSprite)s).move() );
    }

    private Edge randomEdge(Graph graph) {
        int rand = (int) (Math.random() * graph.edges().count());
        return graph.getEdge(rand);
    }
}