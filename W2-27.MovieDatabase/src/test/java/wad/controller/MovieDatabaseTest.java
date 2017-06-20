package wad.controller;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import wad.domain.Actor;
import wad.domain.Movie;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieDatabaseTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Points("27.1")
    public void testActors() throws Exception {
        // listaus
        MvcResult res = mockMvc.perform(get("/actors"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("actors"))
                .andExpect(view().name("actors"))
                .andReturn();

        List<Actor> actors = new ArrayList<>((Collection<Actor>) res.getModelAndView().getModel().get("actors"));
        int actorCount = actors.size();

        // lisäys
        String rnd = UUID.randomUUID().toString().substring(0, 4);
        String name = "van damme " + rnd;
        mockMvc.perform(post("/actors").param("name", name))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        res = mockMvc.perform(get("/actors"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("actors"))
                .andExpect(view().name("actors"))
                .andReturn();

        actors = new ArrayList<>((Collection<Actor>) res.getModelAndView().getModel().get("actors"));
        assertTrue("When one actor is added, the actor count should increase only by one.", actorCount + 1 == actors.size());
        boolean found = false;
        Long id = -1L;
        for (Actor actor : actors) {
            if (actor.getName() == null) {
                continue;
            }

            if (actor.getName().equals(name)) {
                id = actor.getId();
                found = true;
                break;
            }
        }

        assertTrue("When a POST request is made to the address /actors with a parameter 'name', a new actor should be created. Now the actor was not created, or it was not found in the response to a GET query to /actors.", found);

        // poistetaan ja tarkistetaan että poistettu
        mockMvc.perform(delete("/actors/" + id))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        res = mockMvc.perform(get("/actors"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("actors"))
                .andExpect(view().name("actors"))
                .andReturn();

        actors = new ArrayList<>((Collection<Actor>) res.getModelAndView().getModel().get("actors"));
        assertTrue("When one actor is added, and then removed, the overall actor count should not change.", actorCount == actors.size());

        found = false;
        for (Actor actor : actors) {
            if (actor.getName() == null) {
                continue;
            }

            if (actor.getName().equals(name)) {
                found = true;
                break;
            }
        }

        assertFalse("When a DELETE-request is made to /actors/{id}, where {id} is the identifier of an actor, that actor should be removed. Now the actor was not removed (it was available in the listing from /actors).", found);
    }

    @Test
    @Points("27.2")
    public void testMovies() throws Exception {
        // listaus
        MvcResult res = mockMvc.perform(get("/movies"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("movies"))
                .andReturn();

        List<Movie> movies = new ArrayList<>((Collection<Movie>) res.getModelAndView().getModel().get("movies"));
        int movieCount = movies.size();

        // lisäys
        String rnd = UUID.randomUUID().toString().substring(0, 4);
        int pituus = new Random().nextInt(20) + 10;
        String name = "bloodsport " + rnd;

        Long movieId = createMovie(name, pituus);

        res = mockMvc.perform(get("/movies"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("movies"))
                .andReturn();

        movies = new ArrayList<>((Collection<Movie>) res.getModelAndView().getModel().get("movies"));
        assertTrue("When one movie is added, the movie count should increase only by one.", movieCount + 1 == movies.size());
        boolean found = false;
        Long id = -1L;
        for (Movie movie : movies) {
            if (movie.getName() == null) {
                continue;
            }

            if (movie.getLengthInMinutes() == null) {
                continue;
            }

            if (movie.getName().equals(name) && movie.getLengthInMinutes() == pituus) {
                id = movie.getId();
                found = true;
                break;
            }
        }

        assertTrue("When a POST request is made to /movies with parameters 'name' and 'lengthInMinutes', a new movie should be created with those values. Now the movie was not created, or it was not found in the listing at /movies.", found);

        // poistetaan ja tarkistetaan että poistettu
        mockMvc.perform(delete("/movies/" + id))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        res = mockMvc.perform(get("/movies"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("movies"))
                .andReturn();

        movies = new ArrayList<>((Collection<Movie>) res.getModelAndView().getModel().get("movies"));
        assertTrue("When a movie is first added and then removed, the overall movie count should not change.", movieCount == movies.size());

        found = false;
        for (Movie movie : movies) {
            if (movie.getName() == null) {
                continue;
            }

            if (movie.getName().equals(name)) {
                found = true;
                break;
            }
        }

        assertFalse("When a DELETE request is made to /movies/{id}, where {id} is the identifier for a movie, that movie should be removed. Now the movie was not removed (it was found in the listing at /movies).", found);
    }

    @Test
    @Points("27.3 27.4")
    public void testAssigningMoviesAndRetrievingSingleUsers() throws Exception {

        String name = "van damme " + UUID.randomUUID().toString().substring(0, 4);
        Long actorId = createActor(name);
        // actor created, lets see if we can retrieve it
        MvcResult res = mockMvc.perform(get("/actors/" + actorId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("actor"))
                .andExpect(view().name("actor"))
                .andReturn();

        Actor actor = (Actor) res.getModelAndView().getModel().get("actor");
        assertEquals("When a request is made to /actors/{actorId}, the actor-attribute in the model should be the one that can be found with the id 'actorId'.", name, actor.getName());
        assertEquals("When a new actor has been created, he or she should not have any movies at the beginning.", 0, actor.getMovies().size());

        int pituus = new Random().nextInt(20) + 10;
        name = "bloodsport " + UUID.randomUUID().toString().substring(0, 4);

        Long bloodsportId = createMovie(name, pituus);

        name = "kickboxer " + UUID.randomUUID().toString().substring(0, 4);
        Long kickboxerId = createMovie(name, pituus);

        mockMvc.perform(post("/actors/" + actorId + "/movies").param("movieId", "" + bloodsportId));

        res = mockMvc.perform(get("/actors/" + actorId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("actor"))
                .andExpect(view().name("actor"))
                .andReturn();

        actor = (Actor) res.getModelAndView().getModel().get("actor");
        assertEquals("When a request is made to /actors/{actorId}/movies with a request parameter 'movieId', the movie should be added to the actor whose id is 'actorId'.", 1, actor.getMovies().size());
        assertEquals("When a movie is added to an actor, the actor should also be added to the movie.", 1, actor.getMovies().get(0).getActors().size());

        // poistetaan elokuva
        mockMvc.perform(delete("/movies/" + bloodsportId))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        res = mockMvc.perform(get("/actors/" + actorId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("actor"))
                .andExpect(view().name("actor"))
                .andReturn();

        actor = (Actor) res.getModelAndView().getModel().get("actor");
        assertEquals("When a movie is removed, the reference to the movie should also be removed from the actors.", 0, actor.getMovies().size());

    }

    private Long createMovie(String name, Integer length) throws Exception {
        mockMvc.perform(post("/movies").param("name", name).param("lengthInMinutes", "" + length))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MvcResult res = mockMvc.perform(get("/movies"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("movies"))
                .andExpect(view().name("movies"))
                .andReturn();

        List<Movie> movies = new ArrayList<>((Collection<Movie>) res.getModelAndView().getModel().get("movies"));

        for (Movie movie : movies) {
            if (movie.getName() == null) {
                continue;
            }

            if (movie.getLengthInMinutes() == null) {
                continue;
            }

            if (movie.getName().equals(name) && movie.getLengthInMinutes() == length) {
                if (movie.getActors().size() > 0) {
                    fail("When a new movie has been created, it should not have any actors at the beginning. In addition, the movie length should be set as requested.");
                }

                return movie.getId();
            }
        }

        return -1L;
    }

    private Long createActor(String name) throws Exception {
        mockMvc.perform(post("/actors").param("name", name))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MvcResult res = mockMvc.perform(get("/actors"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("actors"))
                .andExpect(view().name("actors"))
                .andReturn();

        List<Actor> actors = new ArrayList<>((Collection<Actor>) res.getModelAndView().getModel().get("actors"));

        for (Actor actor : actors) {
            if (actor.getName() == null) {
                continue;
            }

            if (actor.getName().equals(name)) {
                if (actor.getMovies().size() > 0) {
                    fail("When a new actor has been created, it should not have any movies associated with it at the beginning.");
                }

                return actor.getId();
            }
        }

        return -1L;
    }

}
