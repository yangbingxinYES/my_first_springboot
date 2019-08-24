package com.springboot.demo_ybx07.service.impl;

import com.springboot.demo_ybx07.service.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 发送邮件相关serviceImpl.
 *
 * @author 杨冰鑫
 * @since 2019/8/14 15:31
 */
@Service
@Data
@Slf4j
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${spring.mail.username}")
    //使用@Value注入application.properties中指定的用户名
    private String from;

    @Autowired
    //用于发送文件
    private JavaMailSender mailSender;

    /**
     * 用于发送模板邮件
     */
    @Autowired
    private TemplateEngine templateEngine;
    /**
     * 简单邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
       //简单邮件msg
        SimpleMailMessage message = new SimpleMailMessage();
        //收信人
        message.setTo(to);
        //主题
        message.setSubject(subject);
        //内容
        message.setText(content);
        //发信人
        message.setFrom(from);
        try {
            mailSender.send(message);
            logger.info("发送简单邮件");
        }catch (Exception e){
            logger.info("发送简单邮件失败");
        }

    }

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //复杂邮件msg
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            // true 为 HTML 邮件
            messageHelper.setText(content, true);
            mailSender.send(message);
            log.info("【HTML 邮件】成功发送！to={}", to);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("【HTML 邮件】发送失败！to={}", to);
        }

    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content, String filePath){
//        MimeMessage mimeMailMessage = null;
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);
            //文件路径
  /*          FileSystemResource
                file = new FileSystemResource(new File(filePath));
//            FileSystemResource
//                file = new FileSystemResource(new File("src/main/resources/static/image/mail.jpg"));
            //名称名称
            String fileName = file.getFilename();
//            mimeMessageHelper.addAttachment("mail.jpg", file);
            mimeMessageHelper.addAttachment(fileName, file);*/

            //文件路径,分割成数组
            String[] filePaths = filePath.split(",");
            for (String files: filePaths) {
                FileSystemResource
                    file = new FileSystemResource(new File(files));
                //名称名称
                String fileName = file.getFilename();
                mimeMessageHelper.addAttachment(fileName, file);
            }
            mailSender.send(mimeMailMessage);
            log.info("邮件发送成功！");
        } catch (Exception e) {
            logger.error("邮件发送失败!", e.getMessage());
        }
    }

    @Override
    public void sendInlineResourceMail(
        String to, String subject, String content, Map<String,String> imgMap) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            // 添加图片
            for (Map.Entry<String, String> entry : imgMap.entrySet()) {
                FileSystemResource fileResource = new FileSystemResource(new File(entry.getValue()));
                if (fileResource.exists()) {
                    String filename = fileResource.getFilename();
                    messageHelper.addInline(entry.getKey(), fileResource);
                }
            }
            mailSender.send(mimeMessage);
            log.info("【图片邮件】成功发送！to={}", to);
        }catch (Exception e){
            log.error("【图片邮件】发送失败",e.getMessage());
        }
    }

    /**
     * Thymeleaf式模板邮件
     * @param to 收件人
     * @param subject 主题
     * @param paramMap 附带信息
     * @param template 模板文件名称
     */
    @Override
    public void sendTemplateMail(
        String to, String subject, Map<String, Object> paramMap, String template) {
        try {
            Context context = new Context();
            // 设置变量的值
            context.setVariables(paramMap);
            String emailContent = templateEngine.process(template, context);
            //其原理是再次发送一个html邮件，只是把数据和返回的模板整合到一起注入到要返回的内容中，看如下：
            sendHtmlMail(to, subject, emailContent);
            log.info("发送模板邮件成功 to={}",to);
        }catch (Exception e){
            log.error("发送模板邮件失败",e.getMessage());
        }
    }
}
