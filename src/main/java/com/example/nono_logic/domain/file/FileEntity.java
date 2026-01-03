package com.example.nono_logic.domain.file;

import com.example.nono_logic.domain.board.Board; // 곧 만들 클래스
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; // Setter 추가 (연관관계 편의 메서드용)

@Entity
@Table(name = "file_tb")
@Getter
@Setter
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String saveName;

    @Column(nullable = false)
    private String filePath;

    private Long size;

    // [추가] 게시글과의 관계 설정 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public FileEntity(String originalName, String saveName, String filePath, Long size) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.filePath = filePath;
        this.size = size;
    }
}