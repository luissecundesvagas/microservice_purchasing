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

    @Column(name = "document_number", length = 50, nullable = false)
    private String documentNumber;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "contact_phone", length = 50, nullable = false)
    private String contactPhone;

    @Column(name = "contact_email", length = 100, nullable = false)
    private String contactEmail;

    @Column(name = "payment_terms", length = 100, nullable = false)
    private String paymentTerms;

    @Column(name = "bank_account_info", nullable = false)
    private String bankAccountInfo;

    @Column(name = "supplier_rating", nullable = false)
    private Double supplierRating;

}

/* 
id: INT AUTO_INCREMENT, PRIMARY KEY
supplier_name: VARCHAR(255)
document_number: VARCHAR(50) – (CNPJ/CPF)
address: VARCHAR(255) – (ou TEXT, se for necessário maior detalhamento)
contact_phone: VARCHAR(50)
contact_email: VARCHAR(100)
payment_terms: VARCHAR(100)
bank_account_info: TEXT
supplier_rating: DECIMAL(3,2) – (por exemplo, nota de 0.00 a 5.00)
created_at: DATETIME
updated_at: DATETIME
*/