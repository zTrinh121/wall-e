package com.example.SWP391_Project.repository;

import com.example.SWP391_Project.model.IndividualNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IndividualNotificationRepository extends JpaRepository<IndividualNotification, Integer> {

    Optional<List<IndividualNotification>> findByActor_Id(int id);

    @Query("SELECT n FROM IndividualNotification n WHERE n.actor.id = :actorID AND n.hasSeen = true")
    Optional<List<IndividualNotification>> findByActorIdAndHasSeen(@Param("actorID") int actorID);
}
