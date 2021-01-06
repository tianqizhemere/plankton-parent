package top.tianqi.plankton.common.base;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基类controller
 * @author Wukh
 * @create 2021-01-04
 */
public class BaseController {

    Map<String,String> orderTypeMap = new HashMap<>(2);

    public BaseController(){
        orderTypeMap.put("asc","asc");
        orderTypeMap.put("desc","desc");
    }

}
