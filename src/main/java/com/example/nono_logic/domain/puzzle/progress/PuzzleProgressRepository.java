package com.example.nono_logic.domain.puzzle.progress;


import com.example.nono_logic.domain.user.User;
import com.example.nono_logic.domain.puzzle.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PuzzleProgressRepository
        extends JpaRepository<PuzzleProgress, Long> {

    Optional<PuzzleProgress> findByUserAndPuzzle(
            User user,
            Puzzle puzzle
    );
}
