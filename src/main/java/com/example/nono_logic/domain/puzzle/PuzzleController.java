package com.example.nono_logic.domain.puzzle;

import com.example.nono_logic.domain.puzzle.dto.PuzzleDetailResponse;
import com.example.nono_logic.domain.puzzle.dto.PuzzleListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication; // Import 추가
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    // 전체 목록 조회
    @GetMapping
    public List<PuzzleListResponse> getPuzzles() {
        return puzzleService.getPuzzles();
    }

    // 단건 상세 조회
    @GetMapping("/{id}")
    public PuzzleDetailResponse getPuzzle(@PathVariable Long id) {
        return puzzleService.getPuzzle(id);
    }

    // 퍼즐 생성
    @PostMapping
    public Long create(@RequestBody CreatePuzzleRequest request, Authentication authentication) {
        // Service 파라미터 변경에 맞춰 email 전달
        return puzzleService.create(request, authentication.getName());
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        // 권한 체크를 위해 email 전달
        puzzleService.delete(id, authentication.getName());
    }
}