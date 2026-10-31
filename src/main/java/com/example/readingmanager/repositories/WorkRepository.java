package com.example.readingmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.readingmanager.entities.Work;

// 作品（Work）情報に対するデータベースアクセスおよび動的検索を提供するリポジトリインターフェース
@Repository
public interface WorkRepository extends JpaRepository<Work, Long>,JpaSpecificationExecutor<Work>{

    // 指定された著者IDに紐付く作品のリストを取得する
    List<Work> findByAuthor_Id(Long id);

    // 作品タイトルに部分一致する作品のリストを取得する
    List<Work> findByTitleContaining(String title);
}
