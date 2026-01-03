package com.example.nono_logic.domain.puzzle;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {

    List<Puzzle> findAllByOrderByIdDesc();
}
