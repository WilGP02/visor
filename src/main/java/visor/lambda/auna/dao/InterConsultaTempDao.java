package visor.lambda.auna.dao;

import pvt.auna.visorhc.model.bean.request.InterConsultaTempRequestBean;
import pvt.auna.visorhc.model.bean.response.InterConsultaInfoResponseBean;
import pvt.auna.visorhc.model.bean.response.InterConsultaTempResponseBean;
import visor.lambda.auna.model.bean.response.InterConsultaTempResponseBean;

import java.util.Map;

public interface InterConsultaTempDao {
    public Map<String, Object> getInterConsultaDetalleTemp(String pn_solic, String pn_peticion) throws Exception;
    public InterConsultaTempResponseBean guardarInterConsultaTemp(InterConsultaTempRequestBean objA_request) throws Exception;
    public InterConsultaTempResponseBean modificarInterConsultaTemp(InterConsultaTempRequestBean objA_request) throws Exception;
    public InterConsultaInfoResponseBean enviarInterConsultaHis(InterConsultaTempRequestBean objA_request,String flag) throws Exception;


}
