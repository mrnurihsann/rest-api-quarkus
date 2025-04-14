# Quarkus REST API - Karyawan Management

Proyek ini merupakan REST API sederhana untuk manajemen data karyawan menggunakan **Quarkus**, **Hibernate Panache**, dan **PostgreSQL**.

## 🔧 Teknologi yang Digunakan

- [Quarkus](https://quarkus.io/) - Supersonic Subatomic Java
- Hibernate ORM Panache - ORM ringan dengan sintaks deklaratif
- Jakarta REST (JAX-RS) - Untuk API endpoint
- PostgreSQL - Database relasional
- Maven - Build tool

## 📁 Struktur Direktori

📦 quarkus-karyawan-api
├── 📁 src
│   └── 📁 main
│       ├── 📁 java
│       │   └── 📁 org
│       │       └── 📁 study
│       │           ├── 📁 entity         # Menyimpan class entity (model data)
│       │           │   └── Karyawan.java
│       │           ├── 📁 param          # Menyimpan class untuk parameter input (DTO)
│       │           │   └── KaryawanParam.java
│       │           └── 📁 resource       # Menyimpan REST controller (endpoint API)
│       │               └── KaryawanResource.java
│       └── 📁 resources
│           ├── application.properties   # Konfigurasi Quarkus dan database
│           └── META-INF
│               └── resources            # Untuk menyimpan static file (jika ada)
├── 📁 target                            # Folder hasil build (otomatis oleh Maven)
├── .gitignore                           # Daftar file/folder yang tidak di-push ke GitHub
├── mvnw                                 # Wrapper untuk menjalankan Maven (Linux/Mac)
├── mvnw.cmd                             # Wrapper untuk menjalankan Maven (Windows)
├── pom.xml                              # File konfigurasi dan dependency Maven
└── README.md                            # Dokumentasi project ini (penjelasan dan cara pakai)


## ⚙️ Konfigurasi Database (application.properties)

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=postgres
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/db_belajar

quarkus.hibernate-orm.database.generation=update
quarkus.hibernate-orm.log.sql=true
quarkus.datasource.db-version=10.0.0

🚀 Menjalankan Aplikasi
Pastikan PostgreSQL aktif dan database db_belajar telah dibuat.

Jalankan aplikasi dengan perintah:
mvn quarkus:dev

🔄 Endpoint API
Method	Endpoint	Deskripsi
GET	/karyawan	Menampilkan semua karyawan
GET	/karyawan/{id}	Menampilkan karyawan by ID
GET	/karyawan/search?nama=xxx	Cari karyawan berdasarkan nama
POST	/karyawan	Tambah karyawan baru
PUT	/karyawan/{id}	Update data karyawan
DELETE	/karyawan/{id}	Hapus karyawan berdasarkan ID

📬 Contoh Request JSON
POST /karyawan
{
  "nama": "John Doe",
  "jabatan": "Programmer",
  "alamat": "Yogyakarta"
}

🧑‍💻 Author
Nama: mrnurihsann
LinkedIn/GitHub: github.com/mrnurihsann
