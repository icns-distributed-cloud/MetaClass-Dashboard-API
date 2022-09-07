package com.classMetabus.web.Admin.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class adminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String phone;
}
