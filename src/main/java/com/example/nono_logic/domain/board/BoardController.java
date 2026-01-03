package com.example.nono_logic.domain.board;

import com.example.nono_logic.domain.board.dto.BoardRequest;
import com.example.nono_logic.domain.board.dto.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성 (파일 포함)
    // 중요: @ModelAttribute를 사용해야 form-data(파일+텍스트)를 한 번에 받음
    @PostMapping
    public Long write(
            @ModelAttribute BoardRequest request,
            Authentication authentication
    ) throws IOException {
        return boardService.writeBoard(authentication.getName(), request);
    }

    // 목록 조회
    @GetMapping
    public List<BoardResponse> getList() {
        return boardService.getBoardList();
    }

    // 상세 조회
    @GetMapping("/{id}")
    public BoardResponse getDetail(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody BoardRequest request, // [수정] @ModelAttribute -> @RequestBody
            Authentication authentication
    ) {
        boardService.updateBoard(authentication.getName(), id, request);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        boardService.deleteBoard(authentication.getName(), id);
    }
}