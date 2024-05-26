package propensist.salamMitra.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Admin.AdminRole;
import propensist.salamMitra.model.EmailDetails;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Notifikasi;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.repository.NotfikasiDb;

@Service
public class NotifikasiServiceImpl implements NotifikasiService {

    @Autowired
    private NotfikasiDb notifikasiDb;

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private EmailService emailService;

    @Override
    public void addNotifikasi(Pengajuan pengajuan) {
        String status = pengajuan.getStatus();
        Long idPengajuan = pengajuan.getId();

        Date currentDate = new Date();
        Notifikasi notifikasi = new Notifikasi();
        notifikasi.setNotifiedDate(currentDate);
        notifikasi.setIdPengajuan(idPengajuan);

        List<Admin> listAdmin = penggunaService.getAllAdmin();
        List<ProgramService> listProgramService = penggunaService.getAllProgramService();
        List<Manajemen> listManajemen = penggunaService.getAllManajemen();

        switch (status) {
            case ("Diajukan"):
                notifikasi.setMessage(
                        "Terdapat ajuan kerja sama baru! Silakan periksa ajuan dengan ID " + idPengajuan + ".");

                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.PROGRAM) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break;

            case ("Sedang Diperiksa"):
                notifikasi.setMessage("Ajuan dengan ID " + idPengajuan
                        + " sedang diperiksa. Silakan tunggu pemberitahuan selanjutnya!");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                sendMail(notifikasi);
                break;

            case ("Diteruskan ke Manajemen"):
                notifikasi.setMessage(
                        "Ajuan dengan ID " + idPengajuan + " diteruskan ke manajemen. Silakan periksa ajuan!");
                for (Manajemen manajemen : listManajemen) {
                    notifikasi.setPengguna(manajemen);
                    notifikasiDb.save(notifikasi);
                }
                break;

            case ("Menunggu Pencairan Dana oleh Program Service"):
                notifikasi.setMessage("Ajuan dengan ID " + idPengajuan
                        + " menunggu pencairan dana. Silakan lakukan pencairan ke rekening Salam Setara");
                for (ProgramService programService : listProgramService) {
                    notifikasi.setPengguna(programService);
                    notifikasiDb.save(notifikasi);
                }

                Notifikasi notifikasiMitra = new Notifikasi();
                notifikasiMitra.setNotifiedDate(currentDate);
                notifikasiMitra.setIdPengajuan(idPengajuan);

                notifikasiMitra.setMessage(
                        "Ajuan Anda dengan ID " + idPengajuan + " telah disetujui. Silakan tunggu dana dicairkan!");
                notifikasiMitra.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasiMitra);
                sendMail(notifikasiMitra);
                break;

            case ("Menunggu Pencairan Dana oleh Admin Finance"):
                notifikasi.setMessage("Dana ajuan dengan ID " + idPengajuan
                        + " telah dicairkan ke rekening Salam Setara. Silakan lakukan pencairan dana ke rekening mitra!");
                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.FINANCE) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break;

            case ("Menunggu Laporan"):
                Date tanggalLaporan = pengajuan.getTanggalLaporan();
                Date now = new Date();
                long difference_In_Time = tanggalLaporan.getTime() - now.getTime();
                long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

                if (now.after(tanggalLaporan)) {
                    String message = "Batas waktu unggah laporan kerja sama dengan ID " + pengajuan.getId() 
                            + " sudah lewat. Segera unggah laporan kerja sama.";
                    notifikasi.setMessage(message);
                    notifikasi.setPengguna(pengajuan.getMitra());
                    notifikasiDb.save(notifikasi);
                    sendMail(notifikasi);
                } else {
                    notifikasi.setMessage("Dana ajuan Anda dengan ID " + idPengajuan
                            + " telah dicairkan. Batas waktu unggah laporan kerja sama tinggal " 
                            + difference_In_Days + " hari!");
                    notifikasi.setPengguna(pengajuan.getMitra());
                    notifikasiDb.save(notifikasi);
                    sendMail(notifikasi);
                }
                break;

            case ("Selesai"):
                notifikasi.setMessage(
                        "Laporan ajuan dengan ID" + idPengajuan + " telah diunggah. Kerja sama telah selesai.");
                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.PROGRAM) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break;

            case ("Perlu Revisi"):
                notifikasi.setMessage("Ajuan Anda dengan ID " + idPengajuan
                        + " perlu direvisi! Silakan cek komentar untuk melakukan revisi.");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                sendMail(notifikasi);
                break;

            case ("Dibatalkan"):
                notifikasi.setMessage("Ajuan dengan ID " + idPengajuan + " telah dibatalkan oleh mitra.");

                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.PROGRAM) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break;

            case ("Ditolak"):
                notifikasi.setMessage("Mohon maaf. Ajuan Anda dengan ID " + idPengajuan
                        + " telah ditolak! Silakan ajukan ajuan lainnya.");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                sendMail(notifikasi);
                break;
        }
    }

    @Override
    public List<Notifikasi> getNotifikasiByUser(Pengguna pengguna) {
        return notifikasiDb.findByPenggunaOrderByCreatedAtDesc(pengguna);
    }

    @Override
    public Optional<Notifikasi> getNotifikasiById(Long id) {
        return notifikasiDb.findById(id);
    }

    @Override
    public void updateNotifikasi(Notifikasi notifikasi) {
        notifikasiDb.save(notifikasi);
    }

    private void sendMail(Notifikasi notifikasi) {

        EmailDetails details = new EmailDetails();

        details.setRecipient(notifikasi.getPengguna().getEmail());

        if (notifikasi.getMessage().contains("batas waktu")) {
            details.setSubject("Pemberitahuan Batas Waktu Unggah Laporan Kerja Sama");

            String message = notifikasi.getMessage().trim().replaceAll("[^a-zA-Z0-9]+$", "");
            String messageBody = String.format(
                    """
                            Halo, %s!


                            Melalui email ini, kami ingin menyampaikan bahwa terdapat pemberitahuan baru yang perlu Anda perhatikan terkait dengan kerja sama yang Anda ajukan. %s.
                            Kunjungi Situs https://salam-mitra.up.railway.app/ untuk mendapatkan informasi lebih lanjut.
                            Demikian informasi ini kami sampaikan. Terima kasih atas perhatian dan kerja samanya.

                            Salam,
                            Salam Setara
                            """,
                    notifikasi.getPengguna().getUsername(), message);

            details.setMessageBody(messageBody);

        } else {
            details.setSubject(String.format("Perubahan Status Ajuan Kerja Sama ID %d", notifikasi.getIdPengajuan()));

            String message = notifikasi.getMessage().trim().replaceAll("[^a-zA-Z0-9]+$", "");
            String messageBody = String.format(
                    """
                            Halo, %s!

                            Melalui email ini, kami ingin menyampaikan bahwa terdapat pemberitahuan baru yang perlu Anda perhatikan terkait dengan kerja sama yang Anda ajukan. %s.
                            Kunjungi Situs https://salam-mitra.up.railway.app/ untuk mendapatkan informasi lebih lanjut.
                            Demikian informasi ini kami sampaikan. Terima kasih atas perhatian dan kerja samanya.

                            Salam,
                            Salam Setara
                            """,
                    notifikasi.getPengguna().getUsername(), message);

            details.setMessageBody(messageBody);
        }

        emailService.sendSimpleMail(details);
    }
}
