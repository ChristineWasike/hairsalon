import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class ClientTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test

    public void Client_instantiatesCorrectly_true() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Victor", myStylist.getId());
        myClient.save();
        assertEquals(true, myClient instanceof Client);
    }

    @Test
    public void Client_instantiatesWithDescription_String() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Shampooist", myStylist.getId());
        myClient.save();
        assertEquals("Shampooist", myClient.getDescription());
    }

    @Test
    public void isCompleted_isFalseAfterInstantiation_false() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Shampooist", myStylist.getId());
        myClient.save();
        assertEquals(false, myClient.isCompleted());
    }

    @Test
    public void getCreatedAt_instantiatesWithCurrentTime_today() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Shampooist", myStylist.getId());
        myClient.save();
        assertEquals(LocalDateTime.now().getDayOfWeek(), myClient.getCreatedAt().getDayOfWeek());
    }

    @Test
    public void all_returnsAllInstancesOfClient_true() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client firstClient = new Client("Shampooist", myStylist.getId());
        firstClient.save();
        Client secondClient = new Client("Buy groceries", myStylist.getId());
        secondClient.save();
        assertEquals(true, Client.all().get(0).equals(firstClient));
        assertEquals(true, Client.all().get(1).equals(secondClient));
    }

    // @Test
    // public void clear_emptiesAllClientsFromArrayList_0() {
    //     Stylist myStylist = new Stylist("Shampooist");
    //     myStylist.save();
    //     Client myClient = new Client("Shampooist", myStylist.getId());
    //     myClient.save();
    //     assertEquals(Client.all().size(), 0);
    // }

    @Test
    public void getId_clientsInstantantiateWithAnID_1() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Shampooist", myStylist.getId());
        myClient.save();
        assertTrue(myClient.getId() > 0);
    }

    @Test
    public void find_returnsClientWitSameId_secondClient() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client firstClient = new Client("Shampooist", myStylist.getId());
        firstClient.save();
        Client secondClient = new Client("Buy groceries", myStylist.getId());
        secondClient.save();
        assertEquals(Client.find(secondClient.getId()), secondClient);
    }

    @Test //Overriding equals()
    public void equals_returnsTrueIfDescriptionsAretheSame() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client firstClient = new Client("Shampooist", myStylist.getId());
        firstClient.save();
        assertTrue(firstClient.equals(Client.find(firstClient.getId())));
    }

    @Test //Saving new objects to the DB
    public void save_returnsTrueIfDescriptionsAretheSame() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Shampooist", myStylist.getId());
        myClient.save();
        assertTrue(Client.all().get(0).equals(myClient));
    }

    @Test //Assigning Unique IDs
    public void save_assignsIdToObject() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Shampooist", myStylist.getId());
        myClient.save();
        Client savedClient = Client.all().get(0);
        assertEquals(myClient.getId(), savedClient.getId());
    }

    @Test
    public void delete_deletesClientFromTheDB_false() {
        Stylist Stylist = new Stylist("Home");
        Stylist.save();
        Client client = new Client("Shampooist", Stylist.getId());
        Client Client2 = new Client("Shampooists", Stylist.getId());
        client.save();
        Client2.save();
        client.delete();
        assertFalse(Client.all().get(0).equals(client));
    }

    @Test
    public void update_updatesClientDescription_true() {
        Stylist Stylist = new Stylist("Home");
        Stylist.save();
        Client myClient = new Client("Shampooist", Stylist.getId());
        myClient.save();
        myClient.update("Take a nap");
        assertEquals("Take a nap", Client.find(myClient.getId()).getDescription());
    }
}