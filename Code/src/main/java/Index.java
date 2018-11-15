import java.util.Map;
import java.util.HashMap;
import java.io.*;

// NOTE: Don't index title_principals as its primary key is two values
public class Index {
  private File table; 
  private final int STEPSIZE; 
  private static Map index = new HashMap<String, Integer>();

  public Index(File table, int STEPSIZE) {
    this.table = table;
    this.STEPSIZE = STEPSIZE;

    try {
      RandomAccessFile raf = new RandomAccessFile(table, "rw");

      // Stop the loop when we have less than 10000 bytes left: Our final index point has been created!
      while( raf.getFilePointer() < raf.length() - STEPSIZE ) { // TODO: Might have to be 10001 or something like it, since we skip a line
        // Seek to the next index position
        raf.seek(raf.getFilePointer() + STEPSIZE);
        // Skip to the next line so we're at the start of a line rather than anywhere in the middle of one, for getting the next ID
        raf.readLine();
        String line = raf.readLine();
        // Get the IMDB ID
        String id = line.split("\t")[0];
        // Adds an entry/offset to the index
        index.put(id, raf.getFilePointer());
        System.out.println( "ID: " + id + " Offset: " + raf.getFilePointer() );
      }
    }
    catch (IOException e) { System.out.println(e); }
  }
  

  // Returns the byte offset that's closest to the tConst/nConst id
  public String lookup(String id) {
    int iid = DBHandler.getDataID(id);
    // Calculate the nearest ID we have the line number indexed for. Two ints divided return an int and not a double - nice!
    if (iid < STEPSIZE) return ""

    // TODO: Unsure how to calculate this
    int nearestIndexedId = (iid / STEPSIZE);

    return (String) index.get(nearestIndexedId);
  }

}
