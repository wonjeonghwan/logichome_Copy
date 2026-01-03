package com.example.nono_logic.domain.puzzle.progress;

import com.example.nono_logic.domain.puzzle.Puzzle;
import com.example.nono_logic.domain.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "puzzle_progress",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "puzzle_id"})
        }
)
public class PuzzleProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @Column(name = "board_state", nullable = false, columnDefinition = "TEXT")
    private String boardState;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "elapsed_sec", nullable = false)
    private int elapsedSec;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected PuzzleProgress() {
    }
}
