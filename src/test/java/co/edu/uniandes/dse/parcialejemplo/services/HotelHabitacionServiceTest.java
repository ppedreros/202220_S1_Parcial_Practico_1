package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Hotel - Habitaciones
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ HotelHabitacionService.class, HabitacionService.class })
class HotelHabitacionServiceTest {

	@Autowired
	private HotelHabitacionService hotelHabitacionService;

	@Autowired
	private HabitacionService habitacionService;

    @Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private HotelEntity hotel = new HotelEntity();

    /**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		hotel = factory.manufacturePojo(HotelEntity.class);
		entityManager.persist(hotel);
	}


	/**
	 * Prueba para asociar una habitacion a un hotel.
	 *
	 */
	@Test
	void testAddHabitacion() throws EntityNotFoundException, IllegalOperationException {
		HabitacionEntity newHabitacion = factory.manufacturePojo(HabitacionEntity.class);
		newHabitacion.setNo_banos(2);
        newHabitacion.setNo_camas(3);
		habitacionService.createHabitaciones(newHabitacion);

		HabitacionEntity habitacionEntity = hotelHabitacionService.addHabitacion(hotel.getId(), newHabitacion.getId());
		assertNotNull(habitacionEntity);

		assertEquals(habitacionEntity.getId(), newHabitacion.getId());
		assertEquals(habitacionEntity.getNo_banos(), newHabitacion.getNo_banos());
		assertEquals(habitacionEntity.getNo_camas(), newHabitacion.getNo_camas());
        assertEquals(habitacionEntity.getNo_personas(), newHabitacion.getNo_personas());
        assertEquals(habitacionEntity.getNo_identificacion(), newHabitacion.getNo_identificacion());

		HabitacionEntity lastHabitacion = hotelHabitacionService.getHabitacion(hotel.getId(), newHabitacion.getId());

		assertEquals(lastHabitacion.getId(), newHabitacion.getId());
		assertEquals(lastHabitacion.getNo_banos(), newHabitacion.getNo_banos());
		assertEquals(lastHabitacion.getNo_camas(), newHabitacion.getNo_camas());
        assertEquals(lastHabitacion.getNo_personas(), newHabitacion.getNo_personas());
        assertEquals(lastHabitacion.getNo_identificacion(), newHabitacion.getNo_identificacion());

	}
	

	/**
	 * Prueba para asociar una habitacion a un hotel que no existe.
	 *
	 */

	@Test
	void testAddHabitacionInvalidHotel() {
		assertThrows(EntityNotFoundException.class, () -> {
			HabitacionEntity newHabitacion = factory.manufacturePojo(HabitacionEntity.class);
			habitacionService.createHabitaciones(newHabitacion);
			hotelHabitacionService.addHabitacion(0L, newHabitacion.getId());
		});
	}

    /**
	 * Prueba para asociar una habitacion que no existe a un hotel.
	 *
	 */

    @Test
	void testAddHabitacionInvalidHabitacion() {
		assertThrows(EntityNotFoundException.class, () -> {
			hotelHabitacionService.addHabitacion(hotel.getId(), 0L);
		});
	}

}