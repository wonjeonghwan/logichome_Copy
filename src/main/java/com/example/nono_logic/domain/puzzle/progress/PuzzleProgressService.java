package com.example.nono_logic.domain.puzzle.progress;

import com.example.nono_logic.domain.puzzle.Puzzle;
import com.example.nono_logic.domain.puzzle.PuzzleRepository;
import com.example.nono_logic.domain.user.User;
import com.example.nono_logic.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PuzzleProgressService {

    private final PuzzleProgressRepository puzzleProgressRepository;
    private final UserRepository userRepository; // 추가
    private final PuzzleRepository puzzleRepository; // 추가

    public PuzzleProgress getProgress(String email, Long puzzleId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퍼즐입니다."));

        return puzzleProgressRepository.findByUserAndPuzzle(user, puzzle)
                .orElseThrow(() -> new IllegalArgumentException("진행 데이터가 없습니다."));
    }

    @Transactional
    public void saveProgress(String email, Long puzzleId, SavePuzzleProgressRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new IllegalArgumentException("퍼즐을 찾을 수 없습니다."));

        PuzzleProgress progress = puzzleProgressRepository
                .findByUserAndPuzzle(user, puzzle)
                .orElseGet(() -> {
                    PuzzleProgress newProgress = new PuzzleProgress();
                    newProgress.setUser(user);
                    newProgress.setPuzzle(puzzle);
                    newProgress.setElapsedSec(0);
                    newProgress.setStartedAt(LocalDateTime.now());
                    return newProgress;
                });

        progress.setBoardState(request.getBoardState());
        progress.setElapsedSec(progress.getElapsedSec() + request.getSessionElapsedSec());
        progress.setUpdatedAt(LocalDateTime.now());

        puzzleProgressRepository.save(progress);
    }
}