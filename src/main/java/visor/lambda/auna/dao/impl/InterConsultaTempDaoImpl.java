package visor.lambda.auna.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.auna.commons.trace.util.AuditConstants;
import pvt.auna.visorhc.dao.InterConsultaTempDao;
import pvt.auna.visorhc.model.bean.InterConsultaTempBean;
import pvt.auna.visorhc.model.bean.request.InterConsultaTempRequestBean;
import pvt.auna.visorhc.model.bean.response.InterConsultaInfoResponseBean;
import pvt.auna.visorhc.model.bean.response.InterConsultaTempResponseBean;

import javax.sql.DataSource;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class InterConsultaTempDaoImpl implements InterConsultaTempDao {
    private final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public InterConsultaTempResponseBean guardarInterConsultaTemp(InterConsultaTempRequestBean objA_request) throws Exception {
        InterConsultaTempResponseBean interConsultaTempResponseBean= new InterConsultaTempResponseBean();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dFechaApertura = sdf.parse(objA_request.getPv_fecha_apertura().replace("/", "-"));
            Date dFechaGuardado = sdf.parse(objA_request.getPv_fecha_guardado().replace("/", "-"));
            Date dFechaPeticion = sdf.parse(objA_request.getPv_fecha_peticion().replace("/", "-"));


            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(AuditConstants.ESQUEMA)
                    .withCatalogName(AuditConstants.PAQUETE).withProcedureName("SP_INS_PTM_INTERCONSULTA_TEMP");

            MapSqlParameterSource in = new MapSqlParameterSource();
            in.addValue("ip_fecha_apertura", dFechaApertura, Types.TIMESTAMP);
            in.addValue("ip_fecha_guardado", dFechaGuardado, Types.TIMESTAMP);
            in.addValue("ip_fecha_peticion", dFechaPeticion, Types.TIMESTAMP);
            in.addValue("ip_nhc", objA_request.getPv_nhc(), Types.VARCHAR);
            in.addValue("ip_usuario_his", objA_request.getPv_usuario_his(), Types.VARCHAR);
            in.addValue("ip_pn_solic", objA_request.getPn_solic(), Types.VARCHAR);
            in.addValue("ip_pn_codmed", objA_request.getPn_codmed(), Types.VARCHAR);
            in.addValue("ip_pv_info", objA_request.getPv_info(), Types.VARCHAR);
            in.addValue("ip_pn_encuentro", objA_request.getPn_encuentro(), Types.VARCHAR);
            in.addValue("ip_pn_peticion", objA_request.getPn_peticion(), Types.VARCHAR);

            Map<String, Object> out = simpleJdbcCall.execute(in);
            interConsultaTempResponseBean.setPo_id_evento(out.get("OP_ID_EVENTO").toString());
            interConsultaTempResponseBean.setPo_codigo(out.get("OP_CODIGO").toString());
            interConsultaTempResponseBean.setPo_mensaje(out.get("OP_MENSAJE").toString());

        } catch (Exception e) {
            this.LOGGER.error("[guardarInterConsultaTemp] Error al guardarInterConsultaTemp", e);
            //throw  e;
        }
        return interConsultaTempResponseBean;
    }

    @Override
    public Map<String, Object> getInterConsultaDetalleTemp(String pn_solic,String pn_peticion) throws Exception {
        Map<String, Object> out = new HashMap<>();
        Map<String, InterConsultaTempBean> outSalida = new HashMap<>();
        List<InterConsultaTempBean> lista = new ArrayList<>();
        Integer op_codigo = -1;
        String op_mensaje;
        try {

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(AuditConstants.ESQUEMA)
                    .withCatalogName(AuditConstants.PAQUETE).withProcedureName("SP_GET_PTM_INTERCONSULTA_TEMP")
                    .returningResultSet("PO_KURSOR", BeanPropertyRowMapper.newInstance(InterConsultaTempBean.class));

            MapSqlParameterSource in = new MapSqlParameterSource();

            in.addValue("ip_pn_peticion", pn_peticion, Types.VARCHAR);
            in.addValue("ip_pn_solic", pn_solic, Types.VARCHAR);

            out = simpleJdbcCall.execute(in);


        } catch (Exception e) {
            this.LOGGER.error("[getInterConsultaDetalleTemp] Error al getInterConsultaDetalleTemp", e);
            //throw  e;
        }
        return out;
    }



    @Override
    public InterConsultaTempResponseBean modificarInterConsultaTemp(InterConsultaTempRequestBean objA_request) throws Exception {
        InterConsultaTempResponseBean interConsultaTempResponseBean= new InterConsultaTempResponseBean();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dFechaModificado = sdf.parse(objA_request.getPv_fecha_guardado().replace("/", "-"));

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(AuditConstants.ESQUEMA)
                    .withCatalogName(AuditConstants.PAQUETE).withProcedureName("SP_MOD_PTM_INTERCONSULTA_TEMP");

            MapSqlParameterSource in = new MapSqlParameterSource();

            in.addValue("ip_fecha_modificado", dFechaModificado, Types.TIMESTAMP);
            in.addValue("ip_id_evento", objA_request.getId_evento(), Types.INTEGER);
             in.addValue("ip_pv_info", objA_request.getPv_info(), Types.VARCHAR);

            Map<String, Object> out = simpleJdbcCall.execute(in);
            interConsultaTempResponseBean.setPo_id_evento(out.get("OP_ID_EVENTO").toString());
            interConsultaTempResponseBean.setPo_codigo(out.get("OP_CODIGO").toString());
            interConsultaTempResponseBean.setPo_mensaje(out.get("OP_MENSAJE").toString());
        } catch (Exception e) {
            this.LOGGER.error("[modificarInterConsultaTemp] Error al modificarInterConsultaTemp", e);
            //throw  e;
        }
    return  interConsultaTempResponseBean;
    }


    @Override
    public InterConsultaInfoResponseBean enviarInterConsultaHis(InterConsultaTempRequestBean objA_request,String flag) throws Exception {
        InterConsultaInfoResponseBean interConsultaTempResponseBean= new InterConsultaInfoResponseBean();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dFechaEnviadoHis = sdf.parse(objA_request.getPv_fecha_enviado_his().replace("/", "-"));

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(AuditConstants.ESQUEMA)
                    .withCatalogName(AuditConstants.PAQUETE).withProcedureName("SP_PTM_INTERCONSULTA_TEMP_HIS");

            MapSqlParameterSource in = new MapSqlParameterSource();

            in.addValue("ip_pv_fecha_enviado_his", dFechaEnviadoHis, Types.TIMESTAMP);
            in.addValue("ip_id_evento", objA_request.getId_evento(), Types.INTEGER);
            in.addValue("ip_pv_enviado_his", flag, Types.CHAR);


            Map<String, Object> out = simpleJdbcCall.execute(in);
           // interConsultaTempResponseBean.setOp_id_evento(out.get("OP_ID_EVENTO").toString());
            interConsultaTempResponseBean.setPo_codigo(out.get("OP_CODIGO").toString());
            interConsultaTempResponseBean.setPo_mensaje(out.get("OP_MENSAJE").toString());
        } catch (Exception e) {
            this.LOGGER.error("[AuditInterConsultaRepositoryImpl] Error al writeAuditInterConsultaTrace", e);
            //throw  e;
        }
        return  interConsultaTempResponseBean;
    }
}
