package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HabitacionService {

	@Autowired
	HabitacionRepository habitacionRepository;
	
	/**
	 * Guardar una nueva habitacion
	 *
	 * @param habitacionEntity La entidad de tipo habitacion de la nueva habitacion a persistir.
	 * @return La entidad luego de persistirla
	 */
	@Transactional
	public HabitacionEntity createHabitaciones(HabitacionEntity habitacionEntity) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de creación de la habitacion");
		
		if (habitacionEntity.getNo_banos() > habitacionEntity.getNo_camas()){
			throw new IllegalOperationException("El número de baños no puede ser mayor al de camas");
        }

		log.info("Termina proceso de creación de la habitacion");
		return habitacionRepository.save(habitacionEntity);
	}
}