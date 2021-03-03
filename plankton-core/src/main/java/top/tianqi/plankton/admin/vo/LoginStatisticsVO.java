package top.tianqi.plankton.admin.vo;

/**
 * 统计登录相关信息VO
 * @author Wukh
 * @create 2021-03-03
 */
public class LoginStatisticsVO {

    /** 统计日期 */
    private String statisticsDate;

    /** 登录次数 */
    private Integer count;

    public LoginStatisticsVO() {
    }

    public LoginStatisticsVO(String statisticsDate, Integer count) {
        this.statisticsDate = statisticsDate;
        this.count = count;
    }

    public String getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(String statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
