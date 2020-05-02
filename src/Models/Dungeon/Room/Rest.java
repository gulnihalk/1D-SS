package Models.Dungeon.Room;

import Models.Creatures.AbstractCharacter;
import Models.Dungeon.AbstractRoom;
import Models.TextBasedUI;
import sts.Main;
import sts.RestScene;
import sts.TreasureScene;

import static sts.Main.game;


public class Rest extends AbstractRoom {
    public Rest(AbstractRoom c) {
        type = RoomType.REST;
        children = c;
        done = false;
    }

    @Override
    public void start() {

        game.currentScene = new RestScene();
        Main.window.setScene(
                game.currentScene);
        game.currentScene.initialize();

    }

    @Override
    public RoomType getType() {
        return type;
    }

    @Override
    public AbstractRoom getChildren() {
        return children;
    }

    @Override
    public boolean getDone() {
        return done;
    }
}
