/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.service;

import com.kunleawotunbo.tunbor.model.User;
import freemarker.template.Configuration;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author olakunle
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    Configuration freemarkerConfiguration;

    @Async
    public void sendMail(Object object) {
        User user = (User) object;

        MimeMessagePreparator preparator = getMessagePreparator(user);
        try {
            mailSender.send(preparator);
            System.out.println("Message has been sent....................");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private MimeMessagePreparator getMessagePreparator(final User user) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject("New User Created");
                helper.setFrom("newuser@mywebsite.com");
                helper.setTo(user.getEmail());

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("user", user);

                String text = getFreeMarketTemplateContent(model);
                System.out.println("Template content : " + text);

                //user the true flag to indicate you need a multipart message
                helper.setText(text, true);

                // Additionally, let's add a resource as an attachment as well
                helper.addAttachment("cutie.png", new ClassPathResource("linux-icon.png"));
            }

        };
        return preparator;
    }

    public String getFreeMarketTemplateContent(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();

        String template = "fm_mailTemplate.txt";
        try {

//            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
//                    freemarkerConfiguration.getTemplate(template), model));
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate("fm_mailTemplate.txt"), model));
            return content.toString();
        } catch (Exception e) {
            System.out.println("Exception occured while processing fmtemplate:" + e.getMessage());
        }
        return "";
    }

}
