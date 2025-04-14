package org.study.resource;

import java.util.List;  // Digunakan untuk bekerja dengan koleksi data, seperti List yang digunakan untuk menyimpan dan mengembalikan kumpulan karyawan.

import org.study.entity.Karyawan;  // Mengimpor entitas Karyawan yang biasanya berisi model data untuk tabel karyawan dalam database.
import org.study.param.KaryawanParam;  // Mengimpor KaryawanParam, yang kemungkinan merupakan kelas untuk menangani data input dari pengguna (seperti data yang diterima dalam request body).

import jakarta.inject.Inject;  // Anotasi dari Jakarta CDI (Contexts and Dependency Injection) yang digunakan untuk menyuntikkan (inject) dependency seperti EntityManager.
import jakarta.persistence.EntityManager;  // EntityManager digunakan untuk berinteraksi dengan database menggunakan JPA (Java Persistence API) untuk query, penyimpanan data, dan lain-lain.
import jakarta.persistence.Query;  // Query digunakan untuk menjalankan query native SQL atau JPQL terhadap database.
import jakarta.transaction.Transactional;  // Anotasi untuk menandai bahwa metode yang dijalankan dalam konteks transaksi, memastikan perubahan data dikelola dalam sebuah transaksi database.
import jakarta.ws.rs.Consumes;  // Anotasi ini digunakan untuk menandakan jenis data yang diterima oleh endpoint REST (dalam hal ini, menerima data dalam format JSON).
import jakarta.ws.rs.DELETE;  // Menandakan bahwa metode ini akan menangani permintaan HTTP DELETE untuk menghapus data.
import jakarta.ws.rs.GET;  // Menandakan bahwa metode ini akan menangani permintaan HTTP GET untuk mengambil data.
import jakarta.ws.rs.POST;  // Menandakan bahwa metode ini akan menangani permintaan HTTP POST untuk menambah data.
import jakarta.ws.rs.PUT;  // Menandakan bahwa metode ini akan menangani permintaan HTTP PUT untuk memperbarui data.
import jakarta.ws.rs.Path;  // Anotasi ini digunakan untuk menetapkan path URL dari setiap endpoint REST. Setiap metode endpoint bisa memiliki path yang spesifik.
import jakarta.ws.rs.PathParam;  // Digunakan untuk mengambil parameter dari URL path, misalnya `/karyawan/{id}` akan memetakan `{id}` menjadi parameter method.
import jakarta.ws.rs.Produces;  // Digunakan untuk mengambil parameter query dari URL, misalnya `/karyawan/search?nama=Budi` akan memetakan `nama` menjadi parameter method.
import jakarta.ws.rs.QueryParam;  // Menandakan jenis media yang dihasilkan oleh endpoint REST (dalam hal ini, mengembalikan data dalam format JSON).
import jakarta.ws.rs.core.MediaType;  // Digunakan untuk menyebutkan jenis media (misalnya "application/json") yang digunakan dalam HTTP request/response.
import jakarta.ws.rs.core.Response;  // Digunakan untuk membangun respons HTTP yang dikembalikan ke klien, seperti status dan body dari respons.

@Path("/karyawan")  // Menentukan path untuk resource ini, yaitu "/karyawan"
@Produces(MediaType.APPLICATION_JSON)  // Menentukan bahwa resource ini akan menghasilkan output JSON
@Consumes(MediaType.APPLICATION_JSON)  // Menentukan bahwa resource ini akan menerima input JSON
public class KaryawanResource {

    @Inject
    EntityManager em;  // Dependency injection untuk EntityManager, digunakan untuk interaksi dengan database

    // Endpoint untuk mendapatkan semua data karyawan
    @GET
    public List<Karyawan> getAll() {
        return Karyawan.listAll();  // Mengambil semua data karyawan dari database
    }

    // Endpoint untuk mendapatkan data karyawan berdasarkan ID
    @GET
    @Path("/{id}")  // Path parameter {id} digunakan untuk mengambil karyawan berdasarkan ID
    public Karyawan getById(@PathParam("id") Long id) {
        return Karyawan.findById(id);  // Mencari karyawan berdasarkan ID yang diberikan
    }

    // Endpoint untuk mencari karyawan berdasarkan nama
    @GET
    @Path("/search")  // Path tambahan "/search" untuk pencarian berdasarkan nama
    public List<Karyawan> getByNama(@QueryParam("nama") String nama) {
        return Karyawan.list("nama", nama);  // Mencari karyawan berdasarkan nama
    }

    // Endpoint untuk membuat data karyawan baru
    @POST
    @Transactional  // Menandakan bahwa operasi ini memodifikasi database dan perlu transaksi
    public Response create(KaryawanParam request) {
        Karyawan karyawan = new Karyawan();  // Membuat objek karyawan baru
        karyawan.nama = request.nama;  // Menetapkan nama karyawan dari request
        karyawan.jabatan = request.jabatan;  // Menetapkan jabatan karyawan dari request
        karyawan.alamat = request.alamat;  // Menetapkan alamat karyawan dari request

        karyawan.persist();  // Menyimpan data karyawan ke database
        return Response.status(Response.Status.CREATED).entity(karyawan).build();  // Mengembalikan respons dengan status CREATED dan data karyawan
    }

    // Endpoint untuk memperbarui data karyawan berdasarkan ID
    @PUT
    @Path("/{id}")  // Path parameter {id} digunakan untuk memperbarui data karyawan berdasarkan ID
    @Transactional  // Menandakan bahwa operasi ini memodifikasi database dan perlu transaksi
    public Response update(@PathParam("id") Long id, Karyawan updated) {
        Karyawan karyawan = Karyawan.findById(id);  // Mencari karyawan berdasarkan ID
        if (karyawan == null)  // Jika karyawan tidak ditemukan, kembalikan status NOT_FOUND
            return Response.status(Response.Status.NOT_FOUND).build();

        // Memperbarui data karyawan dengan data yang baru
        karyawan.nama = updated.nama;
        karyawan.jabatan = updated.jabatan;
        karyawan.alamat = updated.alamat;

        return Response.ok(karyawan).build();  // Mengembalikan respons dengan status OK dan data karyawan yang diperbarui
    }

    // Endpoint untuk menghapus data karyawan berdasarkan ID
    @DELETE
    @Path("/{id}")  // Path parameter {id} digunakan untuk menghapus karyawan berdasarkan ID
    @Transactional  // Menandakan bahwa operasi ini memodifikasi database dan perlu transaksi
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = Karyawan.deleteById(id);  // Menghapus karyawan berdasarkan ID
        if (!deleted)  // Jika karyawan tidak ditemukan, kembalikan status NOT_FOUND
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.noContent().build();  // Mengembalikan respons dengan status NO_CONTENT (berhasil menghapus)
    }

    // Endpoint untuk mendapatkan gaji karyawan menggunakan function database
    @GET
    @Path("/function-gaji/{id}")  // Path parameter {id} digunakan untuk mendapatkan gaji karyawan berdasarkan ID
    public Response getGajiByFunction(@PathParam("id") Long id) {
        // Memanggil fungsi PostgreSQL "get_gaji" yang mengambil gaji berdasarkan ID
        Query query = em.createNativeQuery("SELECT get_gaji(:id)");
        query.setParameter("id", id);

        String hasil = (String) query.getSingleResult();  // Menjalankan query dan mendapatkan hasilnya (gaji karyawan dalam format string)

        return Response.ok(hasil).build();  // Mengembalikan respons dengan hasil gaji karyawan
    }

    // Endpoint untuk menambah data karyawan menggunakan function database
    @POST
    @Path("/function-insert")  // Path untuk menambah data karyawan menggunakan function
    @Transactional  // Menandakan bahwa operasi ini memodifikasi database dan perlu transaksi
    public Response insertWithFunction(KaryawanParam request) {
        // Memanggil fungsi PostgreSQL "insert_karyawan" yang menambah data karyawan
        Query query = em.createNativeQuery("SELECT insert_karyawan(:nama, :jabatan, :alamat)");
        query.setParameter("nama", request.nama);
        query.setParameter("jabatan", request.jabatan);
        query.setParameter("alamat", request.alamat);

        query.getSingleResult();  // Menjalankan query dan memastikan bahwa data telah ditambahkan (fungsi insert tidak mengembalikan nilai)

        return Response.status(Response.Status.CREATED)  // Mengembalikan status CREATED setelah data karyawan berhasil ditambahkan
            .entity("Data karyawan berhasil ditambahkan via FUNCTION!")
            .build();
    }
}