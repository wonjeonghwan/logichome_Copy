package com.example.nono_logic.domain.board;

import com.example.nono_logic.domain.board.dto.BoardRequest;
import com.example.nono_logic.domain.board.dto.BoardResponse;
import com.example.nono_logic.domain.file.FileEntity;
import com.example.nono_logic.domain.file.FileService;
import com.example.nono_logic.domain.user.User;
import com.example.nono_logic.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FileService fileService; // 우리가 만든 파일 서비스

    // 게시글 작성 (+파일 업로드)
    public Long writeBoard(String email, BoardRequest request) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        // 1. 게시글 생성
        Board board = new Board(request.getTitle(), request.getContent(), user);

        // 2. 파일 저장 로직
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            FileEntity fileEntity = fileService.saveFile(request.getFile());
            board.addFile(fileEntity); // 게시글과 파일 연결
        }

        return boardRepository.save(board).getId();
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<BoardResponse> getBoardList() {
        return boardRepository.findAllByOrderByIdDesc().stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        return new BoardResponse(board);
    }

    // 게시글 삭제
    public void deleteBoard(String email, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 작성자 검증
        if (!board.getWriter().getEmail().equals(email)) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        // 파일 삭제는 CascadeType.ALL로 인해 DB에서는 자동 삭제됨
        // (실제 디스크 파일 삭제 로직은 선택 사항으로, 지금은 생략합니다)
        boardRepository.delete(board);
    }

    // 게시글 수정 (파일 수정 제외, 텍스트만)
    @Transactional
    public void updateBoard(String email, Long boardId, BoardRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!board.getWriter().getEmail().equals(email)) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        // 제목, 내용 업데이트
        board.update(request.getTitle(), request.getContent());

        // (심화: 이미지 수정 기능은 복잡도가 높으므로 우선 텍스트 수정만 구현합니다)
    }
}