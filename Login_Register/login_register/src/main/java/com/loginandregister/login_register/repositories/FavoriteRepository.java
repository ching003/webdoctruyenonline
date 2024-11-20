package com.loginandregister.login_register.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loginandregister.login_register.model.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>{
    boolean existsByUserIdAndStoryId(Long userId, Long storyId);

    void deleteByUserIdAndStoryId(Long userId, Long storyId);

    @Query("SELECT f.storyId FROM Favorite f WHERE f.userId = :userId")
    List<Long> findStoryIdsByUserId(@Param("userId") Long userId);
}
