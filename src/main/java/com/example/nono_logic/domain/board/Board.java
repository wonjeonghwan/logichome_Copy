package com.example.nono_logic.domain.board;

import com.example.nono_logic.domain.file.FileEntity;
import com.example.nono_logic.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_tb")
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int views; // 조회수

    private LocalDateTime createdAt;

    // 작성자 (User와 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    // 첨부파일 (File과 연결)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<FileEntity> files = new ArrayList<>();

    public Board(String title, String content, User writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.views = 0;
        this.createdAt = LocalDateTime.now();
    }

    // 파일 연관관계 편의 메서드
    public void addFile(FileEntity file) {
        this.files.add(file);
        file.setBoard(this);
    }

    // 게시글 수정 (제목, 내용)
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}