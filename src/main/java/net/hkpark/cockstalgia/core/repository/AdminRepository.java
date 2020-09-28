package net.hkpark.cockstalgia.core.repository;

import net.hkpark.cockstalgia.core.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
