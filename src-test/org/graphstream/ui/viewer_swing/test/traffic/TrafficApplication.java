package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.ui.viewer_swing.test.traffic.demo.TrafficDemo;

public class TrafficApplication {

    private static final int SPRITE_COUNT = 100;

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing.util.Display");
        (new TrafficDemo(SPRITE_COUNT)).run();
    }

}
