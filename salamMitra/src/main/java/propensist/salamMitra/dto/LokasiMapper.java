package propensist.salamMitra.dto;
import org.mapstruct.Mapper;

import propensist.salamMitra.dto.request.CreateLokasiRequestDTO;
import propensist.salamMitra.model.Lokasi;


@Mapper(componentModel = "spring")
public interface LokasiMapper {
    Lokasi createLokasiRequestDTOToLokasi(CreateLokasiRequestDTO createLokasiRequestDTO);
    
}
