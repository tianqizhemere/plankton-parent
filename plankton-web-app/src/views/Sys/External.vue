<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="应用名称"></el-input>
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
        <el-form-item label="应用名称" prop="name">
          <el-input placeholder="请输入应用名称" v-model="dataForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="版本编号" prop="versionCode">
          <el-input placeholder="请输入版本编号" v-model="dataForm.versionCode" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="应用描述" prop="versionDesc">
          <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="dataForm.versionDesc"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="文件类型" prop="externalType">
          <el-select placeholder="请选择" v-model="dataForm.externalType" filterable clearable style="width: 100%;">
            <el-option :label="item.typeName" v-for="item in options" :key="item.code" :value="item.code">
              {{ item.typeName }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="升级文件" prop="attachIds">
          <el-upload
              class="upload-demo"
              ref="uploadMutiple"
              drag
              action="/api/system/attach/uploadFile"
              :on-success="uploadSuccess"
              :on-change="handleChange"
              :headers="headers"
              :data="{ dataType: 2 }"
              :file-list="fileList"
              multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">可上传任意文件，且不超过100MB</div>
          </el-upload>

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
import * as external from "../../http/moudules/external";

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
        name: '',
        model: '',
        parentId: ''
      },
      columns: [],
      filterColumns: [],
      pageRequest: {current: 1, size: 10},
      pageResult: {},

      // 文件类型选项
      options: [],

      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        versionCode: [
          {required: true, message: '请输入版本号', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入应用名称', trigger: 'blur'}
        ],
        externalType: [
          {required: true, message: '请选择文件类型', trigger: 'blur'}
        ]
      },
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        name: '',
        versionCode: '',
        versionDesc: '',
        attachIds: '',
        externalType: '',
        parentId: '',
        isSuccess: false
      },
      // 文件列表
      fileList: [],
      logo: '',
      // 机型树形数据
      tableTreeDdata: [],
      popupTreeData: [],
      popupTreeProps: {
        label: 'name',
        children: 'children'
      },
      headers: {Authorization: sessionStorage.getItem('token')},
    }
  },
  methods: {
    // 获取文件列表
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    uploadSuccess(response, file, fileList) {
      var attachIds = "";
      response.data.map((val) => {
        fileList.map((obj) => {
          if (val.orginalName == obj.name) {
            obj.url = val.path
          }
        })
      })
      fileList.map((obj) => {
        if (obj.id) {
          attachIds = attachIds + obj.id + ","
        } else {
          attachIds = attachIds + obj.response.data[0].id + ','
        }
      })
      this.dataForm.attachIds = attachIds;
    },
    fileChange(file) {
      /**
       * 1. 清除文件对象
       * 2. 取出上传文件的对象,在其它地方也可以使用
       * 3. 重新手动赋值filstList,免得自定义上传成功了,而fileList并没有动态改变,这样每次都是上传一个对象
       * */
      this.$refs.upload.clearFiles()
      this.logo = file.raw
      this.fileList = [{name: file.name, url: file.url}]
    },
    // 获取分页数据
    findPage(data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.$api.external.findPage({
        'pageNum': this.pageRequest.current,
        'pageSize': this.pageRequest.size,
        'name': this.filters.name
      }).then((res) => {
        this.pageResult = res.data
      }).then(data != null ? data.callback : '')
    },
    // 批量删除
    handleDelete: function (data) {
      this.$api.external.batchDelete(data.params).then(data != null ? data.callback : '')
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true
      this.operation = true
      this.dataForm = {
        id: 0,
        versionCode: '',
        name: '',
        versionDesc: '',
        parentId: '',
        attachIds: '',
        externalType: '',
        isSuccess: true
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
            if (this.dataForm.isSuccess) {
              this.editLoading = true
              if (this.dataForm.modelName) {
                this.dataForm.model = this.dataForm.modelName;
              }
              let params = Object.assign({}, this.dataForm)
              params.enable = params.isEnable;
              if (val === 'save') {
                this.$api.external.save(params).then((res) => {
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
                this.$api.external.edit(params).then((res) => {
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
            } else {
              this.$message({message: '文件上传中! ', type: 'warning'})
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
        {prop: "name", label: "应用名称", minWidth: 120},
        {prop: "versionCode", label: "版本编号", minWidth: 120},
        {prop: "versionDesc", label: "应用描述", minWidth: 100},
        {prop: "downloadUrl", label: "下载路径", minWidth: 120},
        {prop: "fileType", label: "文件类型", minWidth: 100},
        {prop: "typeName", label: "状态", minWidth: 70},
        {prop: "createTime", label: "创建时间", minWidth: 80, formatter: this.dateFormat},
        {prop: "modifyTime", label: "更新时间", minWidth: 80, formatter: this.dateFormat}
      ]
      this.filterColumns = JSON.parse(JSON.stringify(this.columns));
    },
    getOption() {
      this.$api.external.getOption().then(res => {
        this.options = res.data;
      })
    }
  },
  mounted() {
    this.initColumns();
    this.getOption();
  }
}
</script>

<style scoped>

</style>
