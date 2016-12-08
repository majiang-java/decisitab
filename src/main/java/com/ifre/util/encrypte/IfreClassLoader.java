package com.ifre.util.encrypte;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class IfreClassLoader extends ClassLoader {
	//private String fileSeparator = File.separator;
	private String fileSeparator = "/";
	public IfreClassLoader(ClassLoader parent) {
		super(parent);
	}

	private String[] classList = new String[]{"ScoreModelObj"};

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		Class<?> clazz = null;
		clazz = findLoadedClass(name);
		// Loaded before
		if (clazz != null)
			return clazz;


		if (inClassList(name)) {
			return getClass(name);
		}
		return super.loadClass(name);
	}

	private boolean inClassList(String name) {
		for (String classname : classList) {
			if (name.contains(classname))
				return true;
		}
		return false;
	}

	/**
	 * Loads a given class from .class file just like the default ClassLoader.
	 * 
	 * @param name
	 *            Full class name
	 */
	private Class<?> getClass(String name) throws ClassNotFoundException {
		// We are getting a name that looks like
		// com.ifre.util.Printer
		// and we have to convert it into the .class file name
		// like com/ifre/util/Printer.class
		//String file = name.replace('.', File.separatorChar) + ".class";
		String file = name.replace('.', '/') + ".class";
		byte[] b = null;
		try {
			// This loads the byte code data from the file
			b = loadClassData(file);
			// defineClass is inherited from the ClassLoader class
			// and converts the byte array into a Class
			Class<?> c = defineClass(name, b, 0, b.length);
			resolveClass(c);
			return c;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Loads a given file (presumably .class) into a byte array.
	 * 
	 * @param name
	 *            File name to load
	 * @return Byte array read from the file
	 * @throws IOException
	 *             Is thrown when there was some problem reading the file
	 */
	private byte[] loadClassData(String name) throws IOException {
		// Opening the file
		InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		int size = stream.available();
		byte buff[] = new byte[size];
		DataInputStream in = new DataInputStream(stream);
		// Reading the binary data
		in.readFully(buff);
		in.close();
		ClassGuarder cg = ClassGuarder.getInstance("com"+fileSeparator+"ifre"+fileSeparator+"util"+fileSeparator+"encrypte"+fileSeparator+"IfreClassLoader.class");		
		buff = cg.decryptClass(buff);
		return buff;
	}
}
