package org.graphstream.ui.viewer_swing.test.traffic.vehicles;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

class VehicleSprite extends Sprite {

    private static final double STEP_PERCENT = 0.005d;
    double step = 0;

    public VehicleSprite(String identifier, SpriteManager manager) {
        super(identifier, manager);
    }

    public void move() {

        double p = getX();
        if (step == 0) {
            step = calculateIncrement((Edge) getAttachment());
        }
        p += step;

        if (p < 0 || p > 1)
            chooseNextEdge();
        else
            setPosition(p);
    }

    public void chooseNextEdge() {
        Edge edge = (Edge) getAttachment();
        Node node = edge.getSourceNode();
        if (step > 0)
            node = edge.getTargetNode();

        Edge nextEdge = randomEdge(node);
        double pos;

        if (node == nextEdge.getSourceNode()) {
            step = calculateIncrement(nextEdge);
            pos = 0;
        } else {
            step = -calculateIncrement(nextEdge);
            pos = 1;
        }

        attachToEdge(nextEdge.getId());
        setPosition(pos);
    }

    /** Move in larger percentage steps across shorter edges */
    private double calculateIncrement(Edge edge) {
        double edgeLen = edge.getAttribute("length", Double.class);
        double scale = 3.0 / edgeLen;
        return STEP_PERCENT * scale;
    }

    /**
     * select an edge other than the one we came from
     */
    private Edge randomEdge(Node node) {
        int rand = (int) (Math.random() * node.getOutDegree());
        return node.getLeavingEdge(rand);
    }
}