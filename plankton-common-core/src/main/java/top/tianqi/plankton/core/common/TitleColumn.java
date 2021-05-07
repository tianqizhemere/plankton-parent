package top.tianqi.plankton.core.common;

import java.io.Serializable;

/**
 * Excel导出自定义TitleColumn类（标题、列宽）
 * @author Wukh
 * @create 2021-05-06
 */
public class TitleColumn implements Serializable {

    private static final long serialVersionUID = 554056332470178696L;

    /** 标题 */
    private String title;

    /** 列宽 */
    private int column;

    public TitleColumn() {
    }

    public TitleColumn(String title, int column) {
        this.title = title;
        this.column = column;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
