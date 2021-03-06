<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="版本编号"></el-input>
        </el-form-item>
        <el-form-item>
          <popup-tree-input
              :data="popupTreeData" :props="popupTreeProps"
              :prop="filters.model==null?'顶级菜单':filters.model"
              :nodeKey="''+filters.parentId" :currentChangeHandle="handleTreeSelectChange">
          </popup-tree-input>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-search" :label="$t('action.search')" perms="system:version:view" type="primary"
                     @click="findPage(null)"/>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-plus" :label="$t('action.add')" perms="system:version:save" type="primary"
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
    <kt-table :height="720" permsEdit="system:version:update" permsDelete="system:version:delete"
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
        <el-form-item label="版本编号" prop="versionCode">
          <el-input placeholder="请输入版本编号" v-model="dataForm.versionCode" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="升级信息" prop="versionDesc">
          <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="dataForm.versionDesc"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="下载路径" prop="downloadUrl">
          <el-input type="textarea" :rows="2" placeholder="发布大版本更新时填写！" v-model="dataForm.downloadUrl"
                    auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="设备型号" prop="model">
          <model-tree-select :data="popupTreeData"
                             :defaultProps="defaultProps" multiple
                             :nodeKey="nodeKey" :checkedKeys="defaultCheckedKeys"
                             @popoverHide="popoverHide"></model-tree-select>
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
              :data="{ dataType: 1 }"
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
        name: '',
        model: '',
        parentId: ''
      },
      columns: [],
      filterColumns: [],
      pageRequest: {current: 1, size: 10},
      pageResult: {},

      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        versionCode: [
          {required: true, message: '请输入版本号', trigger: 'blur'}
        ],
      },
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        versionCode: '',
        model: '',
        versionDesc: '',
        attachIds: '',
        modelName: '',
        parentId: '',
        downloadUrl: '',
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

      // model-tree-select 属性配置
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      nodeKey: 'name',
      defaultCheckedKeys: []
    }
  },
  methods: {
    popoverHide(checkedIds, checkedData) {
      let modelName = '';
      if (checkedData) {
        checkedData.forEach(val => {
          if (!val.children) {
            modelName += val.name + ",";
          }
        })
      }
      this.dataForm.modelName = modelName;
    },
    // 获取型号树形数据
    searchTreeData: function () {
      this.loading = true
      this.$api.dict.findDeptTree().then((res) => {
        this.tableTreeDdata = res.data
        this.popupTreeData = this.getParentMenuTree(res.data)
        this.loading = false
      })
    },
    // 获取上级字典树
    getParentMenuTree: function (tableTreeDdata) {
      let parent = {
        parentId: 0,
        name: '顶级菜单',
        children: tableTreeDdata
      }
      return [parent]
    },
    // 型号树选中
    handleTreeSelectChange(data, node) {
      this.filters.parentId = data.id
      this.filters.model = data.name
    },
    // 获取型号树形数据
    findTreeData() {
      this.loading = true
      this.$api.dict.findDeptTree().then((res) => {
        this.options = res.data
        this.loading = false
      })
    },
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
      this.pageRequest.columnFilters = {username: '', ieml: ''}
      this.$api.version.findPage({
        'pageNum': this.pageRequest.current,
        'pageSize': this.pageRequest.size,
        'name': this.filters.name,
        'dictId': this.filters.parentId
      }).then((res) => {
        this.pageResult = res.data
      }).then(data != null ? data.callback : '')
    },
    // 批量删除
    handleDelete: function (data) {
      this.$api.version.batchDelete(data.params).then(data != null ? data.callback : '')
    },
    // 显示新增界面
    handleAdd: function () {
      this.dialogVisible = true
      this.operation = true
      this.dataForm = {
        id: 0,
        versionCode: '',
        model: '',
        modelName: '',
        versionDesc: '',
        parentId: '',
        attachIds: '',
        downloadUrl: '',
        isSuccess: true
      }
      this.defaultCheckedKeys = []
      this.fileList = []
    },
    // 显示编辑界面
    handleEdit: function (params) {
      this.dialogVisible = true
      this.operation = false
      params.row.isSuccess = true;
      debugger
      this.defaultCheckedKeys = [params.row.model]
      this.fileList = []
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
                this.$api.version.save(params).then((res) => {
                  this.editLoading = false
                  if (res.code == 200) {
                    this.$message({message: '操作成功', type: 'success'})
                    this.dialogVisible = false
                    this.$refs['dataForm'].resetFields()
                  } else if (res.code === 1011) {
                    this.$message({message: '操作失败,' + res.massage, type: 'warning'})
                  } else {
                    this.$message({message: '操作失败, ' + res.massage, type: 'error'})
                  }
                  this.findPage(null)
                })
              } else {
                this.$api.version.edit(params).then((res) => {
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
        {prop: "versionCode", label: "版本编号", minWidth: 120},
        {prop: "model", label: "设备型号", minWidth: 120},
        {prop: "versionDesc", label: "升级信息", minWidth: 100},
        {prop: "typeName", label: "状态", minWidth: 70},
        {prop: "downloadUrl", label: "下载路径", minWidth: 120},
        {prop: "createTime", label: "创建时间", minWidth: 120, formatter: this.dateFormat}
      ]
      this.filterColumns = JSON.parse(JSON.stringify(this.columns));
    },
  },
  mounted() {
    this.searchTreeData();
    this.initColumns();
    this.findTreeData();
  }
}
</script>

<style scoped>

</style>
