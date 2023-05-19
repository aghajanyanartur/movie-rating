package aca.demo.movierating.movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Primary
public class MovieRepositoryJpa implements MovieRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Movie> findById(Long id) {
        log.debug("Finding movie by id - {}", id);
        return Optional.ofNullable(entityManager.find(Movie.class, id));
    }

    public void persist(Movie movie) {
        log.debug("Adding new movie to movies list with movie - {}", movie);
        if(findById(movie.getId()).isPresent()) {
            entityManager.merge(movie);
        }
        entityManager.persist(movie);
    }

    public void delete(Movie movie) {
        log.debug("Deleting movie from list - {}", movie);
        entityManager.remove(movie);
    }

    public List<Movie> search(Genre genre, String title, LocalDate releasedBefore, LocalDate releasedAfter) {
        var query = new StringBuilder();
        query.append("select m from Movie m where 1=1 ");
        if (genre != null) {
            query.append("and m.genre = :genre ");
        }
        if (title != null) {
            query.append("and m.title like :title ");
        }
        if (releasedBefore != null) {
            query.append("and m.releasedAt <= :releasedBefore ");
        }
        if (releasedAfter != null) {
            query.append("and m.releasedAt >= :releasedAfter ");
        }
        TypedQuery<Movie> managerQuery = entityManager.createQuery(query.toString(), Movie.class);
        if (genre != null) {
            managerQuery.setParameter("genre", genre);
        }
        if (title != null) {
            managerQuery.setParameter("title", "%" + title + "%");
        }
        if (releasedBefore != null) {
            managerQuery.setParameter("releasedBefore", releasedBefore);
        }
        if (releasedAfter != null) {
            managerQuery.setParameter("releasedAfter", releasedAfter);
        }
        return managerQuery.getResultList();
    }
}