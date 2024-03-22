package com.shopme.common.entity.order;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "order_track")
public class OrderTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 256)
    private String notes;

    private Date updatedTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
