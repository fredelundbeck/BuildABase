import java.util.Map;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;

// NOTE: Don't index title_principals as its primary key is two values
public class Index {
  private File file; 
  private int stepSize; 
  private static Map index = new HashMap<String, Integer>();

  public Index(String file, int stepSize, DBHandler handler) {
    this.file = file;
    this.stepSize = stepSize;

    RandomAccessFile raf = new RandomAccessFile(file, "rw");
    String line = "";

    // Stop the loop when we have less than 10000 bytes left: Our final index point has been created!
    while( raf.getFilePointer() < raf.length() - 10000 ) { // TODO: Might have to be 10001 or something like it, since we skip a line
      long currentPos = raf.getFilePointer();
      // Seek to the next index position
      raf.seek(currentPos + stepSize);
      // Skip to the next line so we're at the start of a line rather than anywhere in the middle of one, for getting the next ID
      raf.readLine();
      // Adds an entry/offset to the index
      index.put(!imdbIDHere!, raf.currentPos());
    }
  }

  // Returns the byte offset that's closest to the tConst/nConst id
  // TODO: Must also convert the string to ID and get the nearest offset lower than the requested ID
  public String lookup(String id) {
    // Calculate the nearest ID we have the line number indexed for. Two ints divided return an int and not a double - nice!
    int nearestIndexedId = (id / stepSize) * stepSize;

    return (String) index.get(nearestIndexedId);
  }

}
