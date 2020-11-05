package net.hkpark.cockstalgia.core.service;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.entity.Member;
import net.hkpark.cockstalgia.core.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findByKakaoBotUserIdAndIsActive(id, true)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("id %s was not found", id)));
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(member.isAdmin())
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getName(), member.getMemberIdentityKey(), authorities);
    }
}