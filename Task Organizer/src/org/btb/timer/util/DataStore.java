package org.btb.timer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.btb.timer.data.TaskO;

/**
 * Utility class for storing and loading data.
 * @author Stanislav Milev
 * @date 16.10.2008
 */
public class DataStore {

	//TODO delete if not used
	
	/**
	 * Saves an object into a file.
	 * 
	 * @param path	the path of the file
	 * @param timerO the object that will be stored in the file
	 */
	public static void saveObject(String path, TaskO timerO) {
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(new File(path)));
			out.writeObject(timerO);
			System.out.println("Saving");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets an object from a file.
	 * 
	 * @param path the path of the file
	 * @return object the object from the file
	 * @throws IOException thrown when the specified file is not found
	 */
	public static Object getObject(String path) throws IOException {
		ObjectInputStream in 	= null;
		Object result 			= null;
		
		try {
			in = new ObjectInputStream(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			throw new IOException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		try {
			result = in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			System.out.println("There is no correct data in the file.");
			return null;
		} finally {
			in.close();
		}
		
		return result;
	}
	
}
