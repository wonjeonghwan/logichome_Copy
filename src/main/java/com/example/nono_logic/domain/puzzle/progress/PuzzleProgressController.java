package com.example.nono_logic.domain.puzzle.progress;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/puzzles")
public class PuzzleProgressController {

    private final PuzzleProgressService puzzleProgressService;

    @GetMapping("/{puzzleId}/progress")
    public GetPuzzleProgressResponse getProgress(
            @PathVariable Long puzzleId,
            Authentication authentication
    ) {
        // authentication.getName()은 JWT 필터에서 저장한 email을 반환함
        PuzzleProgress progress = puzzleProgressService.getProgress(authentication.getName(), puzzleId);

        return new GetPuzzleProgressResponse(
                progress.getBoardState(),
                progress.getElapsedSec(),
                progress.getStartedAt(),
                progress.getUpdatedAt()
        );
    }

    @PostMapping("/{puzzleId}/progress/save")
    public void saveProgress(
            @PathVariable Long puzzleId,
            @RequestBody SavePuzzleProgressRequest request,
            Authentication authentication
    ) {
        puzzleProgressService.saveProgress(authentication.getName(), puzzleId, request);
    }
}