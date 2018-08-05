package com.ethan.action;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Andy
 * controller 基类
 *
 */
public abstract class ControllerSupper {
	public final static String ERROR = "error";
    public final static String SUCCESS = "success";
//    public Map<String,Object> respond = new HashMap<String,Object>();
    
    protected Logger log = Logger.getLogger(this.getClass());

    /**
     * 添加Model消息
     *
     * @param messages
     */
    protected void addMessage(Model model, String messages) {
        model.addAttribute("message", messages);
    }
    
    /**
     * 添加Model消息
     * @param type 消息类型
     * @param messages
     */
    protected void addMessage(Model model,String type, String messages) {
        model.addAttribute("message", messages);
        model.addAttribute("type", type);
    }

    /**
     * 添加Flash消息
     *
     * @param messages
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String messages) {
        redirectAttributes.addFlashAttribute("message", messages);
    }
    
    /**
     * 添加Flash消息
     * @param type 消息类型
     * @param messages
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String type, String messages) {
        redirectAttributes.addFlashAttribute("message", messages);
        redirectAttributes.addFlashAttribute("type", type);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletRequest request){
    	log.info(ex);
            log.error("当前用户没有此权限");
            return "errors/403";

    }

    /**
     * @description Contorler层通用的增删操作
     * @param service 用于调用service的方法的实例
     * @param method 需要调用的service方法名
     * @param param 方法参数
     * @param respond 返回参数
     * @throws Exception
     */
    private void doAction(Object service,String method,Object param,Map respond)  {
        Method fun = null;
        try {
            fun = service.getClass().getDeclaredMethod(method,param.getClass());
        } catch (NoSuchMethodException e) {
            respond.put("code",-1);
            respond.put("msg","Not defined such action");
        }
        try {
            fun.invoke(service,param);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        respond.put("code",1);
        respond.put("msg",method+" action has been done successfully");
    }

    /**
     * @description doAction方法之前的操作
     * @param respond
     */
    private void initBody(Map respond) {
        respond.clear();
    }

    /**
     * @description doAction方法之后的操作 用于异常处理
     * @param e
     * @param respond
     */
    private void catchException(Exception e,Map respond) {
        respond.put("code",-1);
        respond.put("msg",e.getMessage());
    }

    /**
     * @description controler接口
     * @param service
     * @param method
     * @param param
     * @return
     */
    public Map doRespond(Object service,String method,Object param) {
        Map<String,Object> respond = new HashMap<>();
        initBody(respond);
        try {
            doAction(service, method, param, respond);
        }
        catch (Exception e) {
            respond.put("code",-1);
            respond.put("msg",e.getMessage());
        }
        return respond;
    }
}
