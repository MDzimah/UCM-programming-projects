package tp1.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.view.Messages;

//When load command is executed, this class retrieves the file in question 
class FileGameConfiguration implements GameConfiguration {
	public static final String DIR = "configurations/";
	
	private String nombreFichero;
	private GameObjectContainer goc;
	private int cycle;	
	private int numLemmingsInBoard;
	private int numDeadLems;
	private int numLemmingsExit;
	private int numLemmingsToWin;
	
	FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException {
		this.goc = new GameObjectContainer();
		
		String line = null;
		this.nombreFichero = fileName;
		
		try (BufferedReader bf = new BufferedReader(new FileReader(Paths.get(DIR + fileName + ".txt").toFile()))) {	
			line = bf.readLine();
			String[] l = line.trim().split("\\s+");
			
			if (l.length == 5) {
				this.cycle = Integer.valueOf(l[0]);
				this.numLemmingsInBoard = Integer.valueOf(l[1]);
				this.numDeadLems = Integer.valueOf(l[2]);
				this.numLemmingsExit = Integer.valueOf(l[3]);
				this.numLemmingsToWin = Integer.valueOf(l[4]);
					
				while((line = bf.readLine()) != null) {
					this.goc.add(GameObjectFactory.parse(line, game));
				}
			}
			else throw new NumberFormatException();
		}
		catch (FileNotFoundException e) {
			throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(this.nombreFichero));
		}
		catch (IOException e) {
			throw new GameLoadException(Messages.READ_ERROR.formatted(this.nombreFichero));
		}
		catch (NumberFormatException e) {
			throw new GameLoadException(Messages.INVALID_GAME_STATUS.formatted(line));
		}
		catch (OffBoardException obe) {
			throw new GameLoadException(obe.getMessage());
		}
		catch (ObjectParseException ope) {
			throw new GameLoadException(ope.getMessage());
		}
	}

	
	/*---GETTERS---*/
	
	@Override
	public int getCycle() { return this.cycle; }

	@Override
	public int numLemmingsInBoard() { return this.numLemmingsInBoard; }

	@Override
	public int numLemmingsDead() { return this.numDeadLems; }

	@Override
	public int numLemmingsExit() { return this.numLemmingsExit; }

	@Override
	public int numLemmingsToWin() { return this.numLemmingsToWin; }

	@Override
	public GameObjectContainer getGameObjects(GameWorld g) { return new GameObjectContainer(this.goc, g); }
}
