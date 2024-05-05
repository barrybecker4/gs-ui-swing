/*
 * This file is part of GraphStream <http://graphstream-project.org>.
 *
 * GraphStream is a library whose purpose is to handle static or dynamic
 * graph, create them from scratch, file or any source and display them.
 *
 * This program is free software distributed under the terms of two licenses, the
 * CeCILL-C license that fits European law, and the GNU Lesser General Public
 * License. You can  use, modify and/ or redistribute the software under the terms
 * of the CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
 * URL <http://www.cecill.info> or under the terms of the GNU LGPL as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C and LGPL licenses and that you accept their terms.
 */

/**
 * @author Antoine Dutot <antoine.dutot@graphstream-project.org>
 * @author Guilhelm Savin <guilhelm.savin@graphstream-project.org>
 * @author Hicham Brahimi <hicham.brahimi@graphstream-project.org>
 */

package org.graphstream.ui.viewer_swing.test;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.geom.Point3;

public class TestCurvedEdges {
    public static void main(String[] args) {
        (new TestCurvedEdges()).run();
    }

    private void run() {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing.util.Display");
        Graph graph = new MultiGraph( "TestSprites" );

        populateGraph(graph);

        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.setAttribute("ui.antialias");
        graph.display(false);
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
    }

    String styleSheet = "edge { shape: cubic-curve; }";
}
