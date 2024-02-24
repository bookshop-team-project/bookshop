package bookshop.shop.util;

import bookshop.shop.exception.item.FileDeleteException;
import bookshop.shop.exception.item.FileSaveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileUtil {

    public String extractExtension(String fileName) {
        int lastedIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastedIndex);
    }

    public String changeFileName(String fileName) {
        String extension = extractExtension(fileName);
        return UUID.randomUUID() + extension;
    }

    public String saveFile(String fileDir, MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String UUIDFileName = changeFileName(originalFileName);
        File file = new File(fileDir + UUIDFileName);

        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileSaveException("파일 저장 실패");
        }
        return UUIDFileName;
    }

    public List<String> saveFiles(String fileDir, List<MultipartFile> multipartFiles) {
        List<String> savedFiles = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String savedFile = saveFile(fileDir, multipartFile);
            savedFiles.add(savedFile);
        }
        return savedFiles;
    }

    public void deleteFile(String fileDir, String fileName) {
        File file = new File(fileDir + fileName);
        try {
            if (file.exists()) {
                if (file.delete()) {
                    log.debug("{} 파일 삭제 완료", fileName);
                } else {
                    log.error("{} 파일 삭제 실패", fileName);
                    throw new FileDeleteException("파일 삭제 실패");
                }
                log.debug("{} 파일이 존재하지 않습니다.", fileName);
            }
        } catch (Exception e) {
            log.error("{} 파일 삭제 중 오류 발생 : {}", fileName, e.getMessage());
            throw new FileDeleteException("파일 삭제 실패");
        }
    }

    public void deleteFiles(String fileDir, List<String> fileName) {
        for (String s : fileName) {
            deleteFile(fileDir, s);
        }
    }
}
