package org.graphstream.ui.viewer_swing.test.traffic.demo;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.geom.Point3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TrafficGraphGenerator {

    private static final String STYLE_SHEET_PATH =
            "src-test/org/graphstream/ui/viewer_swing/test/traffic/traffic.css";

    public Graph generateGraph() {
        Graph graph = new MultiGraph( "TestSprites" );
        graph.setAttribute( "ui.default.title", "Test Many Sprites" );
        graph.setAttribute( "ui.antialias" );

        populateGraph(graph);

        graph.setAttribute("ui.quality" );
        graph.setAttribute("ui.stylesheet", loadStyleSheet());

        return graph;
    }

    private void populateGraph(Graph graph) {
        Edge edge;
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("AB", "A", "B", true);
        graph.addEdge("BA", "B", "A", true);
        graph.addEdge("BC", "B", "C", true);
        graph.addEdge("CB", "C", "B", true);
        graph.addEdge("CA", "C", "A", true);
        graph.addEdge("AC", "A", "C", true);

        // Replacing node D with the modelling of an intersection
        graph.addNode("D1");
        graph.addNode("D2");
        graph.addNode("D3");
        graph.addNode("D4");
        graph.addNode("D5");
        graph.addNode("D6");

        graph.getNode("A").setAttribute("xyz", -1500d, -1100d, 0.0 );
        graph.getNode("B").setAttribute("xyz",  1500d, -1100d, 0.0 );
        graph.getNode("C").setAttribute("xyz",  100d, 1500d, 0.0 );
        graph.getNode("D1").setAttribute("xyz",  -100d, 300d, 0.0 );
        graph.getNode("D2").setAttribute("xyz",  100d, 300d, 0.0 );
        graph.getNode("D3").setAttribute("xyz",  250d, -100d, 0.0 );
        graph.getNode("D4").setAttribute("xyz",  200d, -300d, 0.0 );
        graph.getNode("D5").setAttribute("xyz",  -200d, -300d, 0.0 );
        graph.getNode("D6").setAttribute("xyz",  -250d, -100d, 0.0 );

        edge = graph.addEdge("CD1", "C", "D1", true);
        edge.setAttribute("ui.control-point", new Point3(-100.0d, 500.0d, 0.0));
        graph.addEdge("D2C", "D2", "C", true);

        edge = graph.addEdge("BD3", "B", "D3", true);
        edge.setAttribute("ui.control-point", new Point3(1000.0d, -600.0d, 0.0));
        edge = graph.addEdge("D4B", "D4", "B", true);
        edge.setAttribute("ui.control-point", new Point3(1000.0d, -600.0d, 0.0));

        edge = graph.addEdge("AD5", "A", "D5", true);
        edge.setAttribute("ui.control-point", new Point3(-1000.0d, -600.0d, 0.0));
        edge = graph.addEdge("D6A", "D6", "A", true);
        edge.setAttribute("ui.control-point", new Point3(-700.0d, -300.0d, 0.0));

        edge = graph.addEdge("D1D6", "D1", "D6", true);
        edge.setAttribute("ui.control-point", new Point3(-100.0d, 100.0d, 0.0));
        edge = graph.addEdge("D1D4", "D1", "D4", true);
        edge.setAttribute("ui.control-point", new Point3(0.0d, -0.0d, 0.0));
        edge = graph.addEdge("D5D2", "D5", "D2", true);
        edge.setAttribute("ui.control-point", new Point3(0.0d, 0.0d, 0.0));
        edge = graph.addEdge("D3D2", "D3", "D2", true);
        edge.setAttribute("ui.control-point", new Point3(100.0d, 100.0d, 0.0));
        edge = graph.addEdge("D3D6", "D3", "D6", true);
        edge.setAttribute("ui.control-point", new Point3(0.0d, 0.0d, 0.0));
        edge = graph.addEdge("D5D4", "D5", "D4", true);
        edge.setAttribute("ui.control-point", new Point3(0.0d, -240.0d, 0.0));


        addEdgeLengths(graph);
        //showLabels(graph);
    }

    private void showLabels(Graph graph) {
        graph.nodes().forEach(node -> node.setAttribute("ui.label", node.getId()));
    }

    private void addEdgeLengths(Graph graph) {
        graph.edges().forEach(edge -> edge.setAttribute("length", computeEdgeLength(edge)));
    }

    private double computeEdgeLength(Edge edge) {
        Node source = edge.getSourceNode();
        Node target = edge.getTargetNode();
        //System.out.println("source = "+ Arrays.toString(source.getAttribute("xyz", Object[].class)) + " target = " + target.getAttribute("xyz", Object[].class));

        Object[] sourceXYZ = source.getAttribute("xyz", Object[].class);
        Object[] targetXYZ = target.getAttribute("xyz", Object[].class);

        return Math.sqrt(Math.pow((double)targetXYZ[0] - (double)sourceXYZ[0], 2) + Math.pow((double)targetXYZ[1] - (double)sourceXYZ[1], 2));
    }

    private String loadStyleSheet() {
        try {
            return Files.lines(Paths.get(STYLE_SHEET_PATH))
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the style sheet from " + STYLE_SHEET_PATH, e);
        }
    }
}
