<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-search" :label="$t('action.search')" perms="system:externalApplication:view"
                     type="primary"
                     @click="findPage(null)"/>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-plus" :label="$t('action.add')" perms="system:externalApplication:save" type="primary"
                     @click="handleAdd"/>
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
    <kt-table :height="720" permsEdit="system:externalApplication:update"
              permsDelete="system:externalApplication:delete"
              :data="pageResult" :columns="filterColumns"
              @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
    </kt-table>
    <!--新增编辑界面-->
    <el-dialog :title="operation?'新增':'编辑'" width="40%" :visible.sync="dialogVisible" :close-on-click-modal="false">
      <el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size"
               label-position="left">
        <el-form-item label="ID" prop="id" v-if="false">
          <el-input v-model="dataForm.id" :disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input placeholder="请输入应用名称" v-model="dataForm.title" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="versionDesc">
          <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="dataForm.content"
                    auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :size="size" @click.native="dialogVisible = false">{{ $t('action.cancel') }}</el-button>
        <el-button :size="size" type="primary" @click.native="operation ? submitForm('save') : submitForm('edit')"
                   :loading="editLoading">
          {{ $t('action.submit') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import PopupTreeInput from "@/components/PopupTreeInput"
import KtTable from "@/views/Core/KtTable"
import KtButton from "@/views/Core/KtButton"
import TableColumnFilterDialog from "@/views/Core/TableColumnFilterDialog"
import {format} from "@/utils/datetime"
import TestCode from "../../components/treeSelect/TreeSelect";
import * as external from "../../http/moudules/notice";

export default {
  components: {
    TestCode,
    PopupTreeInput,
    KtTable,
    KtButton,
    TableColumnFilterDialog
  },
  data() {
    return {
      size: 'small',
      filters: {
        title: '',
      },
      columns: [],
      filterColumns: [],
      pageRequest: {current: 1, size: 10},
      pageResult: {},

      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        title: [
          {required: true, message: '请输入标题名称', trigger: 'blur'}
        ],
      },
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        title: '',
        content: '',
      },
    }
  },
  methods: {
    // 获取分页数据
    findPage(data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.$api.notice.findPage({
        'pageNum': this.pageRequest.current,
        'pageSize': this.pageRequest.size,
        'title': this.filters.title
      }).then((res) => {
        this.pageResult = res.data
      }).then(data != null ? data.callback : '')
    },
    // 批量删除
    handleDelete: function (data) {
      this.$api.notice.batchDelete(data.params).then(data != null ? data.callback : '')
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true
      this.operation = true
      this.dataForm = {
        id: 0,
        title: '',
        content: ''
      }
      this.fileList = []
    },
    // 显示编辑界面
    handleEdit: function (params) {
      this.dialogVisible = true
      this.operation = false
      params.row.isSuccess = true;
      this.dataForm = Object.assign({}, params.row)
    },
    // 编辑
    submitForm: function (val) {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true
            if (this.dataForm.modelName) {
              this.dataForm.model = this.dataForm.modelName;
            }
            let params = Object.assign({}, this.dataForm)
            params.enable = params.isEnable;
            if (val === 'save') {
              this.$api.notice.save(params).then((res) => {
                this.editLoading = false
                if (res.code == 200) {
                  this.$message({message: '操作成功', type: 'success'})
                  this.dialogVisible = false
                  this.$refs['dataForm'].resetFields()
                } else {
                  this.$message({message: '操作失败, ' + res.massage, type: 'error'})
                }
                this.findPage(null)
              })
            } else {
              this.$api.notice.edit(params).then((res) => {
                this.editLoading = false
                if (res.code == 200) {
                  this.$message({message: '操作成功', type: 'success'})
                  this.dialogVisible = false
                  this.$refs['dataForm'].resetFields()
                } else {
                  this.$message({message: '操作失败, ' + res.massage, type: 'error'})
                }
                this.findPage(null)
              })
            }
          })
        }
      })
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
        {prop: "id", label: "ID", minWidth: 50},
        {prop: "title", label: "标题", minWidth: 120},
        {prop: "content", label: "内容", minWidth: 120},
        {prop: "sendCount", label: "发送数量", minWidth: 100},
        {prop: "readCount", label: "读取数量", minWidth: 100},
        {prop: "createTime", label: "创建时间", minWidth: 80, formatter: this.dateFormat}
      ]
      this.filterColumns = JSON.parse(JSON.stringify(this.columns));
    }
  },
  mounted() {
    this.initColumns();
  }
}
</script>

<style scoped>

</style>
