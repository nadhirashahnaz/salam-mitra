package propensist.salamMitra.dto;

import org.mapstruct.Mapper;

import propensist.salamMitra.dto.request.CreateAdminRequestDTO;
import propensist.salamMitra.model.Admin;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin createAdminRequestDTOToAdmin(CreateAdminRequestDTO createAdminRequestDTO);
    
}
