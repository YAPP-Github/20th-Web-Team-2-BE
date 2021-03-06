package com.yapp.lonessum.domain.dating.repository;

import com.yapp.lonessum.domain.constant.MatchStatus;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DatingSurveyRepository extends JpaRepository<DatingSurveyEntity, Long> {
    Optional<DatingSurveyEntity> findByUser(UserEntity user);
    Optional<List<DatingSurveyEntity>> findAllByMatchStatus(MatchStatus matchStatus);
    Optional<DatingSurveyEntity> findByKakaoId(String kakaoId);
}
