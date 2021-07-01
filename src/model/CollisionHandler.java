package model;

import java.awt.*;

public interface CollisionHandler {
    void handle(Point originalLocation, Sprite from, Sprite to);
}
