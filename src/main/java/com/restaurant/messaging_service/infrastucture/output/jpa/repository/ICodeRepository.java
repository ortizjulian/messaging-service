package com.restaurant.messaging_service.infrastucture.output.jpa.repository;

import com.restaurant.messaging_service.infrastucture.output.jpa.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICodeRepository extends JpaRepository<CodeEntity,Long> {

}
