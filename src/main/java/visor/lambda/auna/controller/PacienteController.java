package visor.lambda.auna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PacienteController {
	
	@Autowired
	PacienteService pacienteService;

	@RequestMapping(value = "/api/getPacientes", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<PacienteResponse> getPacientes(@RequestBody PacienteRequest objA_request) throws Exception {
		long tiempoInicial = System.currentTimeMillis();
			
		ResponseEntity<PacienteResponse> objA_response =pacienteService.getListPacientes(objA_request);
		
		return objA_response;
	}
	

}
