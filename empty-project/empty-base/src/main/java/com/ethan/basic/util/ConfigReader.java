package com.ethan.basic.util;

/**
 * Created by kyoka on 16/10/28.
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.UrlResource;

import java.io.File;

public class ConfigReader extends
        PropertyPlaceholderConfigurer implements BeanPostProcessor,
        InitializingBean {

    private static java.util.Properties pros;
    private String env;
    private String configDir;

    public Object postProcessAfterInitialization(Object arg0, String arg1)
            throws BeansException {
        return arg0;
    }

    public Object postProcessBeforeInitialization(Object arg0, String arg1)
            throws BeansException {
        return arg0;
    }

    public void afterPropertiesSet() throws Exception {
        pros = mergeProperties();
    }

    public java.util.Properties getPros() {
        return pros;
    }

    public static void setPros(java.util.Properties pros) {
        ConfigReader.pros = pros;
    }

    public static String getProperty(String varname){
        try{
            return pros.getProperty(varname);
        }catch(Exception e){
            return null;
        }
    }

    public static void setProperty(String key, String value){
        try{
            pros.setProperty(key, value);
        }catch(Exception e){
        }
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getConfigDir() {
        return configDir;
    }

    public void setConfigDir(String configDir) {
            try {
                String areaCode = System.getenv(env);
                File file = new File(configDir);
                UrlResource location = new UrlResource(new File(file, "config-" + areaCode + ".conf").toURI());
                this.setLocation(location);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        this.configDir = configDir;
    }

}