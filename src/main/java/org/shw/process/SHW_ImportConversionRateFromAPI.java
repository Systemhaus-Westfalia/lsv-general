/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.                                     *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net                                                  *
 * or https://github.com/adempiere/adempiere/blob/develop/license.html        *
 *****************************************************************************/
package org.shw.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.logging.Level;

import org.compiere.model.MConversionRate;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * Fetches an exchange rate from a REST API and writes it to C_Conversion_Rate.
 * Designed to run as a daily ADempiere Scheduler job.
 *
 * Default endpoint: https://api.frankfurter.app/latest?from=USD&to=GTQ
 * For production (Guatemala): replace ApiUrl with the Banco de Guatemala (BANGUAT)
 * SOAP endpoint and override fetchRate() accordingly.
 *
 * @author Systemhaus Westfalia
 */
public class SHW_ImportConversionRateFromAPI extends SHW_ImportConversionRateFromAPIAbstract {

//	private String  p_ApiUrl               = "https://api.frankfurter.app/latest?from=USD&to=GTQ";
//	private String  p_BaseCurrency         = "USD";
//	private String  p_TargetCurrency       = "GTQ";
//	private int     p_C_ConversionType_ID  = 0;
//	private boolean p_CreateReciprocalRate = true;

	
	@Override
	protected String doIt() throws Exception {
		BigDecimal multiplyRate = fetchRate();
		BigDecimal divideRate   = BigDecimal.ONE.divide(multiplyRate, 10, RoundingMode.HALF_UP);

		int fromId = getCurrencyId();
		int toId   = getCurrencyTo();


		Timestamp today = new Timestamp(System.currentTimeMillis());

		saveRate(fromId, toId, multiplyRate, divideRate, today);
		//addLog("Rate saved: 1 " + p_BaseCurrency + " = " + multiplyRate + " " + p_TargetCurrency);

		if (isCreateReciprocalRate()) {
			saveRate(toId, fromId, divideRate, multiplyRate, today);
			//addLog("Reciprocal: 1 " + p_TargetCurrency + " = " + divideRate + " " + p_BaseCurrency);
		}

		return "@Success@";
	}

	private void saveRate(int fromId, int toId,
	                      BigDecimal multiply, BigDecimal divide, Timestamp date) {
		MConversionRate rate = new MConversionRate(getCtx(), 0, get_TrxName());
		rate.setAD_Org_ID(0); // conversion rates are managed at client level
		rate.setC_ConversionType_ID(getConversionTypeId());
		rate.setC_Currency_ID(fromId);
		rate.setC_Currency_ID_To(toId);
		rate.setMultiplyRate(multiply);
		rate.setDivideRate(divide);
		rate.setValidFrom(date);
		rate.setValidTo(date);
		rate.saveEx();
	}

	/**
	 * Calls the configured REST endpoint and returns the exchange rate.
	 * Expected JSON format (open.er-api.com / ExchangeRate-API full-list format):
	 *   {"base_code":"USD","rates":{"GTQ":7.79,"EUR":0.92,...}}
	 *
	 * The target currency ISO code is resolved from the C_Currency_ID_To parameter
	 * so the parser finds the correct value even when the response contains many currencies.
	 *
	 * Recommended free URL (no API key, supports GTQ):
	 *   https://open.er-api.com/v6/latest/USD
	 */
	private BigDecimal fetchRate() throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(getURL()).openConnection();
		conn.setRequestProperty("Accept", "application/json");
		conn.setConnectTimeout(15_000);
		conn.setReadTimeout(15_000);

		int status = conn.getResponseCode();
		if (status != 200)
			throw new Exception("API returned HTTP " + status + " for: " + getURL());

		StringBuilder sb = new StringBuilder();
		try (Scanner sc = new Scanner(conn.getInputStream(), "UTF-8")) {
			while (sc.hasNextLine())
				sb.append(sc.nextLine());
		}
		String targetISO = getCurrencyISO(getCurrencyTo());
		if (targetISO == null || targetISO.isEmpty())
			throw new Exception("Target currency ISO code not found for C_Currency_ID=" + getCurrencyTo());
		return parseRate(sb.toString(), targetISO);
	}

	/**
	 * Finds the value for the given ISO code inside the "rates" object of the JSON response.
	 * Works with any API that returns a flat rates map, regardless of how many currencies it contains.
	 */
	private BigDecimal parseRate(String json, String targetISO) throws Exception {
		String searchKey = "\"" + targetISO + "\"";
		int idx = json.indexOf(searchKey);
		if (idx < 0)
			throw new Exception("Currency '" + targetISO + "' not found in API response: " + json);
		int colon = json.indexOf(':', idx + searchKey.length());
		if (colon < 0)
			throw new Exception("No value after '" + targetISO + "' in API response");
		int end = json.indexOf(',', colon + 1);
		if (end < 0) end = json.indexOf('}', colon + 1);
		if (end < 0) end = json.length();
		String value = json.substring(colon + 1, end).trim().replaceAll("[^0-9.]", "");
		if (value.isEmpty())
			throw new Exception("Could not parse numeric rate for '" + targetISO + "' from API response");
		return new BigDecimal(value);
	}

	private String getCurrencyISO(int currencyId) {
		return DB.getSQLValueString(get_TrxName(),
			"SELECT ISO_Code FROM C_Currency WHERE C_Currency_ID=? AND IsActive='Y'",
			currencyId);
	}
}
