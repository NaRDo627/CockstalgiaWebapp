package net.hkpark.cockstalgia.webview.admin.service;

import net.hkpark.cockstalgia.core.constant.LiquorType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebViewAdminService {
    public List<String> getLiquorBases() {
        return Arrays.stream(LiquorType.values()).map(Enum::name).collect(Collectors.toList());
    }
}
