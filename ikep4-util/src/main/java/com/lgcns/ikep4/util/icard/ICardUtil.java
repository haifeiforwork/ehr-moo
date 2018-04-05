package com.lgcns.ikep4.util.icard;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.vcard.GroupRegistry;
import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.ParameterFactory;
import net.fortuna.ical4j.vcard.ParameterFactoryRegistry;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.Property.Id;
import net.fortuna.ical4j.vcard.PropertyFactoryRegistry;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.VCardBuilder;
import net.fortuna.ical4j.vcard.VCardOutputter;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.property.Address;
import net.fortuna.ical4j.vcard.property.BDay;
import net.fortuna.ical4j.vcard.property.Categories;
import net.fortuna.ical4j.vcard.property.Clazz;
import net.fortuna.ical4j.vcard.property.Email;
import net.fortuna.ical4j.vcard.property.Fn;
import net.fortuna.ical4j.vcard.property.Gender;
import net.fortuna.ical4j.vcard.property.N;
import net.fortuna.ical4j.vcard.property.Note;
import net.fortuna.ical4j.vcard.property.Org;
import net.fortuna.ical4j.vcard.property.Role;
import net.fortuna.ical4j.vcard.property.Telephone;
import net.fortuna.ical4j.vcard.property.Title;
import net.fortuna.ical4j.vcard.property.Version;

import com.lgcns.ikep4.util.icard.model.IAddr;
import com.lgcns.ikep4.util.icard.model.ICard;
import com.lgcns.ikep4.util.icard.model.ITel;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * ICard 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ICardUtil.java 16247 2011-08-18 04:54:29Z giljae $
 */
@SuppressWarnings("unchecked")
public final class ICardUtil {

	public static final int BUFFER_SIZE = 1024;

	private ICardUtil() {
		throw new AssertionError();
	}

	public enum ICardProperty {

		BEGIN, END, VERSION, N, FN, BDAY, GENDER, EMAIL, ORG, TITLE, ROLE, CATEGORIES, CLASS, NOTE, TEL, ADR;

		ICardProperty() {}
	}

	/**
	 * 파일에서 Card 정보를 읽어옴 ical4j-vcard 를 이용함
	 * (http://m2.modularity.net.au/projects/ical4j-vcard/)
	 * 
	 * @param freaderList
	 * @return
	 * @throws Exception
	 */
	public static List<ICard> readICard(InputStream inp) {

		List<ICard> icardList = new ArrayList<ICard>();

		try {
			// Card 정보 얻기

			CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_RELAXED_PARSING, true);

			GroupRegistry groupRegistry = new GroupRegistry();
			PropertyFactoryRegistry propReg = new PropertyFactoryRegistry();
			ParameterFactoryRegistry parReg = new ParameterFactoryRegistry();
			addTypeParamsToRegistry(parReg);

			// VCardBuilder builder = new VCardBuilder(inputStream);
			// InputStreamReader reader = new InputStreamReader(inp, "KSC5601");
			StringReader reader = makeValidFile(new InputStreamReader(inp));

			VCardBuilder builder = new VCardBuilder(reader, groupRegistry, propReg, parReg);

			List<VCard> vcardList = builder.buildAll();

			for (VCard vcard : vcardList) {

				try {
					ICard icard = new ICard();

					Iterator ii = vcard.getProperties().iterator();

					// Card Property 리스트에서 정보 얻기
					while (ii.hasNext()) {

						try {

							Property property = (Property) ii.next();

							// 카드 내용 담기
							setCardForRead(property, icard);

						} catch (Exception e) {
							// ex.printStackTrace();
						}
					}

					icardList.add(icard);

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return icardList;

	}

	/**
	 * 카드 내용 담기
	 * 
	 * @param property
	 * @param icard
	 */
	private static void setCardForRead(Property property, ICard icard) {

		if (property.getId().equals(Id.VERSION)) {
			icard.setVersion(property.getValue());
		} else if (property.getId().equals(Id.N)) {
			icard.setFamilyName(((N) property).getFamilyName());
			icard.setGivenName(((N) property).getGivenName());
		} else if (property.getId().equals(Id.FN)) {
			icard.setFullName(property.getValue());
		} else if (property.getId().equals(Id.BDAY)) {
			icard.setBday(property.getValue());
		} else if (property.getId().equals(Id.GENDER)) {
			icard.setGender(property.getValue());
		} else if (property.getId().equals(Id.EMAIL)) {
			icard.setEmail(property.getValue());
		} else if (property.getId().equals(Id.ORG)) {
			String[] orgStr = ((Org) property).getValues();
			if (orgStr.length > 1) {
				icard.setOrg(orgStr[0]);
				icard.setDept(orgStr[1]);
			} else {
				icard.setOrg(orgStr[0]);
			}
		} else if (property.getId().equals(Id.TITLE)) {
			icard.setTitle(property.getValue());
		} else if (property.getId().equals(Id.ROLE)) {
			icard.setRole(property.getValue());
		} else if (property.getId().equals(Id.CATEGORIES)) {
			icard.setCategories(property.getValue());
		} else if (property.getId().equals(Id.CLASS)) {
			icard.setClazz(property.getValue());
		} else if (property.getId().equals(Id.NOTE)) {
			icard.setNote(property.getValue());
		} else if (property.getId().equals(Id.TEL)) {

			// 전화 번호담기
			setTelForRead(property, icard);

		} else if (property.getId().equals(Id.ADR)) {

			// 주소 담기
			setAddrForRead(property, icard);

		}

	}

	/**
	 * 전화번호 담기
	 * 
	 * @param property
	 * @param icard
	 */
	private static void setTelForRead(Property property, ICard icard) {

		ITel itel = new ITel();

		if (property.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE).getValue().toUpperCase().indexOf("FAX") > -1) {
			itel.setType("FAX");
		} else if (property.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE).getValue().toUpperCase()
				.indexOf("CELL") > -1) {
			itel.setType("CELL");
		} else if (property.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE).getValue().toUpperCase()
				.indexOf("HOME") > -1) {
			itel.setType("HOME");
		} else if (property.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE).getValue().toUpperCase()
				.indexOf("WORK") > -1) {
			itel.setType("WORK");
		} else {
			itel.setType("WORK");
		}

		itel.setNumber(property.getValue().substring(property.getValue().indexOf(":") + 1));
		icard.addITelList(itel);
	}

	/**
	 * 주소 담기
	 * 
	 * @param property
	 * @param icard
	 */
	private static void setAddrForRead(Property property, ICard icard) {

		Address addr = (Address) property;
		IAddr iaddr = new IAddr();

		if (property.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE).getValue().toUpperCase().indexOf("HOME") > -1) {
			iaddr.setType("HOME");
		} else if (property.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE).getValue().toUpperCase()
				.indexOf("WORK") > -1) {
			iaddr.setType("WORK");
		} else {
			iaddr.setType("WORK");
		}

		iaddr.setPoBox(addr.getPoBox());
		iaddr.setExtended(addr.getExtended());
		iaddr.setStreet(addr.getStreet());
		iaddr.setLocality(addr.getLocality());
		iaddr.setRegion(addr.getRegion());
		iaddr.setPostcode(addr.getPostcode());
		iaddr.setCountry(addr.getCountry());
		icard.addIAddrList(iaddr);
	}

	/**
	 * Card 정보를 파일로 저장함 ical4j-vcard 를 이용함
	 * (http://m2.modularity.net.au/projects/ical4j-vcard/)
	 * 
	 * @param icardList
	 * @param fileName
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String saveICard(List<ICard> icardList, String fileNameStr, HttpServletResponse response) {

		List<VCard> vcardList = new ArrayList<VCard>();

		try {

			Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
			String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root");
			String tmpDir = uploadRoot + "/vcard_tmp/";
			File folder = new File(tmpDir);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			// Card 리스트
			for (ICard icard : icardList) {

				try {
					VCard vcard = new VCard();

					vcard.getProperties().add(new Version("4.0"));

					// 카드 내용 담기
					setCardForSave(vcard, icard);

					vcardList.add(vcard);

				} catch (Exception e) {
					// ex.printStackTrace();
				}

			}

			// Writer out = response.getWriter();
			String fileName = fileNameStr;
			fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
			// PrintWriter out = new PrintWriter(new
			// OutputStreamWriter(response.getOutputStream(), "KSC5601"));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream()));

			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			VCardOutputter outputter = new VCardOutputter();

			// String tmpFileName = tmpDir + StringUtil.randomStr(10) + ".vcf";

			for (VCard vcard : vcardList) {
				// FileOutputStream fos = new FileOutputStream(tmpFileName);
				StringWriter sw = new StringWriter();
				outputter.output(vcard, sw);

				StringReader sr = new StringReader(sw.toString());

				int size = -1;
				char[] buf = new char[BUFFER_SIZE];
				while ((size = sr.read(buf)) != -1) {
					sr.read(buf, 0, size);
					out.write(buf, 0, size);
				}

				sw.close();
				sr.close();

			}

			out.flush();
			out.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "ok";
	}

	/**
	 * 카드 내용 담기
	 * 
	 * @param vcard
	 * @param icard
	 */
	private static void setCardForSave(VCard vcard, ICard icard) {

		if (icard.getFamilyName() != null || icard.getGivenName() != null) {
			vcard.getProperties().add(new N(icard.getFamilyName(), icard.getGivenName(), null, null, null));
		}
		if (icard.getFullName() != null) {
			vcard.getProperties().add(new Fn(icard.getFullName()));
		}
		if (icard.getBday() != null) {
			vcard.getProperties().add(new BDay(icard.getBday()));
		}
		if (icard.getGender() != null) {
			vcard.getProperties().add(new Gender(icard.getGender()));
		}
		if (icard.getEmail() != null) {
			vcard.getProperties().add(new Email(icard.getEmail()));
		}
		if (icard.getOrg() != null) {
			vcard.getProperties().add(new Org(icard.getOrg(), icard.getDept()));
		}
		if (icard.getTitle() != null) {
			vcard.getProperties().add(new Title(icard.getTitle()));
		}
		if (icard.getRole() != null) {
			vcard.getProperties().add(new Role(icard.getRole()));
		}
		if (icard.getCategories() != null) {
			vcard.getProperties().add(new Categories(icard.getCategories()));
		}
		if (icard.getClazz() != null) {
			vcard.getProperties().add(new Clazz(icard.getClazz()));
		}
		if (icard.getNote() != null) {
			vcard.getProperties().add(new Note(icard.getNote()));
		}
		if (icard.getItelList() != null) {

			// 전화 번호 담기
			setTelForSave(vcard, icard);

		}
		if (icard.getIaddrList() != null) {

			// 주소 담기
			setAddrForSave(vcard, icard);
		}

	}

	/**
	 * 전화 번호 담기
	 * 
	 * @param vcard
	 * @param icard
	 */
	private static void setTelForSave(VCard vcard, ICard icard) {

		for (ITel itel : icard.getItelList()) {

			try {
				if (StringUtil.nvl(itel.getType(), "").equals("FAX")) {
					vcard.getProperties().add(
							new Telephone(URI.create(StringUtil.nvl(itel.getNumber(), "")), new Type("WORK,FAX")));
				} else if (StringUtil.nvl(itel.getType(), "").equals("CELL")) {
					vcard.getProperties().add(
							new Telephone(URI.create(StringUtil.nvl(itel.getNumber(), "")), new Type("CELL")));
				} else if (StringUtil.nvl(itel.getType(), "").equals("HOME")) {
					vcard.getProperties().add(
							new Telephone(URI.create(StringUtil.nvl(itel.getNumber(), "")), new Type("HOME")));
				} else if (StringUtil.nvl(itel.getType(), "").equals("WORK")) {
					vcard.getProperties().add(
							new Telephone(URI.create(StringUtil.nvl(itel.getNumber(), "")), new Type("WORK")));
				} else {
					vcard.getProperties().add(
							new Telephone(URI.create(StringUtil.nvl(itel.getNumber(), "")), new Type("WORK")));
				}

			} catch (Exception e) {
				// ex.printStackTrace();
			}
		}
	}

	/**
	 * 주소 담기
	 * 
	 * @param vcard
	 * @param icard
	 */
	private static void setAddrForSave(VCard vcard, ICard icard) {

		for (IAddr iaddr : icard.getIaddrList()) {

			try {
				if (StringUtil.nvl(iaddr.getType(), "").equals("HOME")) {
					vcard.getProperties().add(
							new Address(StringUtil.nvl(iaddr.getPoBox(), ""), StringUtil.nvl(iaddr.getExtended(), ""),
									StringUtil.nvl(iaddr.getStreet(), ""), StringUtil.nvl(iaddr.getLocality(), ""),
									StringUtil.nvl(iaddr.getRegion(), ""), StringUtil.nvl(iaddr.getPostcode(), ""),
									StringUtil.nvl(iaddr.getCountry(), ""), new Type("HOME")));
				} else if (StringUtil.nvl(iaddr.getType(), "").equals("WORK")) {
					vcard.getProperties().add(
							new Address(StringUtil.nvl(iaddr.getPoBox(), ""), StringUtil.nvl(iaddr.getExtended(), ""),
									StringUtil.nvl(iaddr.getStreet(), ""), StringUtil.nvl(iaddr.getLocality(), ""),
									StringUtil.nvl(iaddr.getRegion(), ""), StringUtil.nvl(iaddr.getPostcode(), ""),
									StringUtil.nvl(iaddr.getCountry(), ""), new Type("WORK")));
				} else {
					vcard.getProperties().add(
							new Address(StringUtil.nvl(iaddr.getPoBox(), ""), StringUtil.nvl(iaddr.getExtended(), ""),
									StringUtil.nvl(iaddr.getStreet(), ""), StringUtil.nvl(iaddr.getLocality(), ""),
									StringUtil.nvl(iaddr.getRegion(), ""), StringUtil.nvl(iaddr.getPostcode(), ""),
									StringUtil.nvl(iaddr.getCountry(), ""), new Type("WORK")));
				}
			} catch (Exception e) {
				// ex.printStackTrace();
			}
		}
	}

	private static void addTypeParamsToRegistry(ParameterFactoryRegistry parReg) {
		for (final String name : new String[] { "HOME", "WORK", "MSG", "PREF", "VOICE", "FAX", "CELL", "VIDEO",
				"PAGER", "BBS", "MODEM", "CAR", "ISDN", "PCS", "INTERNET", "X400", "DOM", "INTL", "POSTAL", "PARCEL" }) {
			parReg.register(name, new ParameterFactory<Parameter>() {
				public Parameter createParameter(String value) {
					return new Type(name);
				}
			});
			String lc = name.toLowerCase();
			parReg.register(lc, new ParameterFactory<Parameter>() {
				public Parameter createParameter(String value) {
					return new Type(name);
				}
			});
		}
	}

	/**
	 * Valid 한 파일 만들기
	 * 
	 * @param reader
	 * @return
	 */
	private static StringReader makeValidFile(InputStreamReader reader) {

		StringReader returnReader = null;

		try {

			BufferedReader bf = new BufferedReader(reader);
			StringWriter sw = new StringWriter();

			String strLine = "";
			boolean find = false;

			while ((strLine = bf.readLine()) != null) {

				find = false;
				for (ICardProperty property : ICardProperty.values()) {

					if (strLine.startsWith(property + "")) {

						if (strLine.startsWith("N:") && strLine.indexOf(";") > 0) {
							strLine = strLine.substring(0, strLine.indexOf(";") + 1)
									+ strLine.substring(strLine.indexOf(";") + 1).replaceAll(";", " ");
						}
						find = true;
						break;
					}
				}

				if (find) {
					sw.write(strLine + "\n");
				}
			}

			returnReader = new StringReader(sw.toString());

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return returnReader;

	}

}
