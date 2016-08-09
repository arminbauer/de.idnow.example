package models.repositories;

import com.avaje.ebean.annotation.Transactional;
import models.Identification;
import models.dtos.IdentificationDto;

import java.util.List;
import java.util.stream.Collectors;

public class IdentificationsRepositoryImpl implements IdentificationsRepository {
	@Override
	public List<IdentificationDto> findAll() {
		return Identification.find.all()
				.stream()
				.map(i -> new IdentificationDto(i.id, i.name, i.time, i.waitingTime, i.companyId))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void add(IdentificationDto dto) {
		Identification entity = new Identification();
		entity.id = dto.getId();
		entity.name = dto.getName();
		entity.time = dto.getTime();
		entity.waitingTime = dto.getWaitingTime();
		entity.companyId = dto.getCompanyId();

		entity.save();
	}
}
