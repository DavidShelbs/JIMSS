import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;

public class GetFileInfo {
  public static void main(String[] args) {

  	ArrayList<String> dataEntries = new ArrayList<String>();

  	ArrayList<DataEntry> dataEntries2 = new ArrayList<>();

  	String temp = "";

    try {
      File myObj = new File("log.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
      	temp = myReader.nextLine();
      	String[] entries = temp.split(",");

      	int cap = Integer.parseInt(entries[0]);
      	String date_time = entries[1];

      	dataEntries.add(entries[0]);
      }
      myReader.close();

      for(int i=0; i<dataEntries.size(); i++){
      	System.out.println(dataEntries.get(i));
      }

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public class DataEntry {
  	public int capacity_number;
  	public String date_time;
  }
}
