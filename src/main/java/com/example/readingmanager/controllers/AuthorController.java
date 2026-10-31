package com.example.readingmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.readingmanager.entities.Author;
import com.example.readingmanager.repositories.AuthorRepository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// 著者管理に関するリクエストを処理するコントローラー

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    // 著者追加---------------------------------------------------------------------------------------------------------
    // 著者追加画面を表示
    @GetMapping("/addAuthor")
    public ModelAndView addAuthor(@ModelAttribute("formModel") Author author ,ModelAndView mav) {
        mav.setViewName("redinghtml/rdauthor");
        mav.addObject("title", "著者追加");
        return mav;
    }

    // 著者をDBに登録
    @PostMapping("/addAuthor")
    @Transactional
    public ModelAndView add(@ModelAttribute("formModel") @Validated Author author,BindingResult result, ModelAndView mav) {
        System.out.println(result.getFieldError());

        if(result.hasErrors()) {
            // エラー
            mav.setViewName("redinghtml/rdauthor");
            mav.addObject("title", "著者追加");
            return mav;
        }
        // エラーじゃない
        authorRepository.saveAndFlush(author);
        return new ModelAndView("redirect:/readingMng");
    }
}
