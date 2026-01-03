package com.example.nono_logic.domain.board.dto;

import com.example.nono_logic.domain.board.Board;
import lombok.Getter;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String writerEmail;
    private String imageUrl; // 업로드된 이미지 경로 (파일명)

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writerEmail = board.getWriter().getEmail();

        // 파일이 있으면 첫 번째 파일의 이름을 가져옴
        if (!board.getFiles().isEmpty()) {
            this.imageUrl = board.getFiles().get(0).getSaveName();
        }
    }
}