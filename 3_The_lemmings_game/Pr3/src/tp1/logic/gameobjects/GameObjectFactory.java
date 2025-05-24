package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameObject;
import tp1.logic.GameWorld;
import tp1.view.Messages;

//Creates a game object from each line of the file that loaded using LoadCommand
public class GameObjectFactory {	
	private static final List<GameObject> available_game_objects = Arrays.asList(
			new Lemming(),
			new Wall(),
			new MetalWall(),
			new ExitDoor()
			);
	
	/*---CHECKERS---*/
	
	public static GameObject parse(String line, GameWorld g) throws ObjectParseException, OffBoardException {
		GameObject gObj;
		for (GameObject aux : available_game_objects) {
			if ((gObj = aux.parse(line, g)) != null) 
				return gObj;
		}
		
		throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(line));
	}
}
