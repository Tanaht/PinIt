package fr.istic.m2.taa.pinit.repository;

import fr.istic.m2.taa.pinit.domain.Activity;
import fr.istic.m2.taa.pinit.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByNameActivity(String nameActivity);

    Optional<Activity> findById(long activityUser);

    List<Activity> findAll();


}
