package slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateLoss extends BasicGameState {
    @Override
    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
        //
    }

    @Override
    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
        g.setBackground(Color.white);
        g.setColor(Color.blue);
        g.drawString("Temps écoulé, vous avez perdu !!!", 250, 250);
    }

    @Override
    public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
        //
    }

    @Override
    public int getID() {
        return States.LOST;
    }
}
