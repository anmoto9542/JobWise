JobWise - 求職助理平台

JobWise 是一個基於 Spring Boot 與 AI 技術的求職助理平台，這個專案的目的是學習如何串接 OpenAI API，並實現應用。透過這個平台，使用者可以根據履歷生成面試自我介紹、模擬面試，以 AI 模擬面試官提問的方式，練習面試問答。

※主要功能

    帳號系統：使用者註冊、登入（支持 JWT 認證）

    履歷管理：上傳、查看、刪除履歷資料

    AI 助手：
     自我介紹生成：根據履歷生成面試自我介紹

     模擬面試：AI 扮演面試官，提供面試問題

     回應建議：AI 分析回答，給出改進建議

※技術棧
    
    後端：Spring Boot, Spring MVC, Spring Security (JWT)

    資料庫：MySQL

    AI 技術：OpenAI GPT-3.5 / GPT-4 API

※系統架構

    API 設計：採用 RESTful API 架構，確保前後端分離，並提供簡單、清晰的端點。

    AI 整合：根據需求，選擇合適的模型（GPT-3.5 用於輕量級功能，GPT-4 用於高精度需求）。

    資料存儲：MySQL 用於儲存使用者資料與履歷信息，並結合 Redis 提供快取優化。

※API 設計

    Method	 Endpoint	                 說明
    POST	/api/auth/register	         使用者註冊
    POST	/api/auth/login	                 使用者登入（回傳 JWT）
    GET	/api/user/info	                 取得使用者資料（需 JWT）
    POST	/api/resume/upload	         上傳履歷（文字 / 檔案）
    GET	/api/resume/list	         查使用者全部履歷
    GET	/api/resume/{id}	         查看履歷內容
    DELETE	/api/resume/{id}	         刪除履歷
    POST	/api/ai/self-intro	         根據履歷自動生成自我介紹
    POST	/api/ai/interview/ask	         模擬面試問答
    POST	/api/ai/interview/feedback	 問答回饋
