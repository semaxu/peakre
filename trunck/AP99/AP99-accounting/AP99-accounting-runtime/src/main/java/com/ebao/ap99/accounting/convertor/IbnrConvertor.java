/**
 * 
 */
package com.ebao.ap99.accounting.convertor;

/**
 * @author yujie.zhang
 *
 */
public class IbnrConvertor {

	public String cobConvertor(String cob){
		String cobReturn = null;
		//Fire, Property All Risks, Business Interruption, Natural Catastrophe, 
		//Contractors'/Erection All Risks, General Third Party Liability,
		//Products Liability,  Workmen's Compensation/Employers' Liability
		if (cob.equals("Fire")) cobReturn ="1";
		if (cob.equals("Property All Risks")) cobReturn ="2";
		if (cob.equals("Business Interruption")) cobReturn ="3";
		if (cob.equals("Natural Catastrophe")) cobReturn ="4";
		if (cob.equals("Contractors'/Erection All Risks")) cobReturn ="5";
		if (cob.equals("General Third Party Liability")) cobReturn ="6";
		if (cob.equals("Products Liability")) cobReturn ="7";
		if (cob.equals("Workmen's Compensation/Employers' Liability")) cobReturn ="8";

		return cobReturn;
				
	}
	//Australia, Hong Kong, Japan, USA,
	public String countryConvertor(String country){
		String countryReturn = "";
		if (country.equals("Australia")) countryReturn ="1";
		if (country.equals("Hong Kong")) countryReturn ="2";
		if (country.equals("Japan")) countryReturn ="3";
		if (country.equals("USA")) countryReturn ="4";

		
		return countryReturn;
	}
	
	public String contractNatureConvertor(String contractNature){
		String contractNatureR ="";
		
		if (contractNature.equals("Proportional")) contractNatureR ="1";
		if (contractNature.equals("Non-proportional")) contractNatureR ="2";

		return contractNatureR;
	}
}
