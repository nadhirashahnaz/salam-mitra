package propensist.salamMitra.dto;

import org.mapstruct.Mapper;

import propensist.salamMitra.dto.request.CreatePencairanRequestDTO;
import propensist.salamMitra.model.Pencairan;

@Mapper(componentModel = "spring")
public interface PencairanMapper {

    Pencairan createPencairanRequestDTOToPencairan(CreatePencairanRequestDTO createPencairanRequestDTO);
    
}
