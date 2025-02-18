package cliper.apiBoostly.daos;

public class MailDto {
	
	String mail="aaaaa";

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public MailDto(String mail) {
		super();
		this.mail = mail;
	}

	public MailDto() {
		super();
	}

	@Override
	public String toString() {
		return "MailDto [mail=" + mail + "]";
	}
	
	

}
