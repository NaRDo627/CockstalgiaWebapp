package net.hkpark.cockstalgia.webview.admin.service;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.cockstalgia.webview.core.service.WebViewCoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebViewAdminService {
    private final WebViewCoreService webViewCoreService;

    /**
     * 칵테일 베이스의 종류를 {@link net.hkpark.cockstalgia.core.constant.LiquorType}로부터 가져온다.
     * @return 베이스 종류 문자열이 담긴 리스트
     *
     * @see net.hkpark.cockstalgia.core.constant.LiquorType
     */
    public List<String> getLiquorBases() {
        return Arrays.stream(LiquorType.values()).map(Enum::name).collect(Collectors.toList());
    }

    public ResultDto saveMultipartFile(MultipartFile mFile) {
        String publicAccessUrl = webViewCoreService.saveMultipartFile(mFile);
        Map<String, Object> data = new HashMap<>();
        data.put("url", publicAccessUrl);
        return ResultDto.builder().data(data).build();
    }
}
