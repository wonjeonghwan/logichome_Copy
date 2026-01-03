package com.example.nono_logic.domain.puzzle;

import com.example.nono_logic.domain.user.User; // User 패키지 임포트 필요
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "puzzle")
@Getter
@Setter
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author; // 작성자 관계 설정

    protected Puzzle() {}

    public Puzzle(String title, Integer size, String answer, User author) {
        this.title = title;
        this.size = size;
        this.answer = answer;
        this.author = author;
    }
}