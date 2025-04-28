package com.example.jobWise.template;

public class PromptTemplate {
    public static final String SELF_INTRO_PROMPT = """
        根據以下履歷摘要與應徵職缺，幫我生成一段 200 字以內的自我介紹，語氣專業、有自信：
        履歷摘要：%s
        職缺描述：%s
        """;

    public static final String INTERVIEW_ASK_PROMPT = """
        你是一位資深人資，請針對以下職缺設計一個面試問題：
        職稱：%s
        職缺描述：%s
        問題請簡潔且具挑戰性。
        """;

    public static final String INTERVIEW_FEEDBACK_PROMPT = """
        以下是面試回答，請你以面試官角度提供回饋，指出優缺點並給予改進建議：
        回答內容：%s
        """;
}
