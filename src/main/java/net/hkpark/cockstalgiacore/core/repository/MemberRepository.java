package net.hkpark.cockstalgiacore.core.repository;

import net.hkpark.cockstalgiacore.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
