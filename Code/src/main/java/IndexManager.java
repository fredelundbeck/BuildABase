import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class IndexManager {
  private Index[] indexes = new Index[7];
  private DBHandler dbh;
  private final int STEPSIZE = 50000;
    
  public IndexManager(DBHandler dbh) {

    this.dbh = dbh;

    int i = 0; // We need an index as well for the array
    for (File table : dbh.dbFiles) {
      indexes[i] = new Index(table, STEPSIZE);
      i++;
    }
  }

  // TODO: Write the part that actually looks for the relevant ID and retrieves it if it exists
  public String lookup(String sid, File tableToLookup) {

    int i = 0;
    for (File table : dbh.dbFiles) {
      if (table == tableToLookup) {
        break;
      }
      i++;
    }

    return indexes[i].lookup(sid);
  }

  public void recreateIndex(File tableToUpdate) {

    int i = 0;
    for (File table : dbh.dbFiles) {
      if (table == tableToUpdate) {
        indexes[i] = new Index(tableToUpdate, STEPSIZE);
        break;
      }
      i++;
    }
  }
  
}
