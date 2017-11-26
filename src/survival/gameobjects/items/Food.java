package survival.gameobjects.items;

import survival.gameobjects.gameplay.Compteur;
import org.newdawn.slick.Image;
import survival.gameobjects.gameplay.Player;
import survival.gameobjects.gameplay.TestObject;
import survival.utils.Vector2;
import survival.worlds.World;

public class Food extends Items {
	
	public float amount;
	
	public Food (Vector2 location, String name,float amount,Image sprite)
	{
		super(location,sprite);
		this.name = name;
		this.amount = amount;
	}
	
	//the action that made the item
	public void action()
	{
		World.activePlayer.hunger.canAdd(amount);
		World.activePlayer.removeItem(this);
	}

	


}
