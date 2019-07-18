package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/18 14:01
 */
@Component
public class MyRabbitHandler {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEamil;

    @RabbitListener(queues = "email_queue")
    public void  handler(Email email){
        executorService.submit(() -> {
            //发送邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //创建一个Spring提供的邮件帮助对象
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            try {
                messageHelper.setSubject(email.getSubject());

                messageHelper.setFrom(fromEamil);

                messageHelper.setTo(email.getTo());

                messageHelper.setText(email.getContent(), true);

                messageHelper.setSentDate(new Date());

                //发送邮件
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
