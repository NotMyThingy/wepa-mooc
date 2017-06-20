package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.Actor;
import wad.domain.Movie;
import wad.repository.ActorRepository;
import wad.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    public Iterable<Movie> list() {
        return movieRepository.findAll();
    }

    @Transactional
    public void add(String name, int lengthInMinutes) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setLengthInMinutes(lengthInMinutes);
        movieRepository.save(movie);
    }

    @Transactional
    public void remove(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);
        for (Actor actor : movie.getActors()) {
            actor.getMovies().remove(movie);
        }

        movieRepository.delete(movie);
    }

    public List<Movie> listMoviesWithoutActor(Long actorId) {
        Actor actor = actorRepository.findOne(actorId);
        List<Movie> movies = new ArrayList<>();
        for (Movie movie : movieRepository.findAll()) {
            if (!movie.getActors().contains(actor)) {
                movies.add(movie);
            }
        }

        return movies;
    }

}
