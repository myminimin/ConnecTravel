package com.connectravel.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "room_option")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"room", "option"})
public class RoomOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rno", nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ono", nullable = false)
    private OptionEntity option;

}
