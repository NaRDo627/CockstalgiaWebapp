package net.hkpark.cockstalgia.webview.core.service;

import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.core.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebViewCoreService {
    @Value("${media.public.access.url}")
    private String mediaPublicAccessUrl;

    @Value("${dir.media}")
    private String mediaBaseDirName;

    @Value("${dir.media.image.cocktail}")
    private String cocktailImagePathDirName;

    /**
     * Multipart File 을 저장하고, Http를 통해 public 접근이 가능한 URL을 리턴한다.
     *
     * @param mFile 요청으로 들어온 Multipart File
     * @return public 접근이 가능한 URL
     */
    public String saveMultipartFile(MultipartFile mFile) {
        String savedFilePath = FileUtil.saveMultiPartFile(mFile, cocktailImagePathDirName);
        String resourceUrlPath = savedFilePath.replace(mediaBaseDirName, "");
        return mediaPublicAccessUrl + resourceUrlPath;
    }
}
