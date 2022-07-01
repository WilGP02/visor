package visor.lambda.auna.dao.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pvt.auna.visorhc.dao.RecursosBdDao;
import pvt.auna.visorhc.model.bean.ParametroBean;
import pvt.auna.visorhc.model.bean.UserLoginAuditBean;
import pvt.auna.visorhc.model.bean.WebServiceBean;
import pvt.auna.visorhc.model.mapper.ParametroRowMapper;
import pvt.auna.visorhc.model.mapper.WebServiceRowMapper;
import pvt.auna.visorhc.util.ConstanteUtil;
import visor.lambda.auna.util.ConstanteUtil;

@Repository
public class RecursosBdDaoImpl implements RecursosBdDao{
	private final Logger LOGGER = Logger.getLogger(getClass());
	
	@Autowired
	DataSource dataSource;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, WebServiceBean> obtenerWebService() throws Exception {
		Map<String, Object> out = new HashMap<>();
		Map<String, WebServiceBean> outSalida = new HashMap<>();
		List<WebServiceBean> lista = new  ArrayList<>();
		try {			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.ESQUEMA).withCatalogName(ConstanteUtil.PAQUETE)
					.withProcedureName("SP_PTM_WS_SERVICES")					
					.returningResultSet("PO_KURSOR", new WebServiceRowMapper());
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("PN_COD_SERVICE", null, Types.NUMERIC);
			in.addValue("PN_USUARIO", null, Types.VARCHAR);
			in.addValue("PN_TIPO_SERVICIO", null, Types.VARCHAR);
			in.addValue("PN_COD_ESTADO", "2", Types.NUMERIC);
		
			out = simpleJdbcCall.execute(in);			
			lista = (List<WebServiceBean>) out.get("PO_KURSOR");
			for (WebServiceBean bean : lista) {
				outSalida.put(bean.getCodService().toString(), bean);
			}
		}catch (Exception e) {
			LOGGER.error("[RecursosBdDaoImpl]-obtenerWebService() -->Error al consultar: ", e);
		}
		
		
		return outSalida;
	}

	@Override
	public Integer insertarLoginAudit(UserLoginAuditBean objA) throws Exception {

		Integer op_codigo = -1;
		String op_mensaje;
		
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.ESQUEMA)
					.withCatalogName(ConstanteUtil.PAQUETE).withProcedureName("SP_PTM_I_LOGIN");
			
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("ip_sessionid_vch", objA.getSessionid(), Types.VARCHAR);
			in.addValue("ip_username_vch", objA.getUsername(), Types.VARCHAR);
			in.addValue("ip_codsede_vch", objA.getCodsede(), Types.VARCHAR);
			
			Map<String, Object> out = simpleJdbcCall.execute(in);
			op_mensaje = (String)  out.get("OP_MENSAJE");	
			op_codigo = ((BigDecimal) out.get("OP_CODIGO")).intValue();
			
			
			if (op_codigo == -1) {
				LOGGER.error("\t * op_mensaje SP_PTM_I_LOGIN : [" + op_mensaje + "]");
				LOGGER.error("\t * op_codigo  SP_PTM_I_LOGIN : [" + op_codigo + "]");
			} 

					
		} catch (Exception e) {
			LOGGER.error("[[" + getClass().getName() + "]] " + e.getMessage());
		}
		
		return op_codigo;
	}
	
	@SuppressWarnings("unchecked")
	@Override	
	public List<ParametroBean> listarParametros(Integer codParametro, String codGrupo, String estado) throws Exception {		
		
		Map<String, Object> out = new HashMap<>();
		List<ParametroBean> lista = new  ArrayList<>();
		try {			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.ESQUEMA).withCatalogName(ConstanteUtil.PAQUETE)
					.withProcedureName("SP_PTM_OBTENER_PARAMETROS")					
					.returningResultSet("PO_KURSOR", new ParametroRowMapper());
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("PN_COD_PARAMETRO", codParametro, Types.NUMERIC);//PN_COD_PARAMETRO
			in.addValue("PN_COD_GRUPO", codGrupo, Types.VARCHAR);//PN_COD_GRUPO
			in.addValue("PN_ESTADO", estado, Types.VARCHAR);//PN_ESTADO
			
			out = simpleJdbcCall.execute(in);			
			lista = (List<ParametroBean>) out.get("PO_KURSOR");
		}catch (Exception e) {
			LOGGER.error("Error al consultar", e);
		}

		return lista;
	}

}
