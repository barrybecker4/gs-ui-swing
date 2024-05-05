package org.graphstream.ui.viewer_swing.test.traffic;

import org.graphstream.ui.graphicGraph.stylesheet.Values;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteFactory;
import org.graphstream.ui.spriteManager.SpriteManager;


class TestSpriteFactory extends SpriteFactory {
    @Override
    public Sprite newSprite(String identifier, SpriteManager manager, Values position) {
        return new TestSprite(identifier, manager);
    }
}