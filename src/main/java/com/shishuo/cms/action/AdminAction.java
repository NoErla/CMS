/*
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 */

package com.shishuo.cms.action;

import com.shishuo.cms.constant.SystemConstant;
import com.shishuo.cms.entity.vo.JsonVo;
import com.shishuo.cms.service.AdminService;
import com.shishuo.cms.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Herbert
 */

@Controller
@RequestMapping("/admin")
public class AdminAction extends BaseAction {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String login(HttpServletRequest request, ModelMap modelMap) {
        return "/manage/login";
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String adminLogout(HttpServletRequest request, ModelMap modelMap) {
        request.getSession().removeAttribute(SystemConstant.SESSION_ADMIN);
        return "redirect:" + HttpUtils.getBasePath(request);
    }

    @ResponseBody
    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    public JsonVo<String> adminLogin(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "captcha") String captcha,
                                     HttpServletRequest request, ModelMap modelMap) {
        JsonVo<String> json = new JsonVo<String>();

        try {
            String kaptcha = (String) request.getSession().getAttribute(
                    com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if (StringUtils.isBlank(password)) {
                json.getErrors().put("password", "密码不能为空");
            } else if (password.length() < 6 && password.length() > 30) {
                json.getErrors().put("password", "密码最少6个字符，最多30个字符");
            }
            // 校验验证码
            if (StringUtils.isNotBlank(kaptcha) && kaptcha.equalsIgnoreCase(captcha)) {

            } else {
                json.getErrors().put("captcha", "验证码错误");
            }
            json.check();

            adminService.adminLogin(name, password, request);

        } catch (Exception e) {
            // 异常，重置验证码
            request.getSession().removeAttribute(
                    com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            json.setResult(false);
            json.getErrors().put("password", "邮箱或密码错误");
            json.setMsg("change_captcha");
        }
        return json;
    }


}
