package net.hkpark.cockstalgia.webview.admin.service;

import net.hkpark.cockstalgia.core.constant.LiquorType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebViewAdminService {

    /**
     * 칵테일 베이스의 종류를 {@link net.hkpark.cockstalgia.core.constant.LiquorType}로부터 가져온다.
     * @return 베이스 종류 문자열이 담긴 리스트
     *
     * @see net.hkpark.cockstalgia.core.constant.LiquorType
     */
    public List<String> getLiquorBases() {
        return Arrays.stream(LiquorType.values()).map(Enum::name).collect(Collectors.toList());
    }
}
