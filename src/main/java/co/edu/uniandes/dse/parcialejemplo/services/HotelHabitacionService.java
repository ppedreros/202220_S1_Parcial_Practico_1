package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Hotel y Habitacion.
 */

@Slf4j
@Service
public class HotelHabitacionService {

	@Autowired
	private HabitacionRepository habitacionRepository;

	@Autowired
	private HotelRepository hotelRepository;
	
	/**
	 * Asocia un Habitacion existente a un Hotel
	 *
	 * @param hotelId Identificador de la instancia de Hotel
	 * @param habitacionId   Identificador de la instancia de Habitacion
	 * @return Instancia de HabitacionEntity que fue asociada a Hotel
	 */

	@Transactional
	public HabitacionEntity addHabitacion(Long hotelId, Long habitacionId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una habitacion al hotel con id = {0}", hotelId);
		Optional<HotelEntity> hotelEntity = hotelRepository.findById(hotelId);
		Optional<HabitacionEntity> habitacionEntity = habitacionRepository.findById(habitacionId);

		if (hotelEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el hotel");

		if (habitacionEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la habitacion");

		hotelEntity.get().getHabitaciones().add(habitacionEntity.get());
		log.info("Termina proceso de asociarle una habitacion al hotel con id = {0}", hotelId);
		return habitacionEntity.get();
	}

    @Transactional
	public HabitacionEntity getHabitacion(Long hotelId, Long habitacionId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la habitacion con id = {0} del hotel con id = {1} " + hotelId, habitacionId);
		Optional<HotelEntity> hotelEntity = hotelRepository.findById(hotelId);
		Optional<HabitacionEntity> habitacionEntity = habitacionRepository.findById(habitacionId);

		if (hotelEntity.isEmpty())
			throw new EntityNotFoundException("No existe este médico");

		if (hotelEntity.isEmpty())
			throw new EntityNotFoundException("No existe esta habitacion");

		log.info("Termina proceso de consultar la habitacion con id = {0} del hotel con id = {1}" + hotelId, habitacionId);
		if (hotelEntity.get().getHabitaciones().contains(habitacionEntity.get()))
			return habitacionEntity.get();

		throw new IllegalOperationException("La habitacion no está asociada con el médico");
	}

}