package visor.lambda.auna.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author Smith Vasquez <smith.vasquez@avantica.com>
 */

@Component
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);
    //private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(getClass());

    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "PE"));
    public static final SimpleDateFormat FORMATO_FECHA_INGLES = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("es", "PE"));
    public static final SimpleDateFormat FORMATO_FECHA_COMPLETA = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy", new Locale("es", "PE"));
    public static final SimpleDateFormat FORMATO_NUMERO_MES = new SimpleDateFormat("MM", new Locale("es", "PE"));
    public static final SimpleDateFormat FORMATO_ANIO = new SimpleDateFormat("yyyy", new Locale("es", "PE"));

    public static final String FORMAT_SPANISH_DATE = "dd/MM/yyyy";
    public static final String FORMAT_SPANISH_DATE_ALTERNATIVE = "dd-MM-yyyy";
    public static final String FORMAT_ENGLISH_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_SPANISH_TIME = "HH:mm:ss";
    public static final String FORMAT_MONTH_REDUCED = "MMM";

    public static boolean validaEsNuloOBlanco(Object valor) {
        return valor == null || valor.toString().trim().length() == 0;
    }

    public static LocalDate dateFormatter(String date) {
        DateTimeFormatter formatter = null;
        LocalDate dateTime = null;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        dateTime = LocalDate.parse(date, formatter);
        return dateTime;
    }

    public static LocalDate dateFormatterDDMMYYY(String date) {
        DateTimeFormatter formatter = null;
        LocalDate dateTime = null;
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateTime = LocalDate.parse(date, formatter);
        return dateTime;
    }


    public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Long.parseLong(cadena);
            resultado = true;
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            resultado = false;
        }
        return resultado;
    }

    public static boolean isDecimal(String chain){
        boolean resultado;
        try {
            new BigDecimal(chain);
            resultado = true;
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            resultado = false;
        }
        return resultado;
    }

    public static boolean inputCurrentDate(String fechaRegistroActual, String fechaRegistroNueva) {
        try {
            return CommonUtil.FORMATO_FECHA_INGLES.parse(fechaRegistroActual)
                    .compareTo(CommonUtil.FORMATO_FECHA_INGLES.parse(fechaRegistroNueva)) > 0;
        } catch (ParseException e) {
            LOGGER.error("FAIL ResumenGeneralService.currentDate: " + e.getMessage());
            return true;
        }
    }

    public static boolean currentDate(String fechaRegistroActual, String fechaRegistroNueva) {
        try {
            return CommonUtil.FORMATO_FECHA.parse(fechaRegistroActual)
                    .compareTo(CommonUtil.FORMATO_FECHA.parse(fechaRegistroNueva)) > 0;
        } catch (ParseException e) {
            LOGGER.error("FAIL CommonUtil.currentDate: " + e.getMessage());
            return true;
        }
    }

    public static boolean currentDate(Date fechaRegistroActual, Date fechaRegistroNueva) {
        return fechaRegistroActual.compareTo(fechaRegistroNueva) > 0;
    }

    public static String isNullOrEmpty(String value) {
        return value != null ? value.trim(): "";
    }

    public static String getDateNow(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static String getDateNow(Date date, String formatDate){
        SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
        return formatter.format(date);
    }

    public static int numeroDiasEntreDosFechas(Date firstDate, Date secondDate){
        long startTime = firstDate.getTime();
        long endTime = secondDate.getTime();
        long diffTime = startTime - endTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        return (int)diffDays;
    }

    public static Date fromStringToDate(String strDate){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return dateFormat.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * Metodo para parsear una cadena de fecha en otra
     * @param dateInput
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static final String transformDate(String dateInput,String fromFormat,String toFormat){
        if(!StringUtils.isEmpty(dateInput) && !StringUtils.isEmpty(fromFormat) && !StringUtils.isEmpty(toFormat)){
            try{
                Locale locale = toFormat.equals(FORMAT_MONTH_REDUCED) ? new Locale("es", "ES") : new Locale("en", "US");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fromFormat, locale);
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(toFormat, locale);
                LocalDate localDate = LocalDate.parse(dateInput,formatter);
                return localDate.format(formatter1);
            }catch(Exception e){
                e.printStackTrace();
                return dateInput;
            }
        }
        return null;
    }

    public static LocalDate toLocalDate(String dateInput,String format){
        if(!StringUtils.isEmpty(dateInput) && !StringUtils.isEmpty(format)){
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate localDate = LocalDate.parse(dateInput,formatter);
                return localDate;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}