package net.hkpark.cockstalgia.core.util;

import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.vo.MemberIdentityKeyBaseVo;
import org.springframework.security.core.GrantedAuthority;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

@Slf4j
public class SecurityUtil {
    private SecurityUtil() { }

    public static String encodeUserKey(MemberIdentityKeyBaseVo memberIdentityKeyBaseVo) {
        return DigestUtil.md5(memberIdentityKeyBaseVo.getRealname() + ":" + memberIdentityKeyBaseVo.getBirthday());
    }

    public static boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(x -> x.equals(role));
    }
}