<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="版本编号"></el-input>
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
    <kt-table :height="350" permsEdit="system:version:update" permsDelete="system:version:delete"
              :data="pageResult" :columns="filterColumns"
              @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete">
    </kt-table>
    <!--新增编辑界面-->
    <el-dialog :title="operation?'新增':'编辑'" width="40%" :visible.sync="dialogVisible" :close-on-click-modal="false">
      <el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size"
               label-position="right">
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
        <el-form-item label="设备型号" prop="model">
          <el-cascader
              :options="options"
              :props="props"
              ref="myCascader"
              collapse-tags
              @change="onProvincesChange"
              clearable v-model="dataForm.model" style="width: 100%;"></el-cascader>
        </el-form-item>
        <el-form-item label="升级文件" prop="attachIds">
          <el-upload
              ref="uploadMutiple"
              :auto-upload="false"
              action="fakeAction"
              :on-success="uploadSuccess"
              :on-change="handleChange"
              :file-list="fileList"
              multiple
          >选取文件
          </el-upload>
          <el-button type="primary" size="small" @click="submitUpload">上传到服务器</el-button>

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
import * as version from "../../http/moudules/version";

export default {
  components: {
    PopupTreeInput,
    KtTable,
    KtButton,
    TableColumnFilterDialog
  },
  data() {
    return {
      size: 'small',
      filters: {
        name: ''
      },
      columns: [],
      filterColumns: [],
      pageRequest: {pageNum: 1, pageSize: 10},
      pageResult: {},

      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        versionCode: [
          {required: true, message: '请输入版本号', trigger: 'blur'}
        ],
        model: [
          {required: true, message: '请输入设备型号', trigger: 'blur'}
        ],
      },
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        versionCode: '',
        model: '',
        versionDesc: '',
        attachIds: '',
        modelName: ''
      },
      roles: [],
      // 文件列表
      fileList: [],
      logo: '',
      // 级联组件,多选
      props: {multiple: true},
      options: [{
        label: 'Galaxy S10',
        value: '1',
        children: [{
          value: 'G973N',
          label: 'G973N',
        }, {
          value: 'G973F',
          label: 'G973F'
        }, {
          value: 'G975N',
          label: 'G975N',
        },
          {
            value: 'G975F',
            label: 'G975F',
          },
          {
            value: 'G977N',
            label: 'G977N',
          },
          {
            value: 'G977B',
            label: 'G977B',
          },
        ]
      },
        {
          label: 'Galaxy Note10',
          value: '2',
          children: [{
            value: 'N971N',
            label: 'N971N',
          }, {
            value: 'N976N',
            label: 'N976N',
          },
            {
              value: 'N976B',
              label: 'N976B',
            }
          ]
        },
        {
          label: 'Galaxy S20',
          value: '3',
          children: [{
            value: 'G981N',
            label: 'G981N',
          }, {
            value: 'G9810',
            label: 'G9810',
          },
            {
              value: 'G986N',
              label: 'G986N',
            },
            {
              value: 'G9860',
              label: 'G9860',
            },
            {
              value: 'G988N',
              label: 'G988N',
            },
            {
              value: 'G988N',
              label: 'G988N',
            }]
        },
        {
          label: 'Galaxy Note20',
          value: '4',
          children: [{
            value: 'N986N',
            label: 'N986N',
          }, {
            value: 'N9860',
            label: 'N9860'
          }]
        },
        {
          label: 'Galaxy S21U',
          value: '5',
          children: [{
            value: 'G998N',
            label: 'G998N',
          }, {
            value: 'G9980',
            label: 'G9980'
          }]
        }]
    }
  },
  methods: {
    // 获取文件列表
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    // 上传文件
    submitUpload() {
      let formData = new FormData();
      this.fileList.forEach(item => {
        formData.append("files", item.raw);
        formData.append("dataType", 1);
      });
      let selt = this;
      this.$api.version.upload(formData).then(res => {
        if (res.code === 200) {
          let attachIds = '';
          res.data.forEach(key => {
            attachIds += key.id;
          })
          selt.dataForm.attachIds += attachIds;
        }
      });
    },
    uploadSuccess(response, file, fileList) {
      var attachIds = "";
      response.resultMap.list.map((val) => {
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
          attachIds = attachIds + obj.response.resultMap.list[0].id + ','
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
    // Cascader 级联选择器选中事件
    onProvincesChange(item) {
      let modelName = ""
      item.forEach(key => {
        modelName += key[1] + ",";
      })
      this.dataForm.modelName = modelName;
    },
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.pageRequest.columnFilters = {username: '', ieml: ''}
      this.$api.version.findPage({
        'pageNum': this.pageRequest.pageNum,
        'pageSize': this.pageRequest.pageSize,
        'name': this.filters.name
      }).then((res) => {
        this.pageResult = res.data
      }).then(data != null ? data.callback : '')
    },
    // 批量删除
    handleDelete: function (data) {
      this.$api.user.batchDelete(data.params).then(data != null ? data.callback : '')
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
        attachIds: ''
      }
      this.fileList = []
    },
    // 显示编辑界面
    handleEdit: function (params) {
      this.dialogVisible = true
      this.operation = false
      this.dataForm = Object.assign({}, params.row)
    },
    // 编辑
    submitForm: function (val) {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true
            debugger
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
        {prop: "createTime", label: "创建时间", minWidth: 120, formatter: this.dateFormat}
      ]
      this.filterColumns = JSON.parse(JSON.stringify(this.columns));
    },
  },
  mounted() {
    this.initColumns()
  }
}
</script>

<style scoped>

</style>
