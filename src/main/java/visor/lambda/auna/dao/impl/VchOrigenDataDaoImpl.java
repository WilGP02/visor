package visor.lambda.auna.dao.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import pvt.auna.visorhc.dao.VchOrigenDataDao;
@Repository
public class VchOrigenDataDaoImpl implements VchOrigenDataDao{

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Cacheable ("vchOrigenDataDao")
	public VchOrigenDataDao obtenerDatosWebServicexCodigo(String strA_codWebService, Integer idNumEve, Integer idNumLog)
			throws Exception {
//		VchOrigenDataBean bean = new V
		return null;
	}

}
