package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRepository;
	
	/**
	 * Guardar un nuevo hotel
	 *
	 * @param hotelEntity La entidad de tipo hotel del nuevo hotel a persistir.
	 * @return La entidad luego de persistirla
	 */
	@Transactional
	public HotelEntity createHoteles(HotelEntity hotelEntity) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de creación del hotel");
		
		if (!hotelRepository.findByNombre(hotelEntity.getNombre()).isEmpty())
			throw new IllegalOperationException("Hotel nombre already exists");
			

		log.info("Termina proceso de creación del hotel");
		return hotelRepository.save(hotelEntity);
	}
}
