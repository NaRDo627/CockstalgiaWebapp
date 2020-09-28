package net.hkpark.cockstalgiacore.core.repository;

import net.hkpark.cockstalgiacore.core.entity.Admin;
import net.hkpark.cockstalgiacore.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
