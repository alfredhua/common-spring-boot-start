package com.common.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailUtils {

  MailProperties mailProperties;

  public MailUtils(MailProperties mailProperties) {
    this.mailProperties = mailProperties;
  }


  public void sendMail(String toMail,String title, String context)throws Exception {
    List<String> list=new ArrayList<>();
    list.add(toMail);
    sendMails(list, title,context);
  }


  public void sendMails(List<String> toMailList, String title, String context)throws Exception {
    java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    Properties props = new Properties();
    props .setProperty("mail.smtp.host",mailProperties.getMail_host()); // 设置邮件服务器
    props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
    props.setProperty("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.port", mailProperties.getPort());
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.smtp.socketFactory.port", mailProperties.getPort());
    props.put("mail.smtp.auth", "true");// 发送服务器需要身份验证
    Session session = Session.getDefaultInstance(props, new Authenticator(){
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(mailProperties.getEmail_name(), mailProperties.getEmail_password());
      }});
    Message msg = new MimeMessage(session);// 创建默认的 MimeMessage 对象
    msg.setFrom(new InternetAddress(mailProperties.getEmail_name()));//设置发件
    final int num =toMailList.size();
    InternetAddress[] addresses = new InternetAddress[num];
    for (int i = 0; i < num; i++) {
      addresses[i] = new InternetAddress( toMailList.get(i));
    }
    msg.setRecipients(Message.RecipientType.TO,addresses);//设置收件

    //设置html格式
    BodyPart html = new MimeBodyPart();
    Multipart mainPart = new MimeMultipart();
    html.setContent(context, "text/html; charset=utf-8");
    mainPart.addBodyPart(html);
    msg.setContent(mainPart);

    msg.setSentDate(new Date());
    msg.setSubject(title);
    Transport transport = session.getTransport();
    Transport.send(msg);
    transport.close();
  }

}
