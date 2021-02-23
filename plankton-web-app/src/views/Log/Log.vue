<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="模块名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select placeholder="数据操作类型" v-model="filters.type" filterable clearable style="width: 100%;">
            <el-option :label="item.typeName" v-for="item in operationTypes" :key="item.code" :value="item.typeName">{{ item.typeName }}</el-option>
          </el-select>
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
        {prop: "model", label: "模块名称", minWidth: 100},
        {prop: "type", label: "操作类型", minWidth: 120},
        {prop: "operationDesc", label: "操作描述", minWidth: 80},
        {prop: "method", label: "方法", minWidth: 180},
        {prop: "requestParam", label: "请求参数", minWidth: 220},
        /*{prop:"responseParam", label:"响应参数", minWidth:220},*/
        {prop: "ip", label: "IP", minWidth: 120},
        {prop: "code", label: "请求人code", minWidth: 100},
        {prop: "createTime", label: "创建时间", minWidth: 120, formatter: this.dateFormat}
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
      this.$api.log.findPage({
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
    },
    // 获取数据操作类型
    getOperationType(){
      this.$api.log.getOperationType().then(res => {
        this.operationTypes = res.data;
      })
    }
  },
  mounted() {
    this.getOperationType();
  }
}
</script>

<style scoped>

</style>
