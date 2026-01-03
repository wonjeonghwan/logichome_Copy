package com.example.nono_logic.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BoardRequest {
    private String title;
    private String content;
    private MultipartFile file; // 이미지 파일 (하나만 업로드 예시)
}