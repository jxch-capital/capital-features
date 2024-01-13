package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.NoteBook5mDto;
import org.jxch.capital.domain.po.NoteBook5m;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteBook5mMapper {
    NoteBook5m toNoteBook5m(NoteBook5mDto dto);

    List<NoteBook5m> toNoteBook5m(List<NoteBook5mDto> dto);

    NoteBook5mDto toNoteBook5mDto(NoteBook5m noteBook5m);

    List<NoteBook5mDto> toNoteBook5mDto(List<NoteBook5m> list);
}
