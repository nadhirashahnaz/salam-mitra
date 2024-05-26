package propensist.salamMitra.dto;
import org.mapstruct.Mapper;

import propensist.salamMitra.dto.request.CreatePengajuanRequestDTO;
import propensist.salamMitra.dto.request.UpdatePengajuanRequestDTO;
import propensist.salamMitra.model.Pengajuan;


@Mapper(componentModel = "spring")
public interface PengajuanMapper {
    Pengajuan createPengajuanRequestDTOToPengajuan(CreatePengajuanRequestDTO createPengajuanRequestDTO);
    Pengajuan updatePengajuanRequestDTOToPengajuan(UpdatePengajuanRequestDTO updatePengajuanRequestDTO);
    UpdatePengajuanRequestDTO pengajuanToUpdatePengajuanRequestDTO(Pengajuan pengajuan);
}
