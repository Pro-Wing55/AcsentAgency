package ascentAgency;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Text {

	public static void main(String[] args) throws IOException {
		createFile();
	}
	
	public static void createFile() throws IOException {
		 File myObj = new File("uploadFile/xyz.txt");
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName());
	      } else {
	        System.out.println("File already exists.");
	      }
		
	}

}
