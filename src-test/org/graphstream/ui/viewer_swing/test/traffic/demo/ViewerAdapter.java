package org.graphstream.ui.viewer_swing.test.traffic.demo;

import org.graphstream.ui.view.ViewerListener;

public class ViewerAdapter implements ViewerListener {

    private boolean looping = true;

    public boolean isLooping() {
        return looping;
    }

    // Viewer Listener Interface
    public void viewClosed( String id ) {
        looping = false;
    }

    public void buttonPushed( String id ) {}
    public void buttonReleased( String id ) {}
    public void mouseOver(String id){}
    public void mouseLeft(String id){}
}
