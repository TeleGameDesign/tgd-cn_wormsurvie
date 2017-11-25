package general;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import general.worms.World;
import menus.MainMenu;
import menus.WelcomeMenu;

public class Main extends StateBasedGame{

	public static int longueur=1280;
	public static int hauteur=720;

	public static void main(String[] args) throws SlickException {


		//Normalement c'est plus necessaire, c'est fait dans le setup du projet en theorie
		System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());

		AppGameContainer app = new AppGameContainer(new Main(),longueur, hauteur, false);
		app.setTargetFrameRate(60);
		app.setVSync(true);
		app.setShowFPS(true);
		app.start();
	}


	public Main() {
		super("INSERER LE NOM DU PROJET ICI MAIS EN PLUS COURT QUAND MEME PARCE-QUE LA C'EST UN PEU LONG");
	}



	@Override
	public void initStatesList(GameContainer container) throws SlickException {
	    long time = System.currentTimeMillis();

	    addState(new WelcomeMenu());
		addState(new MainMenu());
		addState(new World());

        System.out.println("time to load game = "+(System.currentTimeMillis()-time));

        this.enterState(WelcomeMenu.ID);
	}
}