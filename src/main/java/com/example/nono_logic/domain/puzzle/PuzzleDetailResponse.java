package com.example.nono_logic.domain.puzzle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class PuzzleDetailResponse {
    private Long id;
    private String title;
    private Integer size;
    private String answer;
    private List<List<Integer>> rowHints;    // 가로 힌트
    private List<List<Integer>> columnHints; // 세로 힌트
}