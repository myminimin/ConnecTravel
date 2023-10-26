package com.connectravel.repository;

import com.connectravel.entity.RoomEntity;
import com.connectravel.entity.RoomImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomImgRepository extends JpaRepository<RoomImgEntity,Long> {
    @Query("SELECT i from RoomImgEntity i where i.room = :room")
    List<RoomImgEntity> getImgByRoom(@Param("room") RoomEntity room);
}
