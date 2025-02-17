package com.connectravel.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString(exclude = "accommodation")
public class AccommodationImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE) //delete 옵션을 cascade로 설정하는것! room이 삭제되면 img는 쓰레기가 되디때문에 지워줘야됨
    private Accommodation accommodation;

    @Column(length = 200)
    private String imgFile;

}
