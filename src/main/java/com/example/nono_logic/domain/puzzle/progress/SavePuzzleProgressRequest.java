package com.example.nono_logic.domain.puzzle.progress;

public class SavePuzzleProgressRequest {

    private String boardState;
    private int sessionElapsedSec;
    private boolean firstInteraction;

    public String getBoardState() {
        return boardState;
    }

    public int getSessionElapsedSec() {
        return sessionElapsedSec;
    }

    public boolean isFirstInteraction() {
        return firstInteraction;
    }
}
