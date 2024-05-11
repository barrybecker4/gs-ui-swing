package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.viewer_swing.test.traffic.vehicles.VehicleSpriteGenerator;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Frame;


public class TrafficDemo {

    private final int numSprites;

    public TrafficDemo(int numSprites) {
        this.numSprites = numSprites;
    }
    
    /** The application runs while this is true. */
    
    void run() {
        Graph graph = new TrafficGraphGenerator().generateGraph();

        Viewer viewer = graph.display(false );
        ViewerAdapter viewerListener = new ViewerAdapter();

        setViewerSize(1200, 900, viewer);

        ViewerPipe pipeIn = viewer.newViewerPipe();

        pipeIn.addAttributeSink( graph );
        pipeIn.addViewerListener( new ViewerAdapter() );
        pipeIn.pump();

        sleep( 1000 );
        VehicleSpriteGenerator spriteGenerator = new VehicleSpriteGenerator(numSprites);
        spriteGenerator.addSprites(graph);

        while( viewerListener.isLooping() ) {
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

}
