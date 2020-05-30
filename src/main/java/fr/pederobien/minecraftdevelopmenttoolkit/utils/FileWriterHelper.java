package fr.pederobien.minecraftdevelopmenttoolkit.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileWriterHelper {

	/**
	 * Verify if the given file exists. If not the file is created. Then write the specified string into the File.
	 * 
	 * @param file    File in which write.
	 * @param toWrite The String to write into the file.
	 * 
	 * @return True if the string was successfully written into the file, false otherwise.
	 */
	public static boolean write(Path path, String toWrite) {
		BufferedWriter writer = null;
		try {
			createNewFile(path);

			// Writing default content
			writer = new BufferedWriter(new FileWriter(path.toFile()));
			writer.write(toWrite);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {

			}
		}
		return false;
	}

	public static File mkdirs(Path path) {
		File file = path.toFile();
		if (!file.exists())
			file.mkdirs();
		return file;
	}

	public static File createNewFile(Path path) throws IOException {
		File file = path.toFile();
		if (!file.exists())
			file.createNewFile();
		return file;
	}
}
