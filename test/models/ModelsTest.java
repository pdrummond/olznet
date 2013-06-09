package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }
    
    @Test
    public void createAndRetrieveUser() {
        new OlzUser("bob@gmail.com", "Bob", "secret").save();
        OlzUser bob = OlzUser.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
    }
    
    @Test
    public void tryAuthenticateUser() {
        new OlzUser("bob@gmail.com", "Bob", "secret").save();
        
        assertNotNull(OlzUser.authenticate("bob@gmail.com", "secret"));
        assertNull(OlzUser.authenticate("bob@gmail.com", "badpassword"));
        assertNull(OlzUser.authenticate("tom@gmail.com", "secret"));
    }
    
    @Test
    public void findProjectsInvolving() {
        new OlzUser("bob@gmail.com", "Bob", "secret").save();
        new OlzUser("jane@gmail.com", "Jane", "secret").save();

        OlzList.create("OpenLoopz Net", "olznet", "bob@gmail.com");
        OlzList.create("OpenLoopz Android", "olzandroid", "jane@gmail.com");

        List<OlzList> results = OlzList.findInvolving("bob@gmail.com");
        assertEquals(1, results.size());
        assertEquals("OpenLoopz Net", results.get(0).name);
    }
    
    @Test
    public void findActionsInvolving() {
        OlzUser bob = new OlzUser("bob@gmail.com", "Bob", "secret");
        bob.save();

        OlzList olzList = OlzList.create("OpenLoopz Net", "olznet", "bob@gmail.com");
        OlzAction t1 = new OlzAction();
        t1.title = "Implement olznet ASAP";
        t1.assignedTo = bob;
        t1.done = true;
        t1.save();

        OlzAction t2 = new OlzAction();
        t2.title = "Write OpenLoopz User guide";        
        t2.olzList = olzList;
        t2.save();

        List<OlzAction> results = OlzAction.findActionInvolving("bob@gmail.com");
        assertEquals(1, results.size());
        assertEquals("Write OpenLoopz User guide", results.get(0).title);
    }
}