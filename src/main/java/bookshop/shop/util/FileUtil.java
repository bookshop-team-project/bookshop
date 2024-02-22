package bookshop.shop.util;

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
            throw new RuntimeException("파일 저장 실패");
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

}
