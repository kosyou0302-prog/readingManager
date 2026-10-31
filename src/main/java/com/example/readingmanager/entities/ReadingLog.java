package com.example.readingmanager.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

// 読書記録情報を管理するエンティティ（データベースの readlog テーブルに対応）
@Entity
@Table(name="readlog")
@Getter
@Setter

public class ReadingLog {

    // 読書記録ID（主キー / 自動採番）
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    // 対象の作品情報（多対一のリレーション / 遅延読み込み）
    @ManyToOne(optional= false, fetch= FetchType.LAZY)
    @JoinColumn(name="work_id")
    private Work work;

    // 読了日（今日または過去の日付のみ許容、YYYY-MM-DD形式）
    @Column
    @PastOrPresent(message = "今日または過去の日付にしてください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedDate;

    // 読書メモ（最大255文字）
    @Column(length = 255)
    private String memo;

    // 評価
    @Column
    private Integer rating;
}
