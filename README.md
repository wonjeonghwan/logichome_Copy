# logichome_Copy
본 프로젝트는 logichome 웹사이트 (원본: https://logichome.org/g5/)의주요 기능을 복제(clone)하는 Spring Boot 기반 프로젝트입니다.

유저 계정 관리, 노노그램 퍼즐 플레이, 커뮤니티 게시판 기능을 중심으로 구성되어 있으며,
백엔드는 **PostgreSQL + Spring Boot + JPA** 기반으로 구현되어 있습니다.

현재는 기능 단위로 점진적으로 확장 중인 상태입니다

## 프로젝트 진행 현황 (Project Status)

### AS-IS 화면 분석
- 완료. (기존 logichome 사이트의 주요 페이지 구성, 기능 흐름, 데이터 구조를 분석하여 요구사항을 정의함)

### ERD 작성
<img width="1451" height="873" alt="image" src="https://github.com/user-attachments/assets/b44df9dd-c620-4b03-8e05-14a092e2aa5e" />

- 완료
- 주요 엔티티:
  - User
  - Puzzle
  - PuzzleProgress
  - Board
  - FileEntity
- JPA 엔티티 기반 / 퍼즐 작성자, 게시글 작성자, 첨부 파일 관계 등 원본 사이트 구조를 반영
- 하지만 실제로 작업하며 다수 변경됨

### API 목록 및 Request / Response 구조 정의
- 완료
- 기능 단위로 API 엔드포인트를 정의하고, Request / Response DTO를 기준으로 통신
- 예시:
  - 로그인: LoginRequest / LoginResponse
  - 퍼즐 목록: PuzzleListResponse
  - 퍼즐 상세: PuzzleDetailResponse
  - 게시글: BoardRequest / BoardResponse
 
### User API 개발
**PostgreSQL 연동**
- PostgreSQL + Spring Data JPA 연동 완료
- application.yml 기준으로 DB 연결
- 엔티티 기반 테이블 자동 생성 및 검증

**JWT 기반 회원가입 / 로그인 구현**
- 회원가입 시 비밀번호 BCrypt 암호화
- 로그인 성공 시 JWT Access Token 발급
- Spring Security + JWT Filter 기반 인증 처리
- 인증 정책:
  - 로그인 / 회원가입 / 퍼즐 조회 / 게시판 조회 → 인증 없이 접근 가능
  - 퍼즐 생성 / 삭제, 진행 글 작성, 퍼즐 진행도 저장 → JWT 인증 필수
 
---
## 이하 진행 중

### Puzzle 기능
- **진행 중 (핵심 기능 구현 완료)**

**구현된 기능**
- 퍼즐 목록 조회 (비로그인 가능)
- 퍼즐 상세 조회
  - 퍼즐 정답 기반 행/열 힌트 자동 계산
- 퍼즐 생성 (로그인 유저)
- 퍼즐 삭제
  - 작성자 본인만 삭제 가능

**미구현 / 향후 예정**
- 퍼즐 수정 기능
- 퍼즐 통계 정보 (조회수, 완료 수 등)

### Puzzle 진행도 저장 기능
- **진행 중**

**구현된 기능**
- 퍼즐별 사용자 진행 상태 저장
- 사용자 + 퍼즐 기준으로 1개의 진행 데이터 유지
- 저장 정보:
  - 현재 퍼즐 상태(board state)
  - 누적 플레이 시간
  - 시작 시각 / 마지막 저장 시각

**제공 API**
- 진행도 저장
- 진행도 불러오기 (로그인 유저 기준)

### Board 게시판 기능
- **진행 중**

**구현된 기능**
- 게시글 작성 (로그인 필수)
- 게시글 목록 조회 / 상세 조회 (비로그인 가능)
- 게시글 수정 / 삭제
  - 작성자 본인만 가능
- 파일(이미지) 첨부 업로드
  - 서버 로컬 저장
  - DB에는 파일 메타 정보만 저장

**보완 예정**
- 게시글 삭제 시 실제 파일 삭제 처리
- 댓글 기능
- 게시판 카테고리 확장

---
## 이하 추가 작업 예정

- 유저 프로필 관리
- 퍼즐 완료 기록
- 추천 / 좋아요

- **어쩌면 위에 무엇보다 더 급한 프론트엔드드드ㄷㄷㄷㄷ...**

---
### 구조도
 ```
logichome_Copy
│
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── nono_logic
│       │
│       │               ├── config
│       │               │   ├── SecurityConfig.java
│       │               │   │   └─ Spring Security 설정
│       │               │   ├── JwtAuthenticationFilter.java
│       │               │   │   └─ JWT 인증 필터
│       │               │   └── JwtProvider.java
│       │               │       └─ JWT 생성 / 검증 로직
│       │               │
│       │               ├── domain
│       │               │
│       │               │   ├── user
│       │               │   │   ├── User.java
│       │               │   │   ├── UserRepository.java
│       │               │   │   ├── UserService.java
│       │               │   │   └── UserController.java
│       │               │   │
│       │               │   ├── puzzle
│       │               │   │   ├── Puzzle.java
│       │               │   │   ├── PuzzleRepository.java
│       │               │   │   ├── PuzzleService.java
│       │               │   │   ├── PuzzleController.java
│       │               │   │   └── dto
│       │               │   │       ├── PuzzleListResponse.java
│       │               │   │       ├── PuzzleDetailResponse.java
│       │               │   │       └── PuzzleCreateRequest.java
│       │               │   │
│       │               │   ├── progress
│       │               │   │   ├── PuzzleProgress.java
│       │               │   │   ├── PuzzleProgressRepository.java
│       │               │   │   └── PuzzleProgressService.java
│       │               │   │
│       │               │   ├── board
│       │               │   │   ├── Board.java
│       │               │   │   ├── BoardRepository.java
│       │               │   │   ├── BoardService.java
│       │               │   │   └── BoardController.java
│       │               │   │
│       │               │   └── file
│       │               │       ├── FileEntity.java
│       │               │       ├── FileRepository.java
│       │               │       └── FileService.java
│       │               │
│       │               ├── global
│       │               │   ├── exception
│       │               │   │   └─ 공통 예외 처리
│       │               │   ├── response
│       │               │   │   └─ 공통 응답 포맷
│       │               │   └── util
│       │               │       └─ 공통 유틸
│       │               │
│       │               └── NonoLogicApplication.java
│       │
│       └── resources
│           ├── application.yml
│           │   └─ DB, JWT, 서버 설정
│           └── static / templates
│
├── images
│   └─ 업로드된 이미지 저장 경로
│
├── build.gradle
│   └─ Gradle 의존성 관리
│
└── README.md
```
