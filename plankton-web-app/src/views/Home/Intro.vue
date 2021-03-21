<template>
  <div :class="'multi-page not-menu-page home-page'">
    <el-row :gutter="8" class="head-info">
      <el-card class="head-info-card">
        <el-col :span="12">
          <div class="head-info-avatar">
            <img alt="头像" :src="avatar">
          </div>
          <div class="head-info-count">
            <div class="head-info-welcome">
              {{welcomeMessage}}
            </div>
            <div class="head-info-desc">
              <p>{{'暂无部门'}} | {{'暂无角色'}}</p>
            </div>
            <div class="head-info-time">登录时间：{{personalInfo.loginTime }}</div>
          </div>
        </el-col>
        <el-col :span="12">
          <div>
            <el-row class="more-info">
              <el-col :span="4"></el-col>
              <el-col :span="4"></el-col>
              <el-col :span="4"></el-col>
              <el-col :span="4">
                <head-info title="今日IP" :content="todayIp" :center="false" :bordered="false"/>
              </el-col>
              <el-col :span="4">
                <head-info title="今日访问" :content="todayVisitCount" :center="false" :bordered="false"/>
              </el-col>
              <el-col :span="4">
                <head-info title="总访问量" :content="totalVisitCount" :center="false" />
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-card>
    </el-row>
    <el-row :gutter="8" class="count-info">
      <el-col :span="12" class="visit-count-wrapper">
        <el-card class="visit-count">
          <div  id="countChart" style="height: 400px;border:1px solid  #f1f1f1;border-radius: 5px" ></div>
        </el-card>
      </el-col>
      <el-col :span="12" class="project-wrapper">
        <el-card title="拓展区域" class="project-card" v-if="loadRepo === 1">
          <a href="" target="_blank" slot="extra">所有项目</a>
          <running-task :projects="projects">
          </running-task>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import moment from "moment";
import HeadInfo from './HeadInfo'
moment.locale("zh-cn");
import RunningTask from './RunningTask'
export default {
  name: "HomePage",
  data() {
    return {
      avatar: require("../../../static/avatar/c7c4ee7be3eb4e73a19887dc713505145.jpg"),
      editable: false,
      series: [],
      projects: [],
      todayIp: '',
      todayVisitCount: '',
      totalVisitCount: '',
      userRole: '',
      userDept: '',
      lastLoginTime: '',
      welcomeMessage: '',
      loadRepo: 0,
      htmlspan: '<span style="display:inline-block;margin-right: 5px;border-radius: 10px;width: 10px;height: 10px;background-color: ',
      legends: ['总数', '您'],
      myChart: {},
      personalInfo: {
        id: 0,
        code: '',
        createTime: '2021-1-1 12:59:59',
        phone: '15575731038',
        isEnable: 1,
        loginTime : '2021-1-1 12:59:59'
      }
    };
  },
  components: {RunningTask, HeadInfo},
  methods: {
    welcome () {
      const date = new Date()
      const hour = date.getHours()
      let time = hour < 6 ? '美好的一天开始' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour <= 18 ? '下午好' : '晚上好')))
      let welcomeArr = [
        '喝杯咖啡休息下吧☕',
        '要不要和朋友打局LOL',
        '要不要和朋友打局王者荣耀',
        '几天没见又更好看了呢😍',
        '今天又写了几个Bug🐞呢',
        '今天在群里吹水了吗',
        '今天吃了什么好吃的呢',
        '今天您微笑了吗😊',
        '今天帮助别人解决问题了吗',
        '准备吃些什么呢',
        '周末要不要去看电影？'
      ]
      let index = Math.floor((Math.random() * welcomeArr.length))
      return `${time}，${this.personalInfo.username}`
    },
    getRepos () {

    },
    runningProject () {
      let that = this
      this.$api.loginLog.visitCount({code: this.personalInfo.username}).then((r) => {
        let data = r.data
        this.todayIp = data.todayIp
        this.todayVisitCount = data.todayVisitCount
        this.totalVisitCount = data.totalVisitCount
        let dateArr = []
        let totalSeries = {name: '总数', data: [], type: 'bar'}
        let yourSeries = {name: '您', data: [], type: 'bar'}
        for (let i = 6; i >= 0; i--) {
          let time = moment().subtract(i, 'days').format('MM-DD')
          let contain = false
          for (let o of data.lastSevenVisitCount) {
            if (o.days === time) {
              contain = true
              totalSeries.data.push(o.count)
            }
          }
          if (!contain) {
            totalSeries.data.push(0)
          }
          dateArr.push(time)
        }
        this.series.push(totalSeries)
        for (let i = 6; i >= 0; i--) {
          let time = moment().subtract(i, 'days').format('MM-DD')
          let contain = false
          for (let o of data.lastSevenUserVisitCount) {
            if (o.days === time) {
              contain = true
              yourSeries.data.push(o.count)
            }
          }
          if (!contain) {
            yourSeries.data.push(0)
          }
        }
        this.series.push(yourSeries)
        this.myChart.setOption({
          title: {
            text: '近7日系统访问记录',
            show: true,
            left: 10,
            top: 10
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'line'
            },
            formatter: function name (params) {
              let htmlTip = ''
              for (let i = 0; i < params.length; i++) {
                if (i === 0) {
                  htmlTip += params[i].axisValue + '<br />'
                }
                if (i === (params.length - 1)) {
                  htmlTip += (that.htmlspan + params[i].color + ';"></span>' + params[i].seriesName + ' : ' + params[i].value)
                } else {
                  htmlTip += (that.htmlspan + params[i].color + ';"></span>' + params[i].seriesName + ' : ' + params[i].value + '<br />')
                }
              }
              return htmlTip
            }
          },
          legend: {
            type: 'scroll',
            x: 'center',
            y: 'bottom',
            textStyle: {
              fontSize: '12'
            },
            data: this.legends
          },
          toolbox: {
            show: true,
            right: 20,
            top: 10,
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: true,
            data: dateArr,
            axisLabel: {
              textStyle: {
                fontSize: '12'
              }
            }
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value}',
              textStyle: {
                fontSize: '12'
              }
            }
          },
          grid: {
            left: '4%'
          },
          series: this.series
        }, true)
      }).catch((r) => {
        console.error(r)
        that.$message.error('获取首页信息失败')
      })
    }
  },
  created () {
    this.getRepos()
    this.loadRepo = 1
  },
  mounted() {
    var user = sessionStorage.getItem("user")
    this.personalInfo.username = user
    this.$api.user.findByName({'username': user}).then((res) => {
        this.personalInfo = res.data
    });
    this.welcomeMessage = this.welcome()
    this.myChart = this.$echarts.init(document.getElementById('countChart'))
    this.runningProject()
  }
};
</script>

<style lang="scss">
.home-page .head-info {
  margin-bottom: .5rem;
}
.home-page .head-info .head-info-card {
  padding: .5rem;
  border-color: #f1f1f1;
}
.home-page .head-info .head-info-card .head-info-avatar {
  display: inline-block;
  float: left;
  margin-right: 1rem;
}
.home-page .head-info .head-info-card .head-info-avatar img {
  width: 5rem;
  border-radius: 2px;
}
.home-page .head-info .head-info-card .head-info-count {
  display: inline-block;
  float: left;
}
.home-page .head-info .head-info-card .head-info-count .head-info-welcome {
  font-size: 1.05rem;
  margin-bottom: .1rem;
}
.home-page .head-info .head-info-card .head-info-count .head-info-desc {
  color: rgba(0, 0, 0, 0.45);
  font-size: .8rem;
  padding: .2rem 0;
}
.home-page .head-info .head-info-card .head-info-count .head-info-desc p {
  margin-bottom: 0;
}
.home-page .head-info .head-info-card .head-info-count .head-info-time {
  color: rgba(0, 0, 0, 0.45);
  font-size: .8rem;
  padding: .2rem 0;
}
.home-page .count-info .visit-count-wrapper {
  padding-left: 0 !important;
}
.home-page .count-info .visit-count-wrapper .visit-count {
  padding: .5rem;
  border-color: #f1f1f1;
}
.home-page .count-info .visit-count-wrapper .visit-count .ant-card-body {
  padding: .5rem 1rem !important;
}
.home-page .count-info .project-wrapper {
  padding-right: 0 !important;
}
.home-page .count-info .project-wrapper .project-card {
  border: none !important;
}
.home-page .count-info .project-wrapper .project-card .ant-card-head {
  border-left: 1px solid #f1f1f1 !important;
  border-top: 1px solid #f1f1f1 !important;
  border-right: 1px solid #f1f1f1 !important;
}
.home-page .count-info .project-wrapper .project-card .ant-card-body {
  padding: 0 !important;
}
.home-page .count-info .project-wrapper .project-card .ant-card-body table {
  width: 100%;
}
.home-page .count-info .project-wrapper .project-card .ant-card-body table td {
  width: 50%;
  border: 1px solid #f1f1f1;
  padding: .6rem;
}
.home-page .count-info .project-wrapper .project-card .ant-card-body table td .project-avatar-wrapper {
  display: inline-block;
  float: left;
  margin-right: .7rem;
}
.home-page .count-info .project-wrapper .project-card .ant-card-body table td .project-avatar-wrapper .project-avatar {
  color: #42b983;
  background-color: #d6f8b8;
}
.home-page .count-info .project-wrapper .project-card .project-detail {
  display: inline-block;
  float: left;
  text-align: left;
  width: 78%;
}
.home-page .count-info .project-wrapper .project-card .project-detail .project-name {
  font-size: .9rem;
  margin-top: -2px;
  font-weight: 600;
}
.home-page .count-info .project-wrapper .project-card .project-detail .project-desc {
  color: rgba(0, 0, 0, 0.45);
}
.home-page .count-info .project-wrapper .project-card .project-detail .project-desc p {
  margin-bottom: 0;
  font-size: .6rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
