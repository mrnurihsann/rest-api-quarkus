package org.study.param; // Menyatakan bahwa class ini berada di dalam package 'org.study.param'

// Class ini berfungsi sebagai DTO (Data Transfer Object) atau parameter object
// yang digunakan untuk menerima data input dari client, seperti saat create/update karyawan
public class KaryawanParam {

    // Field untuk menyimpan nama karyawan yang dikirim dari client
    public String nama;

    // Field untuk menyimpan jabatan karyawan yang dikirim dari client
    public String jabatan;

    // Field untuk menyimpan alamat karyawan yang dikirim dari client
    public String alamat;
}
