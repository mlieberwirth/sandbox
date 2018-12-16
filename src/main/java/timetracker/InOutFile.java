package main.java.timetracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public class InOutFile {

	private String[] _files;

	private String _directory;
	
	public InOutFile(String dir) {
		_directory = dir;
	}
	
	public void readFiles(){
		Reader f = null;
		
		try {

			f = new FileReader(_directory+"load.txt");
			BufferedReader fileIn = new BufferedReader(f);

			int count = 0;

			while ((fileIn.readLine()) != null) {
				count++;
			}
			f.close();

			_files = new String[count];

			f = new FileReader(_directory+"load.txt");
			fileIn = new BufferedReader(f);
			
			for (int j = 0; j < count; j++) {
				_files[j] = fileIn.readLine();
			}

			f.close();
			
		} catch (Exception e) {
			System.out.println(e);

		}
	}
	
	public String[] getFiles(){
		return _files;
	}
	
	public void addFile(String file){
		
		readFiles();
		
		Writer fw = null;

		try {
			fw = new FileWriter(_directory+"load.txt");

			for (int i = 0; i < _files.length; i++) {
				fw.write(_files[i]);
				fw.append('\n');
			}
			fw.write(file);
			fw.append('\n');

			fw.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
}
