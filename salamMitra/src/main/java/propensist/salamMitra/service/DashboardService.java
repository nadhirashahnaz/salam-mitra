package propensist.salamMitra.service;

import java.time.LocalDate;
import java.util.Map;

public interface DashboardService {
    Map<String, Integer> getTotalPengajuanForMonth(LocalDate date);
    Map<String, Integer> getJumlahProgramKerjaPerKategori();
    
}
