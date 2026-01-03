package com.example.nono_logic.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByIdDesc(); // 최신순 조회
}