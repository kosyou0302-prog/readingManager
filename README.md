# ReadingManager

Java / Spring Boot を用いて開発した、個人向けの**読書管理・蔵書検索Webアプリケーション**です。
日々の読書記録の管理をはじめ、著者ごとの作品管理や、複数の条件（作品名・著者・読了状態）を組み合わせた動的検索機能を備えています。

---

## 技術スタック（Tech Stack）

* **Backend**: Java 25, Spring Boot
* **Data Access**: Spring Data JPA , H2 Database
* **Frontend**: Thymeleaf, HTML5, Bootstrap 5
* **Build Tool**: Maven
* **Version Control**: Git / GitHub

---

##  主な機能（Features）

1. **読書記録の管理（CRUD機能）**
   * 読了日、感想メモ、5段階評価（1〜5）を紐付けた読書記録の登録・編集・削除。
   * ホーム画面での「直近の読書記録（上位5件）」一覧表示。
2. **著者および作品のマスタ管理**
   * 著者情報（生没年など）および作品情報（出版日など）の登録・管理。
3. **柔軟な動的検索機能（Specification API活用）**
   * 作品名（部分一致）、著者（プルダウン選択）、読了状態（読了/未読）を自由に組み合わせた高度な検索。
4. **入力値バリデーション & ユーザーフレンドリーなUI**
   * Bean Validationによる堅牢な入力チェックと、Bootstrap 5によるレスポンシブで洗練されたUI/UX。

---

## データベース設計（ER図 / テーブル構造）

本アプリは3つのテーブルによるリレーショナルデータベースとして設計されています。

* **`author`（著者テーブル）**: 著者名、生年月日、没年月日
* **`work`（作品テーブル）**: 著者ID（外部キー）、作品タイトル、出版日
* **`readlog`（読書記録テーブル）**: 作品ID（外部キー）、読了日、メモ、評価

---

## プロジェクト構成（Package Structure）

```text
com.example.readingmanager
 ┣ controllers/     # 画面遷移およびリクエスト制御 (Author, Work, ReadingLog)
 ┣ entities/        # データベースモデルエンティティ (Author, Work, ReadingLog)
 ┣ repositories/    # データアクセスおよび動的検索インターフェース
 ┣ services/        # ビジネスロジック層
 ┗ DTO / forms/     # フォームデータ保持およびバリデーション用クラス