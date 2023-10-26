package com.connectravel.repository;

import com.connectravel.entity.Member;
import com.connectravel.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    @Query("select r from ReservationEntity r where r.member = :member")
    List<ReservationEntity> findByMember(@Param("member") Member member);
    @Query("select r from ReservationEntity r where r.rvno = :rvno and r.startDate > :Today " +
            " and r.member = :member")
    Optional<ReservationEntity> findByRvnoAndDateAndMember_id
            (@Param("Today") LocalDate Today, @Param("rvno")Long rvno,@Param("member") Member member);

    public ReservationEntity findByRvno(@Param("rvno")Long Rvno);
}

