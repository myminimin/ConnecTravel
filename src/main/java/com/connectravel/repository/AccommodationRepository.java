package com.connectravel.repository;

import com.connectravel.entity.AccommodationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, Long>, QuerydslPredicateExecutor<AccommodationEntity> {

    @Query("select a from AccommodationEntity a where a.member.id = :id")
    AccommodationEntity findAccommodationByMemberId(@Param("id") String id);

    @Query("select case when count(a)> 0 then true else false end from AccommodationEntity a where a.member.id = :id")
    boolean existsAccommodationByMemberId(@Param("id") String id);

    @Query("select a from AccommodationEntity a where a.member.email = :email")
    AccommodationEntity findByEmail(@Param("email") String email);

    @Query("SELECT a ,i FROM AccommodationEntity a left join AccommodationImgEntity i on a = i.accommodation group by a.ano")
    List<Object[]> findAccommodationWithImages();

    @Query("select r.accommodation FROM RoomEntity r where r.rno = :rno")
    AccommodationEntity findAnoByRno(@Param("rno") Long rno);

    AccommodationEntity findByAno(Long ano);

    // 숙소 정보와 숙소가 가지고 있는 방중 가장 작은 가격의 방을 보여줌. 날짜 입력을 통해 예약할 수있는 방을 보여줌.
    // 방 중에서 1개라도 NULL을 가지고 있으면 NULL으로 출력함 (빈방)
    // 인덱스 0번 숙소 / 1번 가격 / 2번 예약정보 (NULL일때는 빈방)
    @Query("select a, min(r.price) as min_price,rv.rvno as rvno" +
            " from AccommodationEntity a join RoomEntity as r on a = r.accommodation" +
            " left join ReservationEntity as rv on r = rv.room and rv.startDate >= :startDate" +
            " and rv.endDate <= :endDate where rvno is null group by a.ano")
    List<Object[]> findByRv_LowPrice(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);
}
