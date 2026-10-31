package com.example.readingmanager.entities;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.OneToMany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

// 作品情報を管理するエンティティ（データベースの work テーブルに対応）
@Entity
@Table(name="work")
@Getter
@Setter

public class Work {

    // 作品ID（主キー / 自動採番
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    // 著者情報（多対一のリレーション / 遅延読み込み）
    @ManyToOne(optional= false, fetch= FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author author;

    // 作品タイトル
    @Column
    private String title;

    // 出版日（過去の日付のみ許容）
    @Column
    @Past
    private LocalDate published;

    // 紐付く読書記録のリスト（一対多のリレーション）
    @OneToMany(mappedBy = "work")
    private List<ReadingLog> readingLogs;
}
