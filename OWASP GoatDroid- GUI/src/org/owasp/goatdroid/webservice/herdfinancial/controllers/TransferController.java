/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @created 2012
 */
package org.owasp.goatdroid.webservice.herdfinancial.controllers;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.owasp.goatdroid.webservice.herdfinancial.Constants;
import org.owasp.goatdroid.webservice.herdfinancial.bean.TransferBean;
import org.owasp.goatdroid.webservice.herdfinancial.services.RegisterServiceImpl;
import org.owasp.goatdroid.webservice.herdfinancial.services.TransferServiceImpl;

@Controller
@RequestMapping("herdfinancial/api/v1/transfer")
public class TransferController {

	TransferServiceImpl transferService;

	@Autowired
	public TransferController(TransferServiceImpl transferService) {
		this.transferService = transferService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public TransferBean doTransfer(
			@RequestParam(value = "from", required = true) String from,
			@RequestParam(value = "to", required = true) String to,
			@RequestParam(value = "amount", required = true) double amount,
			@RequestHeader(Constants.AUTH_TOKEN_HEADER) int sessionToken) {

		try {
			return TransferServiceImpl.transferFunds(sessionToken, from, to,
					amount);
		} catch (NullPointerException e) {
			TransferBean bean = new TransferBean();
			bean.setSuccess(false);
			return bean;
		}
	}
}
