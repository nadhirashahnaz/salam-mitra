package propensist.salamMitra.dto;
import org.mapstruct.Mapper;

import propensist.salamMitra.dto.request.CreateProgramKerjaRequestDTO;
import propensist.salamMitra.dto.request.UpdateProgramKerjaRequestDTO;
import propensist.salamMitra.model.ProgramKerja;


@Mapper(componentModel = "spring")
public interface ProgramKerjaMapper {
    ProgramKerja createProgramKerjaDTOToProgramKerja(CreateProgramKerjaRequestDTO createProgramKerjaRequestDTO);
    
    ProgramKerja updateProgramKerjaRequestDTOToProgramKerja(UpdateProgramKerjaRequestDTO updateProgramKerjaRequestDTO);

    UpdateProgramKerjaRequestDTO programKerjaToUpdateProgramKerjaRequestDTO(ProgramKerja programKerja);
}
