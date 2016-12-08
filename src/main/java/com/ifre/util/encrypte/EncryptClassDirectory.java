package com.ifre.util.encrypte;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A tool to encrypt all files in a directory. The output files keep the
 * original directory structure.
 * 
 * @author Yan Sun
 * @version 1.0
 */
public class EncryptClassDirectory {

	private String rootOutDir = "";
	private int rootIndex = 0;
	private ClassGuarder protectClass; //= ClassGuarder.getInstance();
	private String fileSeparator = File.separator;
	private String ostype = System.getProperty("os.name");

	public static void main(String[] args) {

		EncryptClassDirectory ecd = null;
		
		String dir = null;
		if (args != null && args.length == 1 || args.length == 2 ) {
			if (args.length == 1)
				ecd = new EncryptClassDirectory();
			else if (args.length == 2) 
				ecd = new EncryptClassDirectory(args[1]);

			dir = args[0];
			ecd.encryptDir(dir);
		} else
			System.out.println("Usage: EncryptClassDirectory \"Directory To Be Ecrypted\" \"seed file name(optional) \"");
		
	}

	
	public EncryptClassDirectory(String seedName) {		
		protectClass = ClassGuarder.getInstance(seedName);
	}


	public EncryptClassDirectory() {
		protectClass = ClassGuarder.getInstance();
	}


	/**
	 * 
	 * @param dirName
	 *            absolute path of the directory to be encrypted.
	 */
	public void encryptDir(String dirName) {
		File rootDir = new File(dirName);
		encryptPath(rootDir, 0);
	}

	private void encryptPath(File path, int level) {

		if (path.isDirectory()) {
			File dir = path;
			System.out.println(path);
			dir.mkdirs();

			String[] str = path.list();
			for (int i = 0; i < str.length; i++) {
				encryptPath(new File(path.getPath() + fileSeparator + str[i]), level + 1);
				
			}
		} else {
			byte[] encodedClass = protectClass.encryptClass(path);
			BufferedOutputStream ostream;
			try {
				ostream = new BufferedOutputStream(new FileOutputStream(path));
				ostream.write(encodedClass);
				ostream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private String getRelativeDir(File mFile) {
		String fullPath = mFile.getPath();
		String[] str = null;
		if (ostype.startsWith("win") || ostype.startsWith("Win") )
			str = fullPath.split(fileSeparator+fileSeparator);
		else 
			str = fullPath.split(fileSeparator);
		StringBuilder sb = new StringBuilder();
		for (int i = rootIndex; i < str.length; i++)
			sb.append(str[i]).append(fileSeparator);
		return sb.toString();
	}

	private String getRootDir(File mFile) {
		String fullPath = mFile.getPath();
		String[] str = null;
		if (ostype.startsWith("win") ||ostype.startsWith("Win")  )
			str = fullPath.split(fileSeparator+fileSeparator);
		else 
			str = fullPath.split(fileSeparator);
		rootIndex = str.length - 1;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length - 1; i++)
			sb.append(str[i]).append(fileSeparator);
		return sb.toString();
	}

}
