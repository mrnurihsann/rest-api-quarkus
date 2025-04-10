package org.study.resource; // Menyatakan bahwa file ini berada di package 'org.study.resource'

import java.util.List;

import org.study.entity.Karyawan; // Mengimpor entitas Karyawan
import org.study.param.KaryawanParam; // Mengimpor class param untuk menerima data input

import jakarta.transaction.Transactional; // Digunakan untuk mengatur transaksi database
import jakarta.ws.rs.Consumes; // Mengimpor berbagai anotasi REST dari Jakarta RESTful Web Services
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// Resource endpoint untuk entitas Karyawan
@Path("/karyawan") // Menentukan path utama untuk semua endpoint dalam class ini
@Produces(MediaType.APPLICATION_JSON) // Semua response akan dikembalikan dalam format JSON
@Consumes(MediaType.APPLICATION_JSON) // Semua request body yang diterima harus dalam format JSON
public class KaryawanResource {

    // Endpoint GET /karyawan
    // Mengembalikan daftar semua karyawan
    @GET
    public List<Karyawan> getAll() {
        return Karyawan.listAll(); // Menggunakan Panache untuk mengambil semua data
    }

    // Endpoint GET /karyawan/{id}
    // Mengambil data karyawan berdasarkan ID
    @GET
    @Path("/{id}")
    public Karyawan getById(@PathParam("id") Long id) {
        return Karyawan.findById(id); // Mencari berdasarkan ID
    }

    // Endpoint GET /karyawan/search?nama=nama
    // Mencari karyawan berdasarkan nama
    @GET
    @Path("/search")
    public List<Karyawan> getByNama(@QueryParam("nama") String nama) {
        return Karyawan.list("nama", nama); // Mencari berdasarkan field 'nama'
    }

    // Endpoint POST /karyawan
    // Menambahkan karyawan baru
    @POST
    @Transactional // Dibutuhkan agar operasi persist bisa dijalankan dalam transaksi
    public Response create(KaryawanParam request) {
        // Membuat objek karyawan baru dari data request
        Karyawan karyawan = new Karyawan();
        karyawan.nama = request.nama;
        karyawan.jabatan = request.jabatan;
        karyawan.alamat = request.alamat;

        karyawan.persist(); // Menyimpan data ke database
        return Response.status(Response.Status.CREATED).entity(karyawan).build(); // Mengembalikan status 201 + data karyawan
    }

    // Endpoint PUT /karyawan/{id}
    // Mengupdate data karyawan berdasarkan ID
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Karyawan updated) {
        Karyawan karyawan = Karyawan.findById(id);
        if (karyawan == null) 
            return Response.status(Response.Status.NOT_FOUND).build(); // Jika tidak ditemukan, kembalikan 404

        // Update field-field
        karyawan.nama = updated.nama;
        karyawan.jabatan = updated.jabatan;
        karyawan.alamat = updated.alamat;

        return Response.ok(karyawan).build(); // Kembalikan response dengan data yang sudah diupdate
    }

    // Endpoint DELETE /karyawan/{id}
    // Menghapus karyawan berdasarkan ID
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = Karyawan.deleteById(id); // Menghapus data berdasarkan ID
        if (!deleted) 
            return Response.status(Response.Status.NOT_FOUND).build(); // Jika tidak ada data yang dihapus, 404

        return Response.noContent().build(); // Kembalikan response tanpa body (204)
    }
}
