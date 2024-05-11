package org.graphstream.ui.viewer_swing.test.traffic.demo;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import org.graphstream.ui.viewer_swing.test.traffic.vehicles.VehicleSpriteGenerator;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Frame;


public class TrafficDemo {

    private final ViewerAdapter viewerListener = new ViewerAdapter();
    private final VehicleSpriteGenerator spriteGenerator;

    public TrafficDemo(int numSprites) {
        this.spriteGenerator = new VehicleSpriteGenerator(numSprites);
    }
    
    public void run() {
        Graph graph = new TrafficGraphGenerator().generateGraph();

        Viewer viewer = graph.display(false );
        setViewerSize(1200, 900, viewer);
        ViewerPipe pipeIn = viewer.newViewerPipe();

        pipeIn.addAttributeSink( graph );
        pipeIn.addViewerListener( viewerListener );
        pipeIn.pump();

        sleep( 1000 ); // give a chance to layout
        spriteGenerator.addSprites(graph);

        simulateTrafficFlow(pipeIn);
    }

    private void simulateTrafficFlow(ViewerPipe pipeIn) {
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
