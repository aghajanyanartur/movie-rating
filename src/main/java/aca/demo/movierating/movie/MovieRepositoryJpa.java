package aca.demo.movierating.movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MovieRepositoryJpa implements MovieRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Movie.class, id));
    }

    @Transactional
    public void persist(Movie movie) {
        entityManager.persist(movie);
    }

    @Override
    public void delete(Movie movie) {
        entityManager.remove(movie);
    }

    @Transactional(readOnly = true)
    public List<Movie> search(Genre genre, String title, LocalDate releasedBefore, LocalDate releasedAfter) {
        var query = new StringBuilder();
        query.append("select m from Movie m ");
        class WhereClause<T> {
            final String query;
            final String param;
            final T value;

            WhereClause(String query, String param, T value) {
                this.query = query;
                this.param = param;
                this.value = value;
            }
        }
        var whereClauses = new ArrayList<WhereClause>();
        if (genre != null) {
            whereClauses.add(new WhereClause("m.genre = :genre ", "genre", genre));
        }
        if (title != null) {
            whereClauses.add(new WhereClause("m.title like :title ", "title", "%" + title + "%"));
        }
        if (releasedBefore != null) {
            whereClauses.add(new WhereClause("m.releasedAt <= :releasedBefore ", "releasedBefore", releasedBefore));
        }
        if (releasedAfter != null) {
            whereClauses.add(new WhereClause("m.releasedAt >= :releasedAfter ", "releasedAfter", releasedAfter));
        }
        if (!whereClauses.isEmpty()) {
            query.append("where ");
        }
        var whereClause = whereClauses.stream().map(w -> w.query).collect(Collectors.joining(" and "));
        query.append(whereClause);
        TypedQuery<Movie> managerQuery = entityManager.createQuery(query.toString(), Movie.class);
        whereClauses.forEach(w -> managerQuery.setParameter(w.param, w.value));
        return managerQuery.getResultList();
    }
}