package com.quartz.project.service;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.quartz.project.domain.MailVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailService {
	private JavaMailSender javaMailSender;
	/**
	 * 메일을 발송한다
	 * @param vo mailVO
	 * @return boolean
	 * @exception Exception
	 */
	public void srvSendMail(MailVO mailVO) throws Exception {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(mailVO.getSender());
		simpleMessage.setText(mailVO.getMailContents());
		simpleMessage.setText(mailVO.getMailContents());
		
//		javaMailSender.send(simpleMessage);
		
	}
	

}
