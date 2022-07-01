package visor.lambda.auna.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteReponse {
	private Integer codmsje;
	private String desmsje;
	private List<PacienteResponse> listaPacientes;

}
