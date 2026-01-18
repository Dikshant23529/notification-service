package com.talent.graph.notification_service.util;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {

   public static boolean sendEmail(
        Session session,
        String fromEmail,
        String toEmail,
        String subject,
        String body,
        boolean isHtml
) {
    try {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);

        if (isHtml) {
            msg.setContent(body, "text/html; charset=UTF-8");
        } else {
            msg.setText(body);
        }

        Transport.send(msg);
        return true;

    } catch (Exception e) {
        // log properly
        return false;
    }
}

}
