package top.tianqi.plankton.system.entity;


/**
 * 用户个性化设置
 * @author Wukh
 * @create 2021-01-23
 */
public class UserConfig {

    public static final String DEFAULT_THEME = "dark";
    public static final String DEFAULT_LAYOUT = "side";
    public static final String DEFAULT_MULTIPAGE = "0";
    public static final String DEFAULT_FIX_SIDERBAR = "1";
    public static final String DEFAULT_FIX_HEADER = "1";
    public static final String DEFAULT_COLOR = "rgb(66, 185, 131)";

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 系统主题 dark暗色风格，light明亮风格
     */
    private String theme;

    /**
     * 系统布局 side侧边栏，head顶部栏
     */
    private String layout;

    /**
     * 页面风格 1多标签页 0单页
     */
    private String multiPage;

    /**
     * 页面滚动是否固定侧边栏 1固定 0不固定
     */
    private String fixSiderbar;

    /**
     * 页面滚动是否固定顶栏 1固定 0不固定
     */
    private String fixHeader;

    /**
     * 主题颜色 RGB值
     */
    private String color;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getMultiPage() {
        return multiPage;
    }

    public void setMultiPage(String multiPage) {
        this.multiPage = multiPage;
    }

    public String getFixSiderbar() {
        return fixSiderbar;
    }

    public void setFixSiderbar(String fixSiderbar) {
        this.fixSiderbar = fixSiderbar;
    }

    public String getFixHeader() {
        return fixHeader;
    }

    public void setFixHeader(String fixHeader) {
        this.fixHeader = fixHeader;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
