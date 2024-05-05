package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

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

        graph.setAttribute( "ui.quality" );
        graph.setAttribute("ui.stylesheet", loadStyleSheet());

        return graph;
    }

    private void populateGraph(Graph graph) {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("AB", "A", "B", true);
        graph.addEdge("BA", "B", "A", true);
        graph.addEdge("BC", "B", "C", true);
        graph.addEdge("CB", "C", "B", true);
        graph.addEdge("CA", "C", "A", true);
        graph.addEdge("AC", "A", "C", true);

        graph.addEdge("DA", "D", "A", true);
        graph.addEdge("DB", "D", "B", true);
        graph.addEdge("DC", "D", "C", true);
        graph.addEdge("AD", "A", "D", true);
        graph.addEdge("BD", "B", "D", true);
        graph.addEdge("CD", "C", "D", true);

        graph.getNode("A").setAttribute("xyz", -1.5, -1.1, 0 );
        graph.getNode("B").setAttribute("xyz",  1.5, -1.1, 0 );
        graph.getNode("C").setAttribute("xyz",  0.1, 1.3, 0 );
        graph.getNode("D").setAttribute("xyz",  0, 0, 0 );
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
