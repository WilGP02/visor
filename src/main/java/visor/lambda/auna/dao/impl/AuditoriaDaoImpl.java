package visor.lambda.auna.dao.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pvt.auna.visorhc.dao.AuditoriaDao;
import pvt.auna.visorhc.model.bean.request.AuditoriaTracerRequestBean;
import pvt.auna.visorhc.util.ConstanteUtil;
import visor.lambda.auna.util.ConstanteUtil;

@Repository
public class AuditoriaDaoImpl implements AuditoriaDao{
	@Autowired
	DataSource dataSource;
	

	private final Logger LOGGER = Logger.getLogger(getClass());
	
	/*
	 * Autor            : mbaldeon.consultorexterno@mdp.com.pe 
	 * Fecha de creacion: 24/10/18
	 * Descripcion      : Obtener idnumLog para la sesion
	 */
	@Override
	public Integer obtenerIdCodLogin(Integer idNumLog) throws Exception {
		Integer idNumLogResponse = 0;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.ESQUEMA).withCatalogName(ConstanteUtil.PAQUETE)
			.withFunctionName("FN_OBTENER_IDLOG");	
		    
		    idNumLogResponse = simpleJdbcCall.executeFunction(BigDecimal.class).intValue();		  

		} catch (DataAccessException de) {
			LOGGER.error(de.getMessage(), de);
		}
		return idNumLogResponse;
	}
	@Override
	public Integer insertarLoginAuditoriaTracer(AuditoriaTracerRequestBean objA,String sessionID) throws Exception {

		Integer op_codigo = -1;
		String op_mensaje;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dFormulario= sdf.parse(objA.getPv_fechaFormulario().replace("/","-"));
//	    String date = sdf.format(new Date());
//	    TIMESTAMP ts = new oracle.sql.TIMESTAMP(date);
//	    TIMESTAMP tsFormulario = new oracle.sql.TIMESTAMP(objA.getPv_fechaFormulario().replace("/","-"));
	    
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.ESQUEMA)
					.withCatalogName(ConstanteUtil.PAQUETE).withProcedureName("SP_INS_PTM_AUDIT_TRACER");
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("ip_id_num_session_id", sessionID, Types.VARCHAR);
			in.addValue("ip_nom_paciente", new String(objA.getPn_nomPaciente().getBytes(), "UTF-8"), Types.VARCHAR);
			in.addValue("ip_nhc", objA.getPv_nhc().trim(), Types.VARCHAR);
			in.addValue("ip_desc_formulario", new String(objA.getPv_descFormulario().getBytes(), "UTF-8"), Types.VARCHAR);
			in.addValue("ip_num_encuentro", objA.getPv_numEncuentro(), Types.VARCHAR);
			in.addValue("ip_peticion", objA.getPv_peticion(), Types.VARCHAR);
			in.addValue("ip_fecha_form", dFormulario, Types.DATE);
			in.addValue("ip_fecha_form_consulta",dFormulario , Types.DATE);
			in.addValue("ip_nom_medico", new String(objA.getPv_nomMedico().getBytes(), "UTF-8"), Types.VARCHAR);
			in.addValue("ip_usuario_his", new String(objA.getPv_usuarioHis().getBytes(), "UTF-8"), Types.VARCHAR);
			LOGGER.info("objA "+objA.toString());
			LOGGER.info("parameter MapSQL ip_fecha_form "+in.getValue("ip_fecha_form"));
			LOGGER.info("parameter MapSQL ip_fecha_form_consulta "+in.getValue("ip_fecha_form_consulta"));
//			in.addValue("ip_id_num_session_id", sessionID, Types.VARCHAR);
//			in.addValue("ip_nom_paciente", objA.getPn_nomPaciente(), Types.VARCHAR);
//			in.addValue("ip_nhc", objA.getPv_nhc(), Types.VARCHAR);
//			in.addValue("ip_desc_formulario",objA.getPv_descFormulario(), Types.VARCHAR);
//			in.addValue("ip_num_encuentro", objA.getPv_numEncuentro(), Types.VARCHAR);
//			in.addValue("ip_peticion", objA.getPv_peticion(), Types.VARCHAR);
//			in.addValue("ip_fecha_form", tsFormulario, Types.TIMESTAMP);
//			in.addValue("ip_fecha_form_consulta",tsFormulario , Types.TIMESTAMP);
//			in.addValue("ip_nom_medico", objA.getPv_nomMedico(), Types.VARCHAR);
//			in.addValue("ip_usuario_his", objA.getPv_usuarioHis(), Types.VARCHAR);
			
			
			Map<String, Object> out = simpleJdbcCall.execute(in);
			op_mensaje = (String)  out.get("OP_MENSAJE");	
			op_codigo = ((BigDecimal) out.get("OP_CODIGO")).intValue();
			
			
			if (op_codigo == -1) {
				LOGGER.error("\t * op_mensaje SP_INS_PTM_AUDIT_TRACER : [" + op_mensaje + "]");
				LOGGER.error("\t * op_codigo  SP_INS_PTM_AUDIT_TRACER : [" + op_codigo + "]");
			} 

					
		} catch (Exception e) {
			LOGGER.error("[[" + getClass().getName() + "]] " + e.getMessage());
		}
		
		return op_codigo;
	}


}
