package co.edu.uniandes.dse.parcialejemplo.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.parcialejemplo.dto.HabitacionDTO;
import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.services.HabitacionService;

/**
 * Clase que implementa el recurso "habitaciones". 
 */
@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

	@Autowired
	private HabitacionService habitacionService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea una nueva habitacion con la informacion que se recibe en el cuerpo de la
	 * petición y se regresa un objeto identico con un id auto-generado por la base
	 * de datos.
	 *
	 * @param habitacion {@link HabitacionDTO} - La habitacion que se desea guardar.
	 * @return JSON {@link HabitacionDTO} - La habitacion guardada con el atributo id
	 *         autogenerado.
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public HabitacionDTO create(@RequestBody HabitacionDTO habitacionDTO) throws IllegalOperationException, EntityNotFoundException {
		HabitacionEntity habitacionEntity = habitacionService.createHabitaciones(modelMapper.map(habitacionDTO, HabitacionEntity.class));
		return modelMapper.map(habitacionEntity, HabitacionDTO.class);
	}

   /**
	 * Busca y devuelve todas las habitacions que existen en la aplicacion.
	 *
	 * @return JSONArray {@link habitacionDTO} - Los libros encontrados en la
	 *         aplicación. Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<HabitacionDTO> findAll() {
		List<HabitacionEntity> habitacions = habitacionService.getHabitaciones();
		return modelMapper.map(habitacions, new TypeToken<List<HabitacionDTO>>() {
		}.getType());
	}
}