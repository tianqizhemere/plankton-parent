package top.tianqi.plankton.util.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import top.tianqi.plankton.core.common.TitleColumn;
import top.tianqi.plankton.core.common.annotation.excel.ExcelColumn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel导出工具类
 *
 * @author Wukh
 * @create 2021-05-06
 */
public final class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private ExcelUtil() {}

    /** 标题行高 */
    private static final float TITLE_ROW_HEIGHT = 30;

    /** 数据行高 */
    private static final float DATA_ROW_HEIGHT = 25;

    /**
     * 导出excel
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param fileName excel文件名
     * @param excelData 导出的数据列表
     * @param clazz 导出数据的字节码对象
     */
    public static void exportExcel(HttpServletRequest request, HttpServletResponse response, String fileName, List<?> excelData, Class<?> clazz) {
        try {
            logger.info(">>>>>>>>>>>>>>>>>> 导出excel开始 >>>>>>>>>>>>>>");
            HSSFWorkbook resultWb = new HSSFWorkbook();
            HSSFSheet sheet = resultWb.createSheet(fileName);

            // 根据类类型信息获取导出的excel对应的标题和列宽 key-列号，value-标题和列宽
            Map<Integer, TitleColumn> orderTitleAndColumnMap = getTitleColumnMap(clazz);

            // 设置标题列宽
            orderTitleAndColumnMap.forEach((k, v) -> sheet.setColumnWidth(k, v.getColumn() * 256));

            HSSFRow row0 = sheet.createRow(0);
            // 设置标题行高
            row0.setHeightInPoints(TITLE_ROW_HEIGHT);

            // 创建标题单元格格式
            HSSFCellStyle titleCellStyle = getCellStyle(resultWb, 11, true, HSSFColor.BLACK.index);
            // 填充标题行内容
            orderTitleAndColumnMap.forEach((k, v) -> {
                HSSFCell row0Cell = row0.createCell(k);
                row0Cell.setCellValue(v.getTitle());
                row0Cell.setCellStyle(titleCellStyle);
            });

            // 创建正文单元格格式
            HSSFCellStyle dataStyle = getCellStyle(resultWb, 11, false, HSSFColor.BLACK.index);

            // 将正文转换为excel数据
            logger.info(">>>>>>>>>>>>>>> Excel导出数据转换中 >>>>>>>>>>>>>>");
            int rowNum = 1;
            for (Object data : excelData) {
                HSSFRow row = sheet.createRow(rowNum++);
                row.setHeightInPoints(DATA_ROW_HEIGHT);
                // 获取对象值 key-列号 value-String值
                Map<Integer, String> orderValueMap = getValueMap(data);
                orderValueMap.forEach((k, v) -> {
                            HSSFCell cell = row.createCell(k);
                            cell.setCellValue(v);
                            cell.setCellStyle(dataStyle);
                        }
                );
            }
            logger.info(">>>>>>>>>>>>>>> Excel导出数据转换完成 >>>>>>>>>>>>>>");
            String downFileName = fileName.concat(".xlsx");
            // application/x-download
            response.setContentType("application/vnd.ms-excel; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; ".concat(encodeFileName(request, downFileName)));
            OutputStream outputStream = response.getOutputStream();
            resultWb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            logger.info(">>>>>>>>>>>>>>>>>> 导出excel结束 >>>>>>>>>>>>>>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取类的属性对应单元格标题和列宽
     *
     * @param clz 字节码对象
     * @return 返回结果数据
     */
    private static Map<Integer, TitleColumn> getTitleColumnMap(Class<?> clz) {
        Map<Integer, TitleColumn> orderTitleColumnMap = new HashMap<>();
        Field[] fs = clz.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(ExcelColumn.class)) {
                Integer order = f.getAnnotation(ExcelColumn.class).order();
                String title = f.getAnnotation(ExcelColumn.class).title();
                int column = f.getAnnotation(ExcelColumn.class).column();
                TitleColumn titleColumn = new TitleColumn(title, column);
                orderTitleColumnMap.put(order, titleColumn);
            }
        }
        return orderTitleColumnMap;
    }

    /**
     * 获取单元格样式
     *
     * @param workbook     HSSFWorkbook
     * @param fontSize     字体大小
     * @param isBolaWeight 是否字体加粗
     * @param color        字体颜色
     * @return 返回结果数据
     */
    private static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, int fontSize, boolean isBolaWeight, short color) {
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置水平居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) fontSize);
        // 设置字体颜色
        font.setColor(color);
        // 设置字体类型
        font.setFontName("宋体");
        // 是否需要字体加粗
        if (isBolaWeight) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        style.setWrapText(true);
        style.setFont(font);
        return style;
    }

    /**
     * 获取对象的属性对应单元格坐标和值的键值对
     *
     * @param obj 转换的对象
     * @return 返回结果数据
     */
    private static Map<Integer, String> getValueMap(Object obj) throws IllegalAccessException {
        Map<Integer, String> result = new HashMap<>();
        Class<?> clz = obj.getClass();
        Field[] fs = clz.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(ExcelColumn.class)) {
                Integer order = f.getAnnotation(ExcelColumn.class).order();
                String value = "";
                Object valueObj = f.get(obj);
                if (valueObj != null) {
                    // 日期格式进行特殊处理
                    if (f.getType() == Date.class) {
                        String pattern = f.getAnnotation(ExcelColumn.class).pattern();
                        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                        Date dateTime = (Date) valueObj;
                        value = dateFormat.format(dateTime);
                    } else {
                        // 其他格式调用toString方法，这里枚举就需要定义自己的toString方法
                        value = valueObj.toString();
                    }
                }
                result.put(order, value);
            }
        }
        return result;
    }

    /**
     * 根据不同的浏览器生成不同类型中文文件名编码
     *
     * @param request  HttpServletRequest
     * @param fileName Excel文件名
     * @return 返回结果数据
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    private static String encodeFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        String new_filename = URLEncoder.encode(fileName, "UTF8").replaceAll("\\+", "%20");
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        Assert.notNull(agent, "代理浏览器不存在！！！");
        if (agent.contains("msie")) {
            /*
             * IE浏览器，只能采用URLEncoder编码
             */
            return "filename=\"" + new_filename + "\"";
        } else {
            String s = "filename=\"" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1") + "\"";
            if (agent.contains("applewebkit")) {
                /*
                 * Chrome浏览器，只能采用ISO编码的中文输出
                 */
                return s;
            } else if (agent.contains("opera")) {
                /*
                 * Opera浏览器只可以使用filename*的中文输出
                 * RFC2231规定的标准
                 */
                return "filename*=" + new_filename;
            } else if (agent.contains("safari")) {
                /*
                 * Safani浏览器，只能采用iso编码的中文输出
                 */
                return s;
            } else if (agent.contains("firefox")) {
                /*
                 * Firfox浏览器，可以使用filename*的中文输出
                 * RFC2231规定的标准
                 */
                return "filename*=" + new_filename;
            } else {
                return "filename=\"" + new_filename + "\"";
            }
        }
    }
}
