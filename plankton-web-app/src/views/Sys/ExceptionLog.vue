<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-search" :label="$t('action.search')" perms="system:log:view" type="primary"
                     @click="findPage(null)"/>
        </el-form-item>
      </el-form>
    </div>
    <!--表格内容栏-->
    <kt-table :height="720"
              :data="pageResult" :columns="columns" :showOperation="showOperation" @findPage="findPage">
    </kt-table>
  </div>
</template>

<script>
import KtTable from "@/views/Core/KtTable"
import KtButton from "@/views/Core/KtButton"
import {format} from "@/utils/datetime"

export default {
  components: {
    KtTable,
    KtButton
  },
  data() {
    return {
      size: 'small',
      filters: {
        name: '',
        type:''
      },
      columns: [
        {prop: "id", label: "ID", minWidth: 60},
        {prop: "code", label: "请求人code", minWidth: 100},
        {prop: "message", label: "异常信息", minWidth: 220},
        {prop: "name", label: "异常名称", minWidth: 100},
        {prop: "requestParam", label: "请求参数", minWidth: 100},
        {prop: "method", label: "操作方法", minWidth: 120},
        /*{prop:"responseParam", label:"响应参数", minWidth:220},*/
        {prop: "uri", label: "URI", minWidth: 100},
        {prop: "ip", label: "IP", minWidth: 100},
        {prop: "createTime", label: "异常发生时间", minWidth: 120, formatter: this.dateFormat}
      ],
      // 分页对象
      pageRequest: {current: 1, pageSize: 10},
      // 分页数据
      pageResult: {},
      showOperation: false,

      // 数据操作类型
      operationTypes:[],
    }
  },
  methods: {
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.pageRequest.columnFilters = {userName: {name: 'userName', value: this.filters.name}}
      this.$api.exceptionLog.findPage({
        "pageNum": this.pageRequest.current,
        'pageSize': this.pageRequest.size,
        'name': this.filters.name,
        'type': this.filters.type
      }).then((res) => {
        this.pageResult = res.data
      }).then(data != null ? data.callback : '')
    },
    // 时间格式化
    dateFormat: function (row, column, cellValue, index) {
      return format(row[column.property])
    }
  },
  mounted() {

  }
}
</script>

<style scoped>

</style>
