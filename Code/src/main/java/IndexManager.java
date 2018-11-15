import java.io.FileNotFoundException;
import java.io.IOException;

public class IndexManager {
  private Index[] indexes = new Index[7];
    
  public IndexManager(DBHandler dbh) {

    int i = 0; // We need an index as well for the array
    for (String table : dbh.dbFiles) {
      indexes[i] = new Index(table, 10000, dbh);
      i++;
    }
  }

  // TODO: Write the part that actually looks for the relevant ID and retrieves it if it exists
  public String lookup(int fileIdx, String sid) {

    // Strip tt/nn and turn it into an int
    int intId = HelperLib.convertStringIdtoIntId(sid);

    return indexes[fileIdx].lookup(intId);
  }

  public void recreateIndex(int fileIdx, DBHandler handler) {
    indexes[fileIdx] = new Index(DBHandler.tables[0].path, 10000, handler);
  }
  
}
