package com.example.nono_logic.domain.puzzle;

import com.example.nono_logic.domain.puzzle.dto.PuzzleListResponse;
import com.example.nono_logic.domain.puzzle.dto.PuzzleDetailResponse;
import com.example.nono_logic.domain.user.User;
import com.example.nono_logic.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;
    private final UserRepository userRepository;

    // 목록 조회
    public List<PuzzleListResponse> getPuzzles() {
        return puzzleRepository.findAllByOrderByIdDesc()
                .stream()
                .map(p -> new PuzzleListResponse(p.getId(), p.getTitle()))
                .collect(Collectors.toList());
    }

    // 단건 상세 조회 (힌트 포함)
    public PuzzleDetailResponse getPuzzle(Long id) {
        Puzzle puzzle = puzzleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 퍼즐을 찾을 수 없습니다."));

        return new PuzzleDetailResponse(
                puzzle.getId(),
                puzzle.getTitle(),
                puzzle.getSize(),
                puzzle.getAnswer(),
                calculateRowHints(puzzle),
                calculateColHints(puzzle)
        );
    }

    // 생성 (작성자 매핑 로직 추가)
    @Transactional
    public Long create(CreatePuzzleRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Puzzle puzzle = new Puzzle(
                request.getTitle(),
                request.getSize(),
                request.getAnswer(),
                user
        );
        return puzzleRepository.save(puzzle).getId();
    }

    // 삭제 (권한 검증 포함)
    @Transactional
    public void delete(Long puzzleId, String email) {
        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 퍼즐을 찾을 수 없습니다."));

        if (!puzzle.getAuthor().getEmail().equals(email)) {
            throw new IllegalStateException("해당 퍼즐을 삭제할 권한이 없습니다.");
        }

        puzzleRepository.delete(puzzle);
    }

    // --- 힌트 계산 로직 (기존과 동일) ---

    private List<List<Integer>> calculateRowHints(Puzzle puzzle) {
        List<List<Integer>> allRowHints = new ArrayList<>();
        String answer = puzzle.getAnswer();
        int size = puzzle.getSize();

        for (int r = 0; r < size; r++) {
            String rowStr = answer.substring(r * size, (r + 1) * size);
            allRowHints.add(extractCounts(rowStr));
        }
        return allRowHints;
    }

    private List<List<Integer>> calculateColHints(Puzzle puzzle) {
        List<List<Integer>> allColHints = new ArrayList<>();
        String answer = puzzle.getAnswer();
        int size = puzzle.getSize();

        for (int c = 0; c < size; c++) {
            StringBuilder colStr = new StringBuilder();
            for (int r = 0; r < size; r++) {
                colStr.append(answer.charAt(r * size + c));
            }
            allColHints.add(extractCounts(colStr.toString()));
        }
        return allColHints;
    }

    private List<Integer> extractCounts(String line) {
        List<Integer> counts = new ArrayList<>();
        int count = 0;
        for (char ch : line.toCharArray()) {
            if (ch == '1') {
                count++;
            } else if (count > 0) {
                counts.add(count);
                count = 0;
            }
        }
        if (count > 0) counts.add(count);
        if (counts.isEmpty()) counts.add(0);
        return counts;
    }
}