package com.nhnacademy.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.nhnacademy.springjwt.entity.RefreshTokenEntity;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
	Boolean existsByRefreshToken(String refreshToken);

	@Transactional
	void deleteByRefreshToken(String refreshToken);
}
