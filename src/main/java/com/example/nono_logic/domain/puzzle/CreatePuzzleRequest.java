package com.example.nono_logic.domain.puzzle;

import lombok.Getter;

@Getter
public class CreatePuzzleRequest {
    private String title;
    private Integer size;
    private String answer;
}