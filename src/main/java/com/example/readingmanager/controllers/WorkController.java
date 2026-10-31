package com.example.readingmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.readingmanager.dto.ReadingLogSearchDto;
import com.example.readingmanager.entities.Author;
import com.example.readingmanager.entities.Work;
import com.example.readingmanager.repositories.AuthorRepository;
import com.example.readingmanager.repositories.WorkRepository;
import com.example.readingmanager.specifications.WorkSpecifications;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// 作品（書籍など）の検索に関するリクエストを処理するコントローラー
@Controller
@RequiredArgsConstructor
public class WorkController {

    private final AuthorRepository authorRepository;
    private final WorkRepository workRepository;

    // 作品検索画面を表示する
    @GetMapping("/searchWork")
    public ModelAndView searchWork(ModelAndView mav) {
        mav.setViewName("redinghtml/rdsearch");
        mav.addObject("title", "作品検索");
        mav.addObject("formModel", new ReadingLogSearchDto());

        // 検索条件のプルダウン用に著者リストを取得
        List<Author> ath = authorRepository.findAll();
        mav.addObject("authorList", ath);
        return mav;
    }

    // 検索条件に基づいて作品を検索し、結果を表示する
    @PostMapping("/searchWork")
    @Transactional
    public ModelAndView search(@ModelAttribute("formModel") @Validated ReadingLogSearchDto dto, BindingResult result, ModelAndView mav) {
        // 画面のプルダウン再描画用に全著者リストを取得
        List<Author> authorList = authorRepository.findAll();

        // 入力チェックエラーがある場合
        if(result.hasErrors()) {
            mav.setViewName("redinghtml/rdsearch");
            mav.addObject("title", "作品検索");
            // エラーで元の画面に戻る際も、プルダウン用のリストを渡す
            mav.addObject("authorList", authorList);
            return mav;
        }

        // Specificationを用いた動的検索条件の組み立て（Criteria API）
        Specification<Work> spec = Specification
            .where(WorkSpecifications.titleContains(dto.getTitle()))
            .and(WorkSpecifications.authorIdEquals(dto.getAuthorId()))
            .and(WorkSpecifications.isReadStatus(dto.getReadStatus()));

        // 条件に合致する作品リストを取得
        List<Work> searchResult = workRepository.findAll(spec);

        mav.setViewName("redinghtml/rdsearch");
        mav.addObject("title", "作品検索");
        mav.addObject("search", "検索結果");
        mav.addObject("authorList", authorList);
        mav.addObject("data", searchResult);

        return mav;
    }
}
