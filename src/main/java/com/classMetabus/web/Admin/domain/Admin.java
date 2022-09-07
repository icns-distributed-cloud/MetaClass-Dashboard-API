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

    @Column(nullable = true)
    @CreationTimestamp
    private LocalDateTime joinDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column
    @ColumnDefault(value = "0")
    private boolean deleted;
}
