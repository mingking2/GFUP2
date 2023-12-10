package com.example.gfup2.domain.user.repository;

import com.example.gfup2.domain.user.entity.RefreshToken;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByAccountEmail(String accountEmail);

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.refreshToken = :tokenValue")
    void deleteByRefreshToken(@Param("tokenValue") String tokenValue);

    @Transactional
    @Modifying
    @Query("UPDATE RefreshToken r SET r.refreshToken = :newTokenValue WHERE r.accountEmail = :email")
    void updateTokenValueByEmail(@Param("email") String email, @Param("newTokenValue") String newTokenValue);

    @Transactional
    RefreshToken findByRefreshToken(String refreshToken);
}
