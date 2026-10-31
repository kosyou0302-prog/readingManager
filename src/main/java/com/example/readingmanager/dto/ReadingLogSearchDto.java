package com.example.readingmanager.dto;

import lombok.Getter;
import lombok.Setter;

// 作品検索画面からの検索条件を受け取るDTO（Data Transfer Object）クラス
@Getter
@Setter
public class ReadingLogSearchDto {

    // 検索キーワード（作品タイトル）
    private String title;

    // 著者ID（プルダウン等で選択された著者による絞り込み）
    private Long authorId;

    // 読書ステータス（未読・読書中・読了などの状態による絞り込み）
    private Integer readStatus;
}
