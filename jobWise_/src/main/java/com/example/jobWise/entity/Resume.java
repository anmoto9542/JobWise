package com.example.jobWise.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "resumes")
@EntityListeners(AuditingEntityListener.class)
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String content; // 純文字履歷內容

    private String filePath; // 若為檔案，儲存路徑

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_time")
    private LocalDateTime updatedAt;
}
