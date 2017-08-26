import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class StylistTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void stylist_instantiatesCorrectly_true() {
        Stylist testStylist = new Stylist("Shampooist");
        assertEquals(true, testStylist instanceof Stylist);
    }

    @Test
    public void getName_StylistInstantiatesWithName_Shampooist() {
        Stylist testStylist = new Stylist("Shampooist");
        assertEquals("Shampooist", testStylist.getName());
    }

    @Test
    public void all_returnsAllInstancesOfStylist_true() {
        Stylist firstStylist = new Stylist("Shampooist");
        firstStylist.save();
        Stylist secondStylist = new Stylist("Work");
        secondStylist.save();
        assertEquals(true, Stylist.all().get(0).equals(firstStylist));
        assertEquals(true, Stylist.all().get(1).equals(secondStylist));
    }

    @Test
    public void clear_emptiesAllCategoriesFromList_0() {
        Stylist testStylist = new Stylist("Shampooist");
        assertEquals(Stylist.all().size(), 0);
    }

    @Test
    public void getId_CategoriesInstantiateWithId_1() {
        Stylist testStylist = new Stylist("Shampooist");
        testStylist.save();
        assertTrue(testStylist.getId() > 0);
    }

    @Test
    public void find_returnsStylistWithSameId_secondStylist() {
        Stylist firstStylist = new Stylist("Shampooist");
        firstStylist.save();
        Stylist secondStylist = new Stylist("Work");
        secondStylist.save();
        assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
    }

    @Test //comparing Stylist objects using the .equals() method
    public void equals_returnsTrueIfNamesAretheSae() {
        Stylist firstStylist = new Stylist("Shampooist");
        Stylist secondStylist = new Stylist("Shampooist");
        assertTrue(firstStylist.equals(secondStylist));
    }

    @Test
    public void save_savesIntoDatabase_true() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        assertTrue(Stylist.all().get(0).equals(myStylist));
    }

    @Test
    public void save_assignsIdToObject() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Stylist savedStylist = Stylist.all().get(0);
        assertEquals(myStylist.getId(), savedStylist.getId());
    }

    @Test
    public void save_savesStylistIdIntoDB_true() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client myClient = new Client("Mow the lawn", myStylist.getId());
        myClient.save();
        Client savedClient = Client.find(myClient.getId());
        assertEquals(savedClient.getStylistId(), myStylist.getId());
    }

    @Test
    public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
        Stylist myStylist = new Stylist("Shampooist");
        myStylist.save();
        Client firstClient = new Client("Mow the lawn", myStylist.getId());
        firstClient.save();
        Client secondClient = new Client("Do the dishes", myStylist.getId());
        secondClient.save();
        Client[] clients = new Client[] { firstClient, secondClient };
        assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
    }

}