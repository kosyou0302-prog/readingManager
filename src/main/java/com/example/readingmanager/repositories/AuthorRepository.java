package com.example.readingmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.readingmanager.entities.Author;

// 著者情報（Author）に対するデータベースアクセスを提供するリポジトリインターフェース
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
