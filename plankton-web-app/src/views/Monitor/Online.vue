<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.code" placeholder="CODE"></el-input>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-search" :label="$t('action.search')" perms="monitor:online:view" type="primary"
                     @click="findPage(null)"/>
        </el-form-item>
      </el-form>
    </div>
    <div class="toolbar" style="float:right;padding-top:10px;padding-right:15px;">
      <el-form :inline="true" :size="size">
        <el-form-item>
          <el-button-group>
            <el-tooltip content="刷新" placement="top">
              <el-button icon="fa fa-refresh" @click="findPage(null)"></el-button>
            </el-tooltip>
            <el-tooltip content="列显示" placement="top">
              <el-button icon="fa fa-filter" @click="displayFilterColumnsDialog"></el-button>
            </el-tooltip>
            <el-tooltip content="导出" placement="top">
              <el-button icon="fa fa-file-excel-o"></el-button>
            </el-tooltip>
          </el-button-group>
        </el-form-item>
      </el-form>
      <!--表格显示列界面-->
      <table-column-filter-dialog ref="tableColumnFilterDialog" :columns="columns"
                                  @handleFilterColumns="handleFilterColumns">
      </table-column-filter-dialog>
    </div>
    <!--表格内容栏-->
    <kt-table permsEdit="system:user:update" permsDelete="monitor:online:delete"
              :data="pageResult" :columns="filterColumns"
              @findPage="findPage" @handleDelete="handleDelete">
    </kt-table>
  </div>
</template>

<script>
import PopupTreeInput from "@/components/PopupTreeInput"
import KtTable from "@/views/Core/KtTable"
import KtButton from "@/views/Core/KtButton"
import TableColumnFilterDialog from "@/views/Core/TableColumnFilterDialog"
import {format} from "@/utils/datetime"
import ModelTreeSelect from "../../components/ModelTreeSelect/index";

export default {
  components: {
    ModelTreeSelect,
    PopupTreeInput,
    KtTable,
    KtButton,
    TableColumnFilterDialog
  },
  data() {
    return {
      size: 'small',
      filters: {
        code: '',
      },
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        code: ''
      },
      columns: [],
      filterColumns: [],
      pageRequest: {current: 1, size: 999999999},
      pageResult: {},
      roles: [],
      nodeKey: 'name',
      defaultCheckedKeys: []
    }
  },
  methods: {
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.$api.user.online({
        'username': this.filters.code,
      }).then((res) => {
        this.pageResult = res.data
      }).then(data != null ? data.callback : '')
    },
    // 批量删除
    handleDelete: function (data) {
      this.$api.user.kickOut(data.params).then(data != null ? data.callback : '')
    },
    // 时间格式化
    dateFormat: function (row, column, cellValue, index) {
      return format(row[column.property])
    },
    // 处理表格列过滤显示
    displayFilterColumnsDialog: function () {
      this.$refs.tableColumnFilterDialog.setDialogVisible(true)
    },
    // 处理表格列过滤显示
    handleFilterColumns: function (data) {
      this.filterColumns = data.filterColumns
      this.$refs.tableColumnFilterDialog.setDialogVisible(false)
    },
    // 处理表格列过滤显示
    initColumns: function () {
      this.columns = [
        {prop: "code", label: "CODE", minWidth: 120},
        {prop: "loginTime", label: "登录时间", minWidth: 110, formatter: this.dateFormat},
        {prop: "ip", label: "IP", minWidth: 70},
        {prop: "address", label: "登录地点", minWidth: 70}
      ]
      this.filterColumns = JSON.parse(JSON.stringify(this.columns));
    },
  },
  mounted() {
    this.initColumns();
  }
}
</script>

<style scoped>

</style>
