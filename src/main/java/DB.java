import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;

public class DB {
  private static URI dbUri;
  public static Sql2o sql2o;

  static {

    try {
      if (System.getenv("DATABASE_URL") == null) {
        dbUri = new URI("postgres://localhost:5432/hhairsalon");
      } else {
        dbUri = new URI(System.getenv("DATABASE_URL"));
      }

      int port = dbUri.getPort();

      sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon", "wasike", "1234");
    } catch (URISyntaxException e) {
    }
  }
}