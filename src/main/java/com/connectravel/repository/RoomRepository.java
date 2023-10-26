package com.connectravel.repository;

import com.connectravel.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {

    @Query("SELECT r FROM RoomEntity r JOIN r.accommodation a WHERE r.rno = :rno")
    RoomEntity findByRoom_nameByRno(@Param("rno") Long rno);

    @Query("SELECT r, i FROM RoomEntity r LEFT JOIN RoomImgEntity i ON r = i.room WHERE r.accommodation.ano = :ano GROUP BY r.rno")
    List<Object[]> findRoomWithImages(@Param("ano") Long ano);

    //room+rv 검증할때 사용되는 쿼리문
    @Query("select room, r from RoomEntity room left join ReservationEntity as r " +
            "on room = r.room and r.startDate >= :startDate and r.endDate <= :endDate" +
            " where room.rno = :rno" +
            " group by room.rno")
    List<Object[]> findByDateByRno(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("rno") Long rno);

    //room+rv+img join (방 예약 리스트 정보 조회할때)
    //여기서,예약 정보까지 조인하는 이유는 방이 찼는지 안찼는지 확인해야하니까....(내가 작성해놓고 까먹음)
    @Query("select room, img.imgFile, r.rvno from RoomEntity room left join ReservationEntity as r " +
            "on room = r.room and (r.startDate < :endDate and r.endDate > :startDate) and r.state = true " +
            "left join RoomImgEntity as img on room = img.room " +
            "where room.accommodation.ano = :ano " +
            "group by room.rno")
    List<Object[]> findByDateAndImgAndAno(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("ano") Long ano);

    @Query("select room.roomName,count(r.room) * room.price from RoomEntity as room " +
            "left join ReservationEntity as r " +
            "on r.room = room and date(r.regTime) >= :startDate and date(r.regTime) <= :endDate and r.state = true" +
            " where room.accommodation.ano = :ano group by room.rno ")
    List<Object[]> findSalesByDate(@Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("ano")Long ano);

    @Query("select rv.rvno, rv.state, rv.regTime,rv.startDate,rv.endDate, reserver, rv.message from Member m" +
            " join AccommodationEntity a on m.id = a.member" +
            " join RoomEntity r on r.accommodation = a" +
            " join ReservationEntity rv on rv.room = r" +
            " join Member reserver on reserver.id = rv.member" +
            " where m.email = :email")
    List<Object[]> sellersFindReservation(String email);
}
