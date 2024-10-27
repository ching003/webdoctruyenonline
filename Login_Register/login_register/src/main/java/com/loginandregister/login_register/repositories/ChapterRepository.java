package com.loginandregister.login_register.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loginandregister.login_register.model.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
