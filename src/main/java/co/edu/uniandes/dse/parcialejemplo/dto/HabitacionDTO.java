package co.edu.uniandes.dse.parcialejemplo.dto;

import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class HabitacionDTO {
    private Long id;
	private int no_identificacion;
	private int no_personas;
    private int no_camas;
    private int no_banos;
} 