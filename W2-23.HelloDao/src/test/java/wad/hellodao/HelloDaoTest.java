package wad.hellodao;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import wad.hellodao.dao.AgentDao;
import wad.hellodao.dao.Database;
import wad.hellodao.domain.Agent;

@Points("23")
public class HelloDaoTest {

    private Database database;
    private AgentDao agentDao;
    private String databaseName;

    @Before
    public void setUp() throws Exception {
        this.databaseName = "tmp" + UUID.randomUUID().toString().substring(0, 6);
        this.database = new Database("jdbc:h2:./" + this.databaseName);
        this.agentDao = new AgentDao(this.database);
    }

    @After
    public void tearDown() throws Exception {
        if (new File(databaseName + ".mv.db").exists()) {
            new File(databaseName + ".mv.db").delete();
        }

        if (new File(databaseName + ".trace.db").exists()) {
            new File(databaseName + ".trace.db").delete();
        }
    }

    @Test
    public void agentReturnedOnCreate() throws Exception {

        String details = UUID.randomUUID().toString();

        Agent agent = new Agent(details.substring(0, 4), details.substring(0, 6));
        Agent storedAgent = this.agentDao.create(agent);
        assertNotNull(storedAgent);

        assertEquals(agent.getId(), storedAgent.getId());
        assertEquals(agent.getName(), storedAgent.getName());
    }

    @Test
    public void createdAgentCanBeFound() throws Exception {
        String details = UUID.randomUUID().toString();

        Agent agent = new Agent(details.substring(0, 4), details.substring(0, 6));
        this.agentDao.create(agent);
        Agent foundAgent = this.agentDao.findOne(details.substring(0, 4));
        assertNotNull(foundAgent);

        assertEquals(agent.getId(), foundAgent.getId());
        assertEquals(agent.getName(), foundAgent.getName());
    }

    @Test
    public void addingToDatabaseIncreasesAgentCount() throws Exception {
        List<Agent> agents = this.agentDao.findAll();
        assertNotNull(agents);

        String details = UUID.randomUUID().toString();

        Agent agent = new Agent(details.substring(0, 4), details.substring(0, 6));
        this.agentDao.create(agent);

        assertEquals(agents.size() + 1, this.agentDao.findAll().size());
    }

    @Test
    public void allAddedAgentsFoundFromDatabase() throws Exception {
        List<Agent> agentsBeforeAdd = this.agentDao.findAll();

        List<Agent> agents = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String details = UUID.randomUUID().toString();

            Agent agent = new Agent(details.substring(0, 4), details.substring(0, 6));
            this.agentDao.create(agent);
            agents.add(agent);
        }

        assertEquals(agentsBeforeAdd.size() + 5, this.agentDao.findAll().size());

        for (Agent agent : agents) {
            Agent fromDatabase = this.agentDao.findOne(agent.getId());
            assertEquals(agent.getName(), fromDatabase.getName());
        }
    }

    @Test
    public void canDeleteAllFromDatabase() throws Exception {
        for (int i = 0; i < 5 + new Random().nextInt(10); i++) {
            String details = UUID.randomUUID().toString();

            Agent agent = new Agent(details.substring(0, 4), details.substring(0, 6));
            this.agentDao.create(agent);
        }

        assertTrue(this.agentDao.findAll().size() > 0);

        for (Agent agent : this.agentDao.findAll()) {
            this.agentDao.delete(agent.getId());
        }

        assertEquals(0, this.agentDao.findAll().size());
    }

    @Test
    public void canDeleteOneFromDatabase() throws Exception {
        for (int i = 0; i < 5 + new Random().nextInt(10); i++) {
            String details = UUID.randomUUID().toString();

            Agent agent = new Agent(details.substring(0, 4), details.substring(0, 6));
            this.agentDao.create(agent);
        }

        List<Agent> agents = this.agentDao.findAll();
        assertTrue(agents.size() > 0);
        Collections.shuffle(agents);

        Agent removed = agents.remove(0);
        this.agentDao.delete(removed.getId());

        assertEquals(agents.size(), this.agentDao.findAll().size());

        for (Agent agent : this.agentDao.findAll()) {
            assertFalse(removed.getName().equals(agent.getName()));
        }
    }

    @Test
    public void agentNameCanBeUpdated() throws Exception {
        for (int i = 0; i < 5 + new Random().nextInt(10); i++) {
            String details = UUID.randomUUID().toString();

            Agent agent = new Agent(details.substring(0, 4), details.substring(0, 6));
            String oldName = agent.getName();
            agent = this.agentDao.create(agent);

            assertNotNull(agent);
            assertEquals(oldName, agent.getName());

            String newName = details.substring(4, 10);
            agent.setName(newName);
            this.agentDao.update(agent.getId(), agent);

            assertNotNull(this.agentDao.findOne(agent.getId()));
            assertEquals(newName, this.agentDao.findOne(agent.getId()).getName());
        }
    }
}
