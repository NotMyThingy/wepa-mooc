package wad.hellodao;

import wad.hellodao.dao.Database;
import wad.hellodao.dao.AgentDao;
import wad.hellodao.domain.Agent;
import java.util.UUID;

public class HelloDao {

    public static void main(String[] args) throws Exception {
        
        Database database = new Database("jdbc:h2:./database");
        AgentDao dao = new AgentDao(database);

        for (Agent agent : dao.findAll()) {
            System.out.println(agent);
        }

        System.out.println("");
        Agent a = dao.create(new Agent(UUID.randomUUID().toString().substring(0, 4), UUID.randomUUID().toString().substring(0, 8)));
        for (Agent agent : dao.findAll()) {
            System.out.println(agent);
        }

        System.out.println("");
        dao.delete(a.getId());
        for (Agent agent : dao.findAll()) {
            System.out.println(agent);
        }
    }
}
