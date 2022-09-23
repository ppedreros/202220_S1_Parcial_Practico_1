package co.edu.uniandes.dse.parcialejemplo.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una habitacion en la persistencia
 */

@Getter
@Setter
@Entity
public class HabitacionEntity extends BaseEntity {

	private int no_identificacion;
	private int no_personas;
    private int no_camas;
    private int no_banos;

	@PodamExclude
	@ManyToOne
	private HotelEntity hotel;

}