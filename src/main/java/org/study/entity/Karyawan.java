package org.study.entity; // Menyatakan bahwa class ini berada dalam package 'org.study.entity'

import io.quarkus.hibernate.orm.panache.PanacheEntityBase; // Mengimpor class dasar Panache yang menyederhanakan penggunaan JPA
import jakarta.persistence.Entity; // Anotasi untuk menandai bahwa class ini adalah entity JPA
import jakarta.persistence.GeneratedValue; // Digunakan untuk auto-generasi nilai pada ID
import jakarta.persistence.GenerationType; // Menentukan strategi auto-generasi ID
import jakarta.persistence.Id; // Menandai bahwa field tersebut adalah primary key

// Menandakan bahwa class ini adalah Entity JPA dan akan dipetakan ke tabel database
@Entity
public class Karyawan extends PanacheEntityBase {
    // Menggunakan PanacheEntityBase memungkinkan kita memakai method bawaan seperti findAll(), persist(), dll.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Menandai bahwa karyawanid adalah primary key dan nilainya akan dibuat secara otomatis (auto increment)
    // Strategy.IDENTITY cocok untuk PostgreSQL karena akan menggunakan SERIAL atau IDENTITY di database
    public Long karyawanid;

    // Field biasa yang akan menjadi kolom di tabel 'karyawan'
    public String nama;
    public String jabatan;
    public String alamat;
}
