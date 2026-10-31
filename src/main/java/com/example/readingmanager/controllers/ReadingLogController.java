package com.example.readingmanager.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.readingmanager.entities.Author;
import com.example.readingmanager.entities.ReadingLog;
import com.example.readingmanager.entities.Work;
import com.example.readingmanager.repositories.ReadingLogRepository;
import com.example.readingmanager.repositories.WorkRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

// 読書記録に関するリクエストを処理するコントローラー
@Controller
@RequiredArgsConstructor
public class ReadingLogController {

    private final ReadingLogRepository readingLogRepository;
    private final WorkRepository workRepository;

    // ホーム画面
    @GetMapping("/readingMng")
    public ModelAndView readingMng(@ModelAttribute("formModel") ReadingLog readingLog, ModelAndView mav) {
        mav.setViewName("redinghtml/index");
        mav.addObject("title", "読書記録");

        // 最近の読書記録を5件取得
        List<ReadingLog> recentLogs = readingLogRepository.findTop5ByOrderByFinishedDateDesc();
        mav.addObject("recentLogs", recentLogs);
        // 読了登録用の書籍リストを取得
        mav.addObject("add","新しい読書記録を追加");
        List<Work> worklist = workRepository.findAll();
        mav.addObject("wklist",worklist);
        // 補助機能
        mav.addObject("Assistance", "補助機能");
        return mav;
    }

    // 新しい読書記録を登録する
    @PostMapping("/readingMng")
    @Transactional
    public ModelAndView form(@ModelAttribute("formModel") @Validated ReadingLog readingLog, BindingResult result, ModelAndView mav) {
        // 入力チェックエラーがある場合
        if (result.hasErrors()) {
            mav.setViewName("redinghtml/index");
            mav.addObject("title", "読書記録");
            mav.addObject("data", readingLogRepository.findAll());
            mav.addObject("add", "新しい読書記録を追加");
            mav.addObject("wklist", workRepository.findAll());
            mav.addObject("Assistance", "補助機能");
            return mav;
        }
        // エラーじゃない
        readingLogRepository.saveAndFlush(readingLog);
        return new ModelAndView("redirect:/readingMng");
    }
    // 更新---------------------------------------------------------------------------------------------------------
    // 読書記録の更新画面を表示する
    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@ModelAttribute("formModel") ReadingLog readingLog, @PathVariable Long id, ModelAndView mav) {
        mav.setViewName("redinghtml/rdedit");
        mav.addObject("title", "読了記録更新");

        // 編集画面用の書籍リストを取得
        List<Work> worklist = workRepository.findAll();
        mav.addObject("wklist",worklist);

        // 対象の記録を取得（存在しない場合は例外をスロー）
        ReadingLog data = readingLogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("無効な読書記録IDです: " + id));
        mav.addObject("formModel", data);
        return mav;
    }

    // 読書記録の更新処理を行う
    @PostMapping("/edit")
    @Transactional
    public ModelAndView edit(@ModelAttribute("formModel") @Validated ReadingLog readingLog, BindingResult result, ModelAndView mav) {
        // 入力チェックエラーがある場合
        if(result.hasErrors()) {
            mav.setViewName("redinghtml/rdedit");
            mav.addObject("title", "読了記録更新");
            mav.addObject("wklist", workRepository.findAll());
            return mav;
        }
        // エラーじゃない
        readingLogRepository.saveAndFlush(readingLog);
        return new ModelAndView("redirect:/readingMng");
    }

    // 削除---------------------------------------------------------------------------------------------------------
    // 読書記録の削除確認画面を表示する
    @GetMapping("/deletelog/{id}")
    public ModelAndView deleteLogForm(@ModelAttribute("formModel") Author author , @PathVariable Long id, ModelAndView mav) {
        mav.setViewName("redinghtml/rddelete");
        mav.addObject("title", "読了記録削除");

        // 対象の記録を取得（存在しない場合は例外をスロー）
        ReadingLog data = readingLogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("無効な読書記録IDです: " + id));
        mav.addObject("formModel", data);
        return mav;
    }

    // 読書記録を削除する
    @PostMapping("/deletelog")
    @Transactional
    public ModelAndView deleteLog(@RequestParam Long id) {
        readingLogRepository.deleteById(id);
        return new ModelAndView("redirect:/readingMng");
    }
}
