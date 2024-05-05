package org.graphstream.ui.viewer_swing.test.traffic;


import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

class VehicleSprite extends Sprite {
    double dir = 0.01f;

    public VehicleSprite(String identifier, SpriteManager manager) {
        super(identifier, manager);
    }

    public void move() {
        double p = getX();
        p += dir;

        if (p < 0 || p > 1)
            chooseNextEdge();
        else
            setPosition(p);
    }

    public void chooseNextEdge() {
        Edge edge = (Edge) getAttachment();
        Node node = edge.getSourceNode();
        if (dir > 0)
            node = edge.getTargetNode();


        Edge next = randomEdge(node, edge);
        double pos = 0;

        if (node == next.getSourceNode()) {
            dir = 0.01f;
            pos = 0;
        } else {
            dir = -0.01f;
            pos = 1;
        }

        attachToEdge(next.getId());
        setPosition(pos);
    }

    /**
     * select an edge other than the one we came from
     */
    private Edge randomEdge(Node node, Edge fromEdge) {
        int rand = (int) (Math.random() * node.getOutDegree());
        return node.getLeavingEdge(rand);
    }
}