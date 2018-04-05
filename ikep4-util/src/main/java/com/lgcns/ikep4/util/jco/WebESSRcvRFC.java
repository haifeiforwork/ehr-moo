package com.lgcns.ikep4.util.jco;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

/**
 * @author 최재영
 * @version 1.0, 2012/09/05
 */
@Service("webEssRcv")
@Transactional
public class WebESSRcvRFC {

	protected final Log log = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	public Map<?, ?> callRfcFunction(String functionName, HashMap<String, Object> params, HashMap<?, ?> tableParams, HttpServletRequest request){

		//JCO Object
		Connection connect = null;
		JCoFunction function = null;
		JCoMetaData jcoMetaData = null;
		JCoStructure jcoStructure = null;
		JCoTable jcoTable = null;
		JCoField jcoField = null;

		//Parameter
		List<?> inputList = null;
		Map<?, ?> inputMap = null;
		Map<?, ?> returnValue = null;

		try {
			connect = new Connection(request);
			function = connect.getSAPFunction(functionName);

			if (function == null) {
				log.error("callRfcFunction() -----------------------> function " + functionName + " not found in SAP.");
				return null;
			}

			log.info("callRfcFunction."+functionName+"======================================>");
			log.info(function);

			int fieldCount = 0;
			JCoFieldIterator fieldItr = null;

			HashMap<String, Object> fieldDatas = null;
			ArrayList<Map<?,?>> outputList = null;

			if(params != null && function.getImportParameterList() != null){
				jcoMetaData = function.getImportParameterList().getMetaData();

				fieldCount = jcoMetaData.getFieldCount();
				for( int i = 0 ; i < fieldCount ; i++ ){

					if( jcoMetaData.getType(i) == JCoMetaData.TYPE_STRUCTURE ){
						jcoStructure = function.getImportParameterList().getStructure(i);
						inputMap = (Map<?,?>) params.get(jcoMetaData.getName(i));

						if( inputMap != null ){
							fieldItr = jcoStructure.getFieldIterator();
							while(fieldItr.hasNextField()){
								jcoField = fieldItr.nextField();
								if(jcoField.getType() == JCoMetaData.TYPE_DATE){
									jcoStructure.setValue(jcoField.getName(), inputMap.get(jcoField.getName()) != null ? ((String)inputMap.get(jcoField.getName())).replaceAll("-", "") : "");
								}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
									jcoStructure.setValue(jcoField.getName(), inputMap.get(jcoField.getName()) != null ? StringUtils.rightPad(((String)inputMap.get(jcoField.getName())).replaceAll(":", ""), 6, "0") : "");
								}else{
									jcoStructure.setValue(jcoField.getName(), inputMap.get(jcoField.getName()));
								}
							}
						}

						log.debug(jcoMetaData.getName(i) + " : " + inputMap);
					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_DATE){
						function.getImportParameterList().setValue(jcoMetaData.getName(i), params.get(jcoMetaData.getName(i)) != null ? ((String)params.get(jcoMetaData.getName(i))).replaceAll("-", "") : "");
						log.debug(jcoMetaData.getName(i) + " : " + params.get(jcoMetaData.getName(i)) + "(replace - )");
					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_TIME){
						function.getImportParameterList().setValue(jcoMetaData.getName(i), params.get(jcoMetaData.getName(i)) != null ? StringUtils.rightPad(((String)params.get(jcoMetaData.getName(i))).replaceAll(":", ""), 6, "0") : "");
						log.debug(jcoMetaData.getName(i) + " : " + params.get(jcoMetaData.getName(i)) + "(replace : )");
					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_TABLE){
						inputList = (List<?>) params.get(jcoMetaData.getName(i));
						if( inputList != null ){
							jcoTable = function.getImportParameterList().getTable(jcoMetaData.getName(i));
							for (int j = 0, rowCnt = inputList.size(); j < rowCnt; j++) {
								jcoTable.appendRow();
								fieldDatas = (HashMap<String, Object>) inputList.get(j);
								fieldItr = jcoTable.getFieldIterator();
								while(fieldItr.hasNextField()){
									jcoField = fieldItr.nextField();
									if(jcoField.getType() == JCoMetaData.TYPE_DATE){
										jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()) != null ? ((String)fieldDatas.get(jcoField.getName())).replaceAll("-", "") : "");
										log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()) + "(replace -)");
									}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
										jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()) != null ? StringUtils.rightPad(((String)fieldDatas.get(jcoField.getName())).replaceAll(":", ""), 6, "0") : "");
										log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()) + "(replace :)");
									}else{
										jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()));
										log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()));
									}
								}
							}
						}
					} else {
						function.getImportParameterList().setValue(jcoMetaData.getName(i), params.get(jcoMetaData.getName(i)));
						log.debug(jcoMetaData.getName(i) + " : " + params.get(jcoMetaData.getName(i)));
					}
				}
			}

			if ( tableParams != null && function.getTableParameterList() != null ){
				jcoMetaData = function.getTableParameterList().getMetaData();
				fieldCount = jcoMetaData.getFieldCount();

				for( int i = 0 ; i < fieldCount ; i++ ){

					inputList = (List<?>) tableParams.get(jcoMetaData.getName(i));

					if( inputList != null ){

						log.debug("Table Name : "+jcoMetaData.getName(i));

						jcoTable = function.getTableParameterList().getTable(jcoMetaData.getName(i));

						for (int j = 0, rowCnt = inputList.size(); j < rowCnt; j++) {
							jcoTable.appendRow();
							fieldDatas = (HashMap<String, Object>) inputList.get(j);
							fieldItr = jcoTable.getFieldIterator();
							while(fieldItr.hasNextField()){
								jcoField = fieldItr.nextField();
								if(jcoField.getType() == JCoMetaData.TYPE_DATE){
									jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()) != null ? ((String)fieldDatas.get(jcoField.getName())).replaceAll("-", "") : "");
									log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()) + "(replace -)");
								}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
									jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()) != null ? StringUtils.rightPad(((String)fieldDatas.get(jcoField.getName())).replaceAll(":", ""), 6, "0") : "");
									log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()) + "(replace :)");
								}else{
									jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()));
									log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()));
								}
							}
						}
					}
				}
			}

			connect.executeSAP(function);

			log.info("callRfcFunction()---->connect.executeSAP("+functionName+")");

			HashMap<String, Object> tableDatas = new HashMap<String, Object>();
			HashMap<String,Object> structureData = null;

			String returnStatus = "";

			Date returnDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			if( function.getExportParameterList() != null ){

				jcoMetaData = function.getExportParameterList().getMetaData();
				fieldCount = jcoMetaData.getFieldCount();

				for( int i = 0 ; i < fieldCount ; i++ ){
					if( jcoMetaData.getType(i) == JCoMetaData.TYPE_STRUCTURE ){

						structureData = new HashMap<String, Object>();
						jcoStructure = function.getExportParameterList().getStructure(i);
						fieldItr = jcoStructure.getFieldIterator();

						while(fieldItr.hasNextField()){
							jcoField = fieldItr.nextField();

							if(jcoField.getType() == JCoMetaData.TYPE_DATE){

								returnDate = jcoStructure.getDate(jcoField.getName());
								if(returnDate == null){
									returnStatus = "";
								}else{
									returnStatus = sdf.format(returnDate);
								}
								structureData.put(jcoField.getName(), returnStatus);

							}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
								structureData.put(jcoField.getName(), jcoStructure.getString(jcoField.getName()));
							}else{
								structureData.put(jcoField.getName(), jcoStructure.getString(jcoField.getName()));
							}
						}

						tableDatas.put(jcoMetaData.getName(i), structureData);
					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_DATE){

						returnDate = function.getExportParameterList().getDate(jcoMetaData.getName(i));

						if(returnDate == null){
							returnStatus = "";
						}else{
							returnStatus = sdf.format(returnDate);
						}

						tableDatas.put(jcoMetaData.getName(i), returnStatus);

					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_TIME){
						returnStatus = (String) function.getExportParameterList().getValue(jcoMetaData.getName(i));
						tableDatas.put(jcoMetaData.getName(i), returnStatus);
					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_TABLE){
						jcoTable = function.getExportParameterList().getTable(jcoMetaData.getName(i));
						outputList = new ArrayList<Map<?,?>>();

						for (int j = 0, rowCnt = jcoTable.getNumRows(); j < rowCnt; j++) {
							jcoTable.setRow(j);
							fieldDatas = new HashMap<String, Object>();
							fieldItr = jcoTable.getFieldIterator();

							while(fieldItr.hasNextField()){
								jcoField = fieldItr.nextField();
								if(jcoField.getType() == JCoMetaData.TYPE_DATE){

									returnDate = jcoTable.getDate(jcoField.getName());

									if(returnDate == null){
										returnStatus = "";
									}else{
										returnStatus = sdf.format(returnDate);
									}

									fieldDatas.put(jcoField.getName(), returnStatus);

								}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
									fieldDatas.put(jcoField.getName(), jcoTable.getString(jcoField.getName()));
								}else{
									fieldDatas.put(jcoField.getName(), jcoTable.getString(jcoField.getName()));
								}
							}

							outputList.add(fieldDatas);
						}
						tableDatas.put(jcoMetaData.getName(i), outputList);
					} else {
						tableDatas.put(jcoMetaData.getName(i), function.getExportParameterList().getValue(jcoMetaData.getName(i)));
					}
				}
			}

			if( function.getTableParameterList() != null ){

				jcoMetaData = function.getTableParameterList().getMetaData();
				fieldCount = jcoMetaData.getFieldCount();

				for( int i = 0 ; i < fieldCount ; i++ ){

					jcoTable = function.getTableParameterList().getTable(jcoMetaData.getName(i));
					outputList = new ArrayList<Map<?,?>>();

					for (int j = 0, rowCnt = jcoTable.getNumRows(); j < rowCnt; j++) {
						jcoTable.setRow(j);
						fieldDatas = new HashMap<String, Object>();
						fieldItr = jcoTable.getFieldIterator();


						while(fieldItr.hasNextField()){
							jcoField = fieldItr.nextField();
							if(jcoField.getType() == JCoMetaData.TYPE_DATE){

								returnDate = jcoTable.getDate(jcoField.getName());

								if(returnDate == null){
									returnStatus = "";
								}else{
									returnStatus = sdf.format(returnDate);
								}

								fieldDatas.put(jcoField.getName(), returnStatus);

							}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
								fieldDatas.put(jcoField.getName(), jcoTable.getString(jcoField.getName()));
							}else{
								fieldDatas.put(jcoField.getName(), jcoTable.getString(jcoField.getName()));
							}
						}

						outputList.add(fieldDatas);
					}
					tableDatas.put(jcoMetaData.getName(i), outputList);
				}
			}

			returnValue = Collections.synchronizedMap(tableDatas);

			log.debug(functionName+" Result");
			//log.debug(returnValue);

		} catch (JCoException e) {
			log.debug("function Name : "+functionName);
			log.debug("e.getMessage():"+e.getMessage());
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		} catch (Exception e) {
			log.debug("function Name : "+functionName);
			log.debug("e.getMessage():"+e.getMessage());
			e.printStackTrace();
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public byte[] callRfcFunctionGetPDF(String functionName, HashMap<String, Object> params, HashMap<?, ?> tableParams, HttpServletRequest request){

		//JCO Object
		Connection connect = null;
		JCoFunction function = null;
		JCoMetaData jcoMetaData = null;
		JCoStructure jcoStructure = null;
		JCoTable jcoTable = null;
		JCoField jcoField = null;

		//Parameter
		List<?> inputList = null;
		Map<?, ?> inputMap = null;
		byte[] byteArray = null;

		try {
			connect = new Connection(request);
			function = connect.getSAPFunction(functionName);

			if (function == null) {
				log.error("callRfcFunction() -----------------------> function " + functionName + " not found in SAP.");
				return null;
			}

			log.info("callRfcFunction."+functionName+"======================================>");
			log.info(function);

			int fieldCount = 0;
			JCoFieldIterator fieldItr = null;

			HashMap<String, Object> fieldDatas = null;
			ArrayList<Map<?,?>> outputList = null;

			if(params != null && function.getImportParameterList() != null){
				jcoMetaData = function.getImportParameterList().getMetaData();

				fieldCount = jcoMetaData.getFieldCount();
				for( int i = 0 ; i < fieldCount ; i++ ){

					if( jcoMetaData.getType(i) == JCoMetaData.TYPE_STRUCTURE ){
						jcoStructure = function.getImportParameterList().getStructure(i);
						inputMap = (Map<?,?>) params.get(jcoMetaData.getName(i));

						if( inputMap != null ){
							fieldItr = jcoStructure.getFieldIterator();
							while(fieldItr.hasNextField()){
								jcoField = fieldItr.nextField();
								if(jcoField.getType() == JCoMetaData.TYPE_DATE){
									jcoStructure.setValue(jcoField.getName(), inputMap.get(jcoField.getName()) != null ? ((String)inputMap.get(jcoField.getName())).replaceAll("-", "") : "");
								}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
									jcoStructure.setValue(jcoField.getName(), inputMap.get(jcoField.getName()) != null ? StringUtils.rightPad(((String)inputMap.get(jcoField.getName())).replaceAll(":", ""), 6, "0") : "");
								}else{
									jcoStructure.setValue(jcoField.getName(), inputMap.get(jcoField.getName()));
								}
							}
						}

						log.debug(jcoMetaData.getName(i) + " : " + inputMap);
					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_DATE){
						function.getImportParameterList().setValue(jcoMetaData.getName(i), params.get(jcoMetaData.getName(i)) != null ? ((String)params.get(jcoMetaData.getName(i))).replaceAll("-", "") : "");
						log.debug(jcoMetaData.getName(i) + " : " + params.get(jcoMetaData.getName(i)) + "(replace - )");
					}else if(jcoMetaData.getType(i) == JCoMetaData.TYPE_TIME){
						function.getImportParameterList().setValue(jcoMetaData.getName(i), params.get(jcoMetaData.getName(i)) != null ? StringUtils.rightPad(((String)params.get(jcoMetaData.getName(i))).replaceAll(":", ""), 6, "0") : "");
						log.debug(jcoMetaData.getName(i) + " : " + params.get(jcoMetaData.getName(i)) + "(replace : )");
					} else {
						function.getImportParameterList().setValue(jcoMetaData.getName(i), params.get(jcoMetaData.getName(i)));
						log.debug(jcoMetaData.getName(i) + " : " + params.get(jcoMetaData.getName(i)));
					}
				}
			}

			if ( tableParams != null && function.getTableParameterList() != null ){
				jcoMetaData = function.getTableParameterList().getMetaData();
				fieldCount = jcoMetaData.getFieldCount();

				for( int i = 0 ; i < fieldCount ; i++ ){

					inputList = (List<?>) tableParams.get(jcoMetaData.getName(i));

					if( inputList != null ){

						log.debug("Table Name : "+jcoMetaData.getName(i));

						jcoTable = function.getTableParameterList().getTable(jcoMetaData.getName(i));

						for (int j = 0, rowCnt = inputList.size(); j < rowCnt; j++) {
							jcoTable.appendRow();
							fieldDatas = (HashMap<String, Object>) inputList.get(j);
							fieldItr = jcoTable.getFieldIterator();
							while(fieldItr.hasNextField()){
								jcoField = fieldItr.nextField();
								if(jcoField.getType() == JCoMetaData.TYPE_DATE){
									jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()) != null ? ((String)fieldDatas.get(jcoField.getName())).replaceAll("-", "") : "");
									log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()) + "(replace -)");
								}else if(jcoField.getType() == JCoMetaData.TYPE_TIME){
									jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()) != null ? StringUtils.rightPad(((String)fieldDatas.get(jcoField.getName())).replaceAll(":", ""), 6, "0") : "");
									log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()) + "(replace :)");
								}else{
									jcoTable.setValue(jcoField.getName(), fieldDatas.get(jcoField.getName()));
									log.debug( jcoField.getName() + " : " + fieldDatas.get(jcoField.getName()));
								}
							}
						}
					}
				}
			}

			connect.executeSAP(function);

			log.info("callRfcFunction()---->connect.executeSAP("+functionName+")");

			if( function.getExportParameterList() != null ){
				byteArray = function.getExportParameterList().getByteArray("EX_PDF_FILE");
			}

			return byteArray;

		} catch (JCoException e) {
			log.debug("function Name : "+functionName);
			log.debug("e.getMessage():"+e.getMessage());
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		} catch (Exception e) {
			log.debug("function Name : "+functionName);
			log.debug("e.getMessage():"+e.getMessage());
			e.printStackTrace();
		}
		return byteArray;
	}
}