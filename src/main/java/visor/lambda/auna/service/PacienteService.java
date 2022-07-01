package visor.lambda.auna.service;

import org.springframework.http.ResponseEntity;

public interface PacienteService {
	public ResponseEntity<PacienteResponse> getListPacientes (PacienteRequest objA_request) throws Exception;
}
