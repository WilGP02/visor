package visor.lambda.auna.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteRequest {
	private String idpaciente;
	private Integer nhc;
	private Integer codsede;
	private String fechaingreso;
	private String horaingreso;
	private Integer numcama;
	private String nompaciente;
	private String apellpaciente;
	private Integer edad;
	private String diagnostico;
	private Integer cantpendimg;
	private Integer cantprocimg;
	private Integer cantfinaimg;
	private Integer cantpendlab;
	private Integer cantproclab;
	private Integer cantfinalab;
	private Integer cantpendtec;
	private Integer cantproctec;
	private Integer cantfinatec;
	private String garante;

	
}
