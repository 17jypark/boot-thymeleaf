package idu.cs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import idu.cs.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUserId(String userId);
	List<UserEntity> findByName(String name);
	List<UserEntity> findByNameOrderByIdAsc(String name);
	List<UserEntity> findByCompany (String company);
}
