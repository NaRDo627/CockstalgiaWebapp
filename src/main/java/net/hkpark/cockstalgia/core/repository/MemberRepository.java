package net.hkpark.cockstalgia.core.repository;

import net.hkpark.cockstalgia.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByKakaoBotUserId(String kakaoBotUserId);
    Optional<Member> findByKakaoBotUserIdAndIsActive(String kakaoBotUserId, boolean isActive);
}
