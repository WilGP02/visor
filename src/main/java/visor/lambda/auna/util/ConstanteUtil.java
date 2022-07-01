package visor.lambda.auna.util;

import java.util.List;
import java.util.Map;

import pvt.auna.visorhc.model.bean.WebServiceBean;
import pvt.auna.visorhc.model.beanPrueba.PacienteResponseBean;

public class ConstanteUtil {
	// Datos de conexion de base de datos 
	public static final String ESQUEMA = "VISORHC";
//	public static final String ESQUEMA = "PROVHC";
	public static final String PAQUETE="PCK_PTM_VHC";	
	public static List<PacienteResponseBean> listaPacientes;
	public static final String PERFIL_MED="Médico";
	
	/*	Tipo de permiso: 0. Asignado al médico 1.Todos los médicos  */
	public static final String PERMISO_MED = "0"; //
	public static final String PERMISO_GEN = "1";
	
	/* Tipo de búsqueda */
	public static final String BUS_SINPARAM = "0";
	public static final String BUS_XNOMBRE = "1";
	public static final String BUS_XDOCUMENTO = "2";
	
	/*	Tipo de encuentro Hospitalización, Emergencia y consulta ambulatoria */
	public static final String TIPOENC_GEN = "00";
	public static final String TIPOENC_HOS = "01";
	public static final String TIPOENC_URG = "02";
	public static final String TIPOENC_CEX = "03";
	
	public static Map<String, WebServiceBean> listaWebServices;
	
	/* Default value: Numerico y Cadena*/
	public static final String NUM_DEFAULT_PARAM = "0";
	public static final String VCH_DEFAULT_PARAM = "000";
	public static final String VCH_DEFAULT_PARAM_NUM = "1";
	public static final String VCH_DEFAULT_PARAM_TAM = "50";//100
	public static final String VCH_DEFAULT_PARAM_TAM_INF = "30";//100
	/* Default value: Paginacion*/
	public static final String VCH_NUM_PAG = "1";
	public static final String VCH_TAM_PAG = "30";
	public static final String VCH_TAM_PAG_2 = "20";
	
	/* Código de Origen de Formularios*/
	public static final String ORIGEN_XML = "00";
	public static final String ORIGEN_JAS = "01";
	public static final String ORIGEN_FIS = "02";
	
	/*  XERO VALUE FOR DEFAULLT */
	public static final String IDORIGEN = "VISORHC";
	public static final String IDCANAL = "CAN";
	public static final String IDAPP = "0001";
	public static final String IDRESOURCE = "0001";
	public static final String IDUSUARIO = "JORDAN";
	public static final String TRACE = "00003";
	
}
