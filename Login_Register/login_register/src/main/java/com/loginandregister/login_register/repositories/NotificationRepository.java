package com.loginandregister.login_register.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loginandregister.login_register.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    Notification findByUserIdAndStoryId(Long userId, Long storyId);
    @Query("SELECT n FROM Notification n WHERE n.userId = :userId ORDER BY n.createdAt DESC")
    List<Notification> findRecentByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT n.storyId FROM Notification n WHERE n.userId = :userId")
    List<Long> findStoryIdsByUserId(@Param("userId") Long userId);
}
