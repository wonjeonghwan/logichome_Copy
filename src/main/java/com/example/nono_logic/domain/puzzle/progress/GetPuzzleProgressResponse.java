package com.example.nono_logic.domain.puzzle.progress;

import java.time.LocalDateTime;

public class GetPuzzleProgressResponse {

    private final String boardState;
    private final int elapsedSec;
    private final LocalDateTime startedAt;
    private final LocalDateTime updatedAt;

    public GetPuzzleProgressResponse(
            String boardState,
            int elapsedSec,
            LocalDateTime startedAt,
            LocalDateTime updatedAt
    ) {
        this.boardState = boardState;
        this.elapsedSec = elapsedSec;
        this.startedAt = startedAt;
        this.updatedAt = updatedAt;
    }

    public String getBoardState() { return boardState; }
    public int getElapsedSec() { return elapsedSec; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
