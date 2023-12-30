package com.coursera.model;


import com.coursera.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tbl_course")
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    private BigDecimal id;

    private String category;

    private String name;

    @Lob
    private String description;

    private String link;

    private Boolean active = true;

    private String createdBy;

}
