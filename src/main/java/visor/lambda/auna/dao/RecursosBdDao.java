package visor.lambda.auna.dao;

import java.util.List;
import java.util.Map;

import pvt.auna.visorhc.model.bean.ParametroBean;
import pvt.auna.visorhc.model.bean.UserLoginAuditBean;
import pvt.auna.visorhc.model.bean.WebServiceBean;

public interface RecursosBdDao {
	
	Map<String, WebServiceBean> obtenerWebService() throws Exception;
	
	public Integer insertarLoginAudit(UserLoginAuditBean objA) throws Exception;
	
	public List<ParametroBean> listarParametros(Integer codParametro, String codGrupo, String estado) throws Exception;


}
