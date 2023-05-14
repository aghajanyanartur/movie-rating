package aca.demo.movierating.review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@Primary
public class ReviewRepositoryJpa implements ReviewRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Optional<Review> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Review.class, id));
    }

    @Transactional
    public List<Review> search(String description, Instant updatedBefore, Instant updatedAfter, Long userId, double ratingHigherThan, double ratingLowerThan) {
        var query = new StringBuilder();
        query.append("select r from Review r ");
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
        if (description != null) {
            whereClauses.add(new WhereClause("r.description like :description ", "description", "%" + description + "%"));
        }
        if (updatedBefore != null) {
            whereClauses.add(new WhereClause("r.updatedBefore <= :updatedBefore ", "updatedBefore", updatedBefore));
        }
        if (updatedAfter != null) {
            whereClauses.add(new WhereClause("r.updatedAfter >= :updatedAfter ", "updatedAfter", updatedAfter));
        }
        if (userId != null) {
            whereClauses.add(new WhereClause("r.userId = :userId ", "userId", userId));
        }
        whereClauses.add(new WhereClause("r.rating >= :ratingHigherThan ", "ratingHigherThan", ratingHigherThan));
        whereClauses.add(new WhereClause("r.rating <= :ratingLowerThan ", "ratingLowerThan", ratingLowerThan));

        query.append("where ");

        var whereClause = whereClauses.stream().map(w -> w.query).collect(Collectors.joining(" and "));
        query.append(whereClause);
        TypedQuery<Review> managerQuery = entityManager.createQuery(query.toString(), Review.class);
        whereClauses.forEach(w -> managerQuery.setParameter(w.param, w.value));
        return managerQuery.getResultList();
    }

    @Transactional
    public void persist(Review review) {
        entityManager.persist(review);
    }

    @Transactional
    public void delete(Review review) {
        entityManager.remove(review);
    }
}