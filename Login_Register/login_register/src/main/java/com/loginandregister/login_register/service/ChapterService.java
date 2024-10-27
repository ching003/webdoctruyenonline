package com.loginandregister.login_register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginandregister.login_register.model.Chapter;
import com.loginandregister.login_register.repositories.ChapterRepository;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;
    public void save(Chapter chapter){
        chapterRepository.save(chapter);
    }
}
