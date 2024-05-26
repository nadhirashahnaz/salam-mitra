package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pengajuan")
public class Pengajuan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "waktuDibuat", nullable = false)
    @CreationTimestamp
    private Date waktuDibuat;

    @Column(name = "status", nullable = false)
    private String status;


    @Column(name = "namaProgram", nullable = false)
    private String namaProgram;

    @Column(name = "namaMitra", nullable = false)
    private String namaMitra;

    @Column(name = "kategori", nullable = false)
    private String kategori;

    @Column(name = "namaPIC", nullable = false)
    private String namaPIC;

    @Column(name = "kontakPIC", nullable = false)
    private String kontakPIC;
    
    @Column(name = "bank", nullable = false)
    private String bank;

    @Column(name = "namaPemilikRekening", nullable = false)
    private String namaPemilikRekening;

    @Column(name = "nomorRekening", nullable = false)
    private Long nomorRekening;

    @Column(name = "alamatKantor", nullable = false)
    private String alamatKantor;

    @Lob
    @Column(name = "ktpPIC", nullable = false)
    private byte[] ktpPIC;
    @Transient //For KTP
    private String imageBase64; // Add this field to store Base64 encoded image

   
    
    //------------
    @Column(name = "jumlahKebutuhanOperasional", nullable = false)
    private Long jumlahKebutuhanOperasional;

    @Column(name = "nominalKebutuhanDana", nullable = false)
    private Long nominalKebutuhanDana;

    @OneToMany(mappedBy = "pengajuan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<KebutuhanDana> listKebutuhanDana;


    

    @Lob
    @Column(name = "rab", nullable = false)
    private byte[] rab;
    @Transient //For RAB
    private String rabBase64; // Add this field to store Base64 encoded image

    @Lob
    @Column(name = "dokumen", nullable = false)
    private byte[] dokumen;
    @Transient //For Document
    private String dokumenBase64; 
     
    @Column(name = "tanggalMulai", nullable = false)
    private Date tanggalMulai;
        
    @Column(name = "tanggalSelesai", nullable = false)
    private Date tanggalSelesai;
        
    @Column(name = "tanggalLaporan", nullable = false)
    private Date tanggalLaporan;

    @Column(name = "provinsi", nullable = false)
    private String provinsi;  

    @Column(name = "kabupatenKota", nullable = false)
    private String kabupatenKota;  
    
    @Column(name = "kecamatan", nullable = false)
    private String kecamatan;  
    
    @Column(name = "alamatLengkap", nullable = false)
    private String alamatLengkap; 

    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "komentar")
    private String komentar; 

    @Lob
    @Column(name = "laporan")
    private byte[] laporan;
    @Transient //For KTP
    private String laporanBase64; // Add this field to store Base64 encoded image

    @Column(name = "reviewedBy")
    private String reviewedBy; 

    @OneToOne(mappedBy = "pengajuan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Pencairan pencairan;

    
    @Lob
    @Column(name = "bukuTabungan", nullable = false)
    private byte[] bukuTabungan;
    @Transient 
    private String bukuTabunganBase64; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mitra", referencedColumnName = "id")
    private Mitra mitra;
}
