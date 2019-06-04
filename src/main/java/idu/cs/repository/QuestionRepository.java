package idu.cs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import idu.cs.entity.QuestionEntity;
import idu.cs.entity.UserEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
	/*
	QuestionEntity findByUserId(String userId);
	List<QuestionEntity> findByName(String name);
	List<QuestionEntity> findByNameOrderByIdAsc(String name);
	List<QuestionEntity> findByCompany (String company);
	*/
}
