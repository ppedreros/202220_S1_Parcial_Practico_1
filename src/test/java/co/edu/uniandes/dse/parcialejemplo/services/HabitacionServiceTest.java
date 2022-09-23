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

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Habitaciones
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HabitacionService.class)
class HabitacionServiceTest {

	@Autowired
	private HabitacionService habitacionService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

    /**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from HabitacionEntity");
	}

	/**
	 * Prueba para crear una Habitacion
	 */
	@Test
	void testCreateHabitacion() throws EntityNotFoundException, IllegalOperationException {
		HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
        newEntity.setNo_banos(2);
        newEntity.setNo_camas(3);
		HabitacionEntity result = habitacionService.createHabitaciones(newEntity);
		assertNotNull(result);
		HabitacionEntity entity = entityManager.find(HabitacionEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNo_identificacion(), entity.getNo_identificacion());
		assertEquals(newEntity.getNo_banos(), entity.getNo_banos());
		assertEquals(newEntity.getNo_camas(), entity.getNo_camas());
        assertEquals(newEntity.getNo_personas(), entity.getNo_personas());
	}

	/**
	 * Prueba para crear un Habitacion con más baños que camas.
	 */
	@Test
	void testCreateHabitacionMasBanosQueCamas() {
		assertThrows(IllegalOperationException.class, () -> {
			HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
			newEntity.setNo_banos(3);
            newEntity.setNo_camas(2);
			habitacionService.createHabitaciones(newEntity);
		});
	} 
}