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

//    @Query(value = "SELECT * FROM t20_individual_notification WHERE C20_SEND_TO_USER = (SELECT C14_USERNAME FROM t14_user WHERE C14_USER_ID = :userId ORDER BY c20_create_at DESC)", nativeQuery = true)
//    List<IndividualNotification> findNotificationsByUserId(@Param("userId") int userId);

    @Query(value = "SELECT * FROM t20_individual_notification WHERE C20_ACTOR_ID = :userId ORDER BY c20_create_at ", nativeQuery = true)
    List<IndividualNotification> findNotificationsByUserId(@Param("userId") int userId);

    @Query(value = "SELECT * FROM t20_individual_notification WHERE C20_ACTOR_ID = 1 ORDER BY c20_create_at DESC LIMIT 1000", nativeQuery = true)
    List<IndividualNotification> findNotificationsByUserId();



}
