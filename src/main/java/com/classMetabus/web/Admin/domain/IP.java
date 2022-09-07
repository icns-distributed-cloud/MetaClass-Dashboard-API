package com.classMetabus.web.Admin.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class IP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(unique = true,nullable = false)
    private String address;

    @Column(unique = true,nullable = false)
    private String name;

    @Column
    private Integer maxUser;

    @Column
    @ColumnDefault(value = "0")
    private boolean deleted;
}
