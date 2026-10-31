package com.example.readingmanager.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

// 著者情報を管理するエンティティ（データベースの author テーブルに対応）
@Entity
@Table(name="author")
@Getter
@Setter

public class Author {

    // 著者ID（主キー / 自動採番）
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;

    // 著者名（最大20文字
    @Column(length=20)
    private String name;

    // 生年月日（過去の日付のみ許容、YYYY-MM-DD形式）
    @Column
    @Past(message = "過去の日付にしてください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    // 没年月日（過去の日付のみ許容、YYYY-MM-DD形式）
    @Column
    @Past(message = "過去の日付にしてください")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate death;
}
