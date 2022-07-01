package visor.lambda.auna.dao;

import pvt.auna.visorhc.model.bean.request.AuditoriaTracerRequestBean;

public interface AuditoriaDao {
	public Integer obtenerIdCodLogin(Integer idNumLog) throws Exception;
	public Integer insertarLoginAuditoriaTracer(AuditoriaTracerRequestBean objA,String sessionID) throws Exception;


}
