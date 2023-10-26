package com.connectravel.repository;

import com.connectravel.entity.AccommodationEntity;
import com.connectravel.entity.AccommodationImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccommodationImgRepository extends JpaRepository<AccommodationImgEntity, Long> {

    @Query("SELECT i from AccommodationImgEntity i where i.accommodation = :ano")
    List<AccommodationImgEntity> getImgByAccommodationId(@Param("ano") AccommodationEntity ano);

}
