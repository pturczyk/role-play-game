package io.pturczyk.rpg.engine.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Repository for persisting game progress from {@link GameWorld} in system
 * files.
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
public class FileGameWorldRepository implements GameWorldRepository {
	private final String saveFilePath;

	public FileGameWorldRepository(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	@Override
	public void store(GameWorld world) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(saveFilePath)))) {
			oos.writeObject(world);
		}
	}

	@Override
	public GameWorld retrieve() throws IOException {
		GameWorld world = null;

		File saveFile = new File(saveFilePath);
		if (saveFile.canRead()) {
			try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(saveFile))) {
				world = (GameWorld) oos.readObject();
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			}
		}

		return world;
	}
}
