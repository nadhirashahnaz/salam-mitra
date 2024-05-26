package propensist.salamMitra.dto;

import org.mapstruct.Mapper;

import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.model.Mitra;

@Mapper(componentModel = "spring")
public interface MitraMapper {

    Mitra createMitraRequestDTOToAdmin(CreateMitraRequestDTO createMitraRequestDTO);
    
}
