package aca.demo.movierating.review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Primary
public class ReviewRepositoryJpa implements ReviewRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Review> findById(Long id) {
        log.debug("Finding review by reviewId: {}", id);
        return Optional.ofNullable(entityManager.find(Review.class, id));
    }

    public List<Review> search(String description, Instant updatedBefore, Instant updatedAfter, Long userId, double ratingHigherThan, double ratingLowerThan) {
        log.debug("Finding reviews by parameters - description: {}, updatedBefore: {}, updatedAfter: {}, userId: {}, ratingHigherThan: {}, ratingLowerThan: {}",
                description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
        var query = new StringBuilder();
        query.append("select r from Review r where 1=1");

        if (description != null) {
            query.append("r.description like :description ");
        }
        if (updatedBefore != null) {
            query.append("r.updatedBefore <= :updatedBefore ");
        }
        if (updatedAfter != null) {
            query.append("r.updatedAfter >= :updatedAfter ");
        }
        if (userId != null) {
            query.append("r.userId = :userId ");
        }
        query.append("r.rating >= :ratingHigherThan ");
        query.append("r.rating <= :ratingLowerThan ");

        TypedQuery<Review> managerQuery = entityManager.createQuery(query.toString(), Review.class);
        if (description != null) {
            managerQuery.setParameter("description", "%" + description + "%");
        }
        if (updatedBefore != null) {
            managerQuery.setParameter("updatedBefore", updatedBefore);
        }
        if (updatedAfter != null) {
            managerQuery.setParameter("updatedAfter", updatedAfter);
        }
        if (userId != null) {
            managerQuery.setParameter("userId", userId);
        }

        managerQuery.setParameter("ratingHigherThan", ratingHigherThan);
        managerQuery.setParameter("ratingLowerThan", ratingLowerThan);

        return managerQuery.getResultList();
    }

    public void persist(Review review) {
        log.debug("Adding new review to reviews list - {}", review);
        entityManager.persist(review);
    }

    public void delete(Review review) {
        log.debug("Deleting review from list - {}", review);
        entityManager.remove(review);
    }
}