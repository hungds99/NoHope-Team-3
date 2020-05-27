/**
 * 
 */
package com.javaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.common.dto.MailDto;
import com.javaweb.common.sendmail.EmailService;

/**
 * @author HungDinh
 *
 */

@RestController
public class ApiController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/sendMail")
	public void sendMail(@RequestBody MailDto mail) {
		emailService.sendSimpleMessage(mail.getTo(), mail.getSubject(), mail.getText());
	}
	
}
