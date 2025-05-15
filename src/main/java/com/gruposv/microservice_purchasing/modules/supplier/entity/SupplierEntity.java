package com.gruposv.microservice_purchasing.modules.supplier.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Where;



@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tb_suppliers")
@Where(clause = "deleted_at IS NULL")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    @Column(name = "supplier_name", length = 255, nullable = false)
    private String name;

}
