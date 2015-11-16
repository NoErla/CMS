/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.action;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.shishuo.cms.exception.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 首页
 *
 * @author Herbert
 */
@Controller
public class IndexAction extends BaseAction {
    /**
     * Kaptcha 验证码
     */
    @Autowired
    private DefaultKaptcha captchaProducer;

    /**
     * 首页
     *
     * @param p
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defalut(
            @RequestParam(value = "p", defaultValue = "1") long p,
            ModelMap modelMap) {
        return home(p, modelMap);
    }

    /**
     * 首页
     *
     * @param p
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String home(@RequestParam(value = "p", defaultValue = "1") long p,
                       ModelMap modelMap) {
        try {
            modelMap.addAttribute("p", p);
            modelMap.addAttribute("g_folderId", 0);
            return themeService.getDefaultTemplate();
        } catch (TemplateNotFoundException e) {
            modelMap.addAttribute("g_folderId", 0);
            logger.fatal(e.getMessage());
            return themeService.get404();
        }
    }

    /**
     * 404
     *
     * @return
     */
    @RequestMapping(value = "/404.htm", method = RequestMethod.GET)
    public String pageNotFound(ModelMap modelMap) {
        modelMap.addAttribute("g_folderId", 0);
        return themeService.get404();
    }

    /**
     * 500
     *
     * @return
     */
    @RequestMapping(value = "/500.htm", method = RequestMethod.GET)
    public String error(ModelMap modelMap) {
        modelMap.addAttribute("g_folderId", 0);
        return themeService.get500();
    }

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/captcha.htm", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        request.getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);

        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
