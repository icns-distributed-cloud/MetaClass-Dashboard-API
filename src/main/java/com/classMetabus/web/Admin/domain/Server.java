package com.classMetabus.web.Admin.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
/*    @Column
    @ColumnDefault(value = "0")
    private boolean deleted;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ip_id")
    private IP ip;

/*    public boolean getDeleted() {
        return deleted;
    }*/
}
