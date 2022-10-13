package com.classMetabus.web.Admin.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(unique = true,nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = true)
    @CreationTimestamp
    private LocalDateTime joinDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column
    @ColumnDefault(value = "0")
    private boolean deleted;

    @Column
    @ColumnDefault(value = "0")
    private Integer status; //0:need to change pw 1:재직 2:휴직 3:퇴직
}
