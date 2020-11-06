package net.hkpark.cockstalgia.core.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * 이 값을 기반으로 회원의 키값이 만들어진다.
 */
@Builder
@Getter
public class MemberIdentityKeyBaseVo {
    @NonNull
    private final String realname;

    @NonNull
    private final String birthday;
}
