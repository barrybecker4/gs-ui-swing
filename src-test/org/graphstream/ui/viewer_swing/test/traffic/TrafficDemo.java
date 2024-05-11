package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.viewer_swing.test.traffic.vehicles.VehicleSpriteGenerator;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Frame;


public class TrafficDemo implements ViewerListener {
    
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing.util.Display");
        (new TrafficDemo()).run();
    }

    int SPRITE_COUNT = 100;
    
    /** The application runs while this is true. */
    boolean loop = true;
    
    private void run() {
        Graph graph = new TrafficGraphGenerator().generateGraph();

        Viewer viewer = graph.display(false );

        setViewerSize(1200, 900, viewer);

        ViewerPipe pipeIn = viewer.newViewerPipe();

        pipeIn.addAttributeSink( graph );
        pipeIn.addViewerListener( this );
        pipeIn.pump();

        sleep( 1000 );
        VehicleSpriteGenerator spriteGenerator = new VehicleSpriteGenerator(SPRITE_COUNT);
        spriteGenerator.addSprites(graph);

        while( loop ) {
            pipeIn.pump();
            spriteGenerator.moveSprites();
            sleep( 10 );
        }
        System.exit(0);
    }

    private void setViewerSize(int width, int height, Viewer viewer) {
        viewer.getDefaultView().openInAFrame(true);
        Frame frame = JFrame.getFrames()[JFrame.getFrames().length - 1];
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setVisible(true);
    }

    private void sleep( long ms ) {
        try {
            Thread.sleep( ms );
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Viewer Listener Interface
    public void viewClosed( String id ) { loop = false; }
    public void buttonPushed( String id ) {}
    public void buttonReleased( String id ) {}
    public void mouseOver(String id){}
    public void mouseLeft(String id){}
}
