package com.example.nono_logic.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    @Transactional
    public FileEntity saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        // [중요 수정] 상대 경로 문제를 방지하기 위해 절대 경로로 변환
        String fullPath = new File(fileDir + storeFileName).getAbsolutePath();

        // [중요 수정] 폴더가 없으면 자동으로 생성하는 로직 추가
        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // images 폴더 생성
        }

        // 파일 저장
        multipartFile.transferTo(file);

        // DB 저장
        FileEntity fileEntity = new FileEntity(
                originalFilename,
                storeFileName,
                fullPath, // 나중에 이미지를 불러올 때 쓸 경로
                multipartFile.getSize()
        );

        return fileRepository.save(fileEntity);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}