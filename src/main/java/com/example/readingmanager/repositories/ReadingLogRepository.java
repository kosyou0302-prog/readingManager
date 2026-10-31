package com.example.readingmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.readingmanager.entities.ReadingLog;

// 読書記録（ReadingLog）に対するデータベースアクセスおよび動的検索を提供するリポジトリインターフェース
@Repository
public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long>,JpaSpecificationExecutor<ReadingLog> {

    // 指定された作品IDに紐付く読書記録のリストを取得する
    List<ReadingLog> findByWork_Id(Long id);

    // 作品タイトルに部分一致する読書記録のリストを取得する
    List<ReadingLog> findByWork_TitleContaining(String title);

    // 読了日の新しい順に上位5件の読書記録を取得する（ホーム画面の最近の記録表示用）
    List<ReadingLog> findTop5ByOrderByFinishedDateDesc();
}
