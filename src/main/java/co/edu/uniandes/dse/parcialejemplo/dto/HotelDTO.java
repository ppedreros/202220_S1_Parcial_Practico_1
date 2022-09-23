package co.edu.uniandes.dse.parcialejemplo.dto;

import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class HotelDTO {
    private Long id;
	private String nombre;
    private String direccion;
    private int no_estrellas;
} 