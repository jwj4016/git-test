package com.quartz.project.domain;

import lombok.Data;

@Data
public class MailVO {
	/** 메세지ID */
	private String mssageId;
	/** 발신자 */
	private String sender;
	/** 수신자 */
	private String receiver;
	/** 제목 */
	private String mailSubject;
	/** 발송결과코드 */
	private String resultCode;
	/** 메일내용 */
	private String mailContents;
	/** 첨부파일ID */
	private String attachedFileId;
	/** 발신일자 */
	private String sendDate;
	/** 첨부파일ID 리스트 */
	private String attachedFileIdList;
	/** 파일명 */
	private String originalFileName;
}
