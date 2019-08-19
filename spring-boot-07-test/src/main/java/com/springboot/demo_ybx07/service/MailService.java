package com.springboot.demo_ybx07.service;

import java.util.Map;

/**
 *发送邮件相关service .
 *
 * @author 杨冰鑫
 * @since 2019/8/14 15:30
 */

public interface MailService {
    /**
     * 发送普通文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);
    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    void sendHtmlMail(String to, String subject, String content);
    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    void sendAttachmentMail(String to, String subject, String content, String filePath);
    /**
     * 发送带图片的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 文本
     * @param imgMap 图片ID(imgMap--存储key/val,key是ID)，用于在<img>标签中使用，从而显示图片
     */
    void sendInlineResourceMail(String to, String subject, String content, Map<String,String> imgMap);

    /**
     *发送模板邮件
     * @param to 收件人
     * @param subject 主题
     * @param paramMap 附带信息
     * @param template 模板文件名称
     */
    void sendTemplateMail(String to, String subject, Map<String, Object> paramMap, String template);
}
