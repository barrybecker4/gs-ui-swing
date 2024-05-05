package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class TrafficDemo implements ViewerListener {
    
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing.util.Display");
        (new TrafficDemo()).run();
    }
    
    /** The application runs while this is true. */
    boolean loop = true;


    /** The set of sprites. */
    SpriteManager sprites = null;

    int SPRITE_COUNT = 100;
    String[] COLORS = new String[] { "#aa5588;", "#11bb99;", "#5522aa;" };

    
    private void run() {
        Graph graph = new TrafficGraphGenerator().generateGraph();

        Viewer viewer = graph.display(false );
        ViewerPipe pipeIn = viewer.newViewerPipe();

        pipeIn.addAttributeSink( graph );
        pipeIn.addViewerListener( this );
        pipeIn.pump();

        sleep( 1000 );
        addSprites(graph);

        while( loop ) {
            pipeIn.pump();
            moveSprites();
            sleep( 10 );
        }
        System.exit(0);
    }

    protected void sleep( long ms ) {
        try {
            Thread.sleep( ms );
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

// Viewer Listener Interface

    public void viewClosed( String id ) { loop = false; }
    public void buttonPushed( String id ) {}
    public void buttonReleased( String id ) {}

    private void moveSprites() {
         sprites.forEach( s -> ((TestSprite)s).move() );    
    }

    private void addSprites(Graph graph) {
        sprites = new SpriteManager(graph);
        sprites.setSpriteFactory( new TestSpriteFactory() );

        for( int i = 0 ; i < SPRITE_COUNT ; i++ ) {
            Sprite sprite = sprites.addSprite( i + "" );
            sprite.setAttribute("ui.style", "fill-color: " + COLORS[(int) (Math.random() * COLORS.length)]);
            sprite.setPosition(Math.random());
        }

        sprites.forEach ( s -> s.attachToEdge( randomEdge( graph ).getId() ));
    }


    private Edge randomEdge(Graph graph) {
        int rand = (int) (Math.random() * graph.edges().count());
        return graph.getEdge(rand);
    }
    
    public void mouseOver(String id){}
    public void mouseLeft(String id){}
}
