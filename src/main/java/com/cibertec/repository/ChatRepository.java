package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	

}
