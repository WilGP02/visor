package visor.lambda.auna.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class PacienteServiceImpl implements PacienteService {
	private final Logger LOGGER = Logger.getLogger(getClass());
	
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RecursosBdService recursoBdService;
	@Autowired
	ConsumoWSSixService consumoWSSixService;
	@Autowired
	CallCryptoService cripBack;
	
	@Override
	public ResponseEntity<PacienteResponse> getListPacientes(PacienteRequest objA_request) throws Exception {
		ResponseEntity<PacienteResponse> objA_response;
		PacienteResponseBean pacienteResponseBean = new PacienteResponseBean();
		try {
			WebServiceBean wsBean = recursoBdService.listarWebServices(WebServiceEnum.WS_BUSCAR_PACIENTE.getStrPv_valor());
			HashMap<Integer, Object> parameterInput = new HashMap<>();
			parameterInput.put(1, objA_request.getPn_codper()!=null? objA_request.getPn_codper() : ConstanteUtil.NUM_DEFAULT_PARAM);
			parameterInput.put(2, objA_request.getPv_codcen()!=null? objA_request.getPv_codcen() : ConstanteUtil.NUM_DEFAULT_PARAM);
			parameterInput.put(3, objA_request.getPv_permiso()!=null? objA_request.getPv_permiso() : ConstanteUtil.VCH_DEFAULT_PARAM);
			parameterInput.put(4, objA_request.getPv_tipobusqueda()!=null? objA_request.getPv_tipobusqueda() : ConstanteUtil.VCH_DEFAULT_PARAM);
			parameterInput.put(5, objA_request.getPv_tipoencuentro()!=null? objA_request.getPv_tipoencuentro() : ConstanteUtil.VCH_DEFAULT_PARAM);
			parameterInput.put(6, objA_request.getPv_param1()!=null ? objA_request.getPv_param1() : ConstanteUtil.VCH_DEFAULT_PARAM);
			parameterInput.put(7, objA_request.getPv_param2()!=null ? objA_request.getPv_param2() : ConstanteUtil.VCH_DEFAULT_PARAM);
			parameterInput.put(8, objA_request.getPv_param3()!=null ? objA_request.getPv_param3() : ConstanteUtil.VCH_DEFAULT_PARAM);
			parameterInput.put(9, objA_request.getPv_idgarante()!=null ? objA_request.getPv_idgarante() : ConstanteUtil.NUM_DEFAULT_PARAM);
			parameterInput.put(10, objA_request.getPn_num_pag());
			parameterInput.put(11, objA_request.getPn_tam_pag());
 
			String responseJson =consumoWSSixService.consumoWsSix(wsBean.getUrlService(), parameterInput);
			Gson gson = new Gson();
			JsonArray jsonArray = gson.fromJson(responseJson, JsonArray.class);
			if(jsonArray.size()>0) {
				JsonElement element = jsonArray.get(0);
				pacienteResponseBean = gson.fromJson(element.toString(), PacienteResponseBean.class);
			}else {
				pacienteResponseBean = new PacienteResponseBean();
				pacienteResponseBean.setPo_codigo("-1");
				pacienteResponseBean.setPo_mensaje("Respuesta vacia");
				LOGGER.error("[PacienteServiceImpl] - getListPacientes: Array de resultados vacio");				
			}
 
			if((pacienteResponseBean!=null) && Integer.parseInt(pacienteResponseBean.getPo_codigo())==1){
				objA_response = new ResponseEntity<PacienteResponseBean>(new PacienteResponseBean(),HttpStatus.NOT_FOUND);
			}else {
				objA_response = new ResponseEntity<PacienteResponseBean>(new PacienteResponseBean(),HttpStatus.NO_CONTENT);
			}	
			
			
		} catch (Exception e) {
			objA_response = new ResponseEntity<PacienteResponseBean>(new PacienteResponseBean(), HttpStatus.INTERNAL_SERVER_ERROR);
			LOGGER.error("[UsuarioServiceImpl] - loginWeb: " + e);
			throw e;
		}
		
		return objA_response;
	}

}
