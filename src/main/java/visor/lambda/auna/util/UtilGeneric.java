package visor.lambda.auna.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;

import pvt.auna.visorhc.config.YMLConf;

public class UtilGeneric {

	public static final String CONF_PATH = "/app/visorhc/conf/app.visorhc.yml";

	//public static YMLConf RSC;

	/*static {
		RSC = UtilGeneric.loadYMLConf();
	}*/
	 
	public static YMLConf loadYMLConf() {
		BufferedReader br = null;
		YMLConf rsc = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(CONF_PATH))));
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			rsc = mapper.readValue(br, YMLConf.class);
		} catch (Exception ex) {
			Logger.getLogger(UtilGeneric.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				if (br!= null) {
					br.close();
				}				
			} catch (IOException ex) {
				Logger.getLogger(UtilGeneric.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return rsc;
	}

	public static DataSource getDataSourceTranformador() {
		/*
		String jndi = RSC.getDatasource_jndi().get("jndi_trn");
		DataSource dataSource = null;
		try {
			dataSource = (DataSource) new JndiTemplate().lookup(jndi);
		} catch (Exception e) {
			DriverManagerDataSource tmp = new DriverManagerDataSource();
			tmp.setDriverClassName(RSC.getDatasource_jdbc().get("jdbc_trn_drive"));
			tmp.setUrl(RSC.getDatasource_jdbc().get("jdbc_trn_url"));
			tmp.setUsername(RSC.getDatasource_jdbc().get("jdbc_trn_username"));
			tmp.setPassword(RSC.getDatasource_jdbc().get("jdbc_trn_password"));
			dataSource = tmp;
		}

		return dataSource;*/
		return null;
	}

	public static DataSource getDataSourceTranformador(String... p) {
		DataSource dataSource = null;
		try {
			dataSource = (DataSource) new JndiTemplate().lookup(p[0]);
		} catch (Exception e) {
			DriverManagerDataSource tmp = new DriverManagerDataSource();
			tmp.setDriverClassName(p[1]);
			tmp.setUrl(p[2]);
			tmp.setUsername(p[3]);
			tmp.setPassword(p[4]);
			dataSource = tmp;
		}

		return dataSource;
	}
}
