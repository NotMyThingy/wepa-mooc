package wad.hellodao.dao;

import wad.hellodao.domain.Agent;
import java.sql.SQLException;
import java.util.List;

public class AgentDao implements Dao<Agent, String> {

    private Database database;

    public AgentDao(Database database) {
        this.database = database;
    }

    @Override
    public Agent create(Agent t) throws SQLException {
        database.update("INSERT INTO Agent VALUES(?,?)", t.getId(), t.getName());
        return findOne(t.getId());
    }

    @Override
    public Agent findOne(String key) throws SQLException {
        return (Agent) database.queryAndCollect("SELECT * FROM Agent WHERE id = ?",
                rs -> new Agent(rs.getString("id"), rs.getString("name")), key).get(0);
    }

    @Override
    public List<Agent> findAll() throws SQLException {
        return database.queryAndCollect("SELECT * FROM Agent",
                rs -> new Agent(rs.getString("id"), rs.getString("name")));
    }

    @Override
    public void delete(String key) throws SQLException {
        database.update("DELETE FROM Agent WHERE id = ?", key);
    }

    @Override
    public void update(String key, Agent agent) throws SQLException {
        database.update("UPDATE Agent SET name = ? WHERE id =?", agent.getName(), key);
    }
}
