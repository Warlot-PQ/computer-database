package com.excilys.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.persistence.pagination.PageRequest;
import com.excilys.persistence.pagination.PageRequest.Builder;
import com.excilys.service.marshaller.MarshallerXml;
import com.excilys.webapp.controller.mapper.RequestModel;

/**
 * Export a list of computers to XML
 * 
 * @author pqwarlot
 *
 */

@Controller
@RequestMapping("/Export")
public class Export {
	@Autowired
	MarshallerXml marshallerXml;

	@RequestMapping(method = RequestMethod.GET)
	public void getXml(@Valid @ModelAttribute RequestModel requestModel, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (result.hasErrors()) {
			requestModel.reset();
		}

		Builder builderDataRequest = PageRequest.create().page(requestModel.getPage()).eltByPage(Integer.MAX_VALUE)
				.orderBy(requestModel.getOrderBy()).orderAlphaNumerical(requestModel.isOrderAlphaNum());

		if (requestModel.getSearch() != null) {
			builderDataRequest.seachedName(requestModel.getSearch());
		}
		
		String filePath = "computer.xml";
		marshallerXml.computerAll(filePath, builderDataRequest.build());

		// get absolute path of the application
		ServletContext context = request.getServletContext();
		// String appPath = context.getRealPath("");
		String appPath = "";

		// construct the complete absolute path of the file
		String fullPath = appPath + filePath;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		
		inputStream.close();
		outStream.close();
		downloadFile.delete();
	}
}
