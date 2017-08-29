import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Stylist {
    private String name;
    private int id;

    public Stylist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Connecting a Class to the Database
    public static List<Stylist> all() {
        String sql = "SELECT id, name FROM stylists";
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Stylist.class);
        }
    }

    public int getId() {
        return id;
    }

    // public static Stylist find(int id) {

    // }

    public List<Client> getClients() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where stylistId=:id";
            return con.createQuery(sql).addParameter("id", this.id).executeAndFetch(Client.class);
        }
    }

    @Override
    public boolean equals(Object otherStylist) {
        if (!(otherStylist instanceof Stylist)) {
            return false;

        } else {
            Stylist newStylist = (Stylist) otherStylist;
            return this.getName().equals(newStylist.getName()) && this.getId() == newStylist.getId();
        }
    }

    public void save() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO stylists (name) VALUES (:name)";
            this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).executeUpdate().getKey();
        }
    }

    public static Stylist find(int id) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM stylists where id=:id";
            Stylist Stylist = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Stylist.class);
            return Stylist;
        }
    }

    public void delete() {
        String sql = "DELETE FROM stylists where id=:id";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql).addParameter("id", this.getId()).executeUpdate();
        }
    }

    public void update(String description) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "UPDATE clients SET description = :description WHERE id=:id";
            con.createQuery(sql).addParameter("description", description).addParameter("id", id).executeUpdate();
        }
    }
}
