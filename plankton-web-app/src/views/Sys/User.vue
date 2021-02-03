<template>
  <div class="page-container">
    <!--工具栏-->
    <div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
      <el-form :inline="true" :model="filters" :size="size">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="code"></el-input>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-search" :label="$t('action.search')" perms="system:user:view" type="primary"
                     @click="findPage(null)"/>
        </el-form-item>
        <el-form-item>
          <kt-button icon="fa fa-plus" :label="$t('action.add')" perms="system:user:save" type="primary"
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
    <kt-table permsEdit="system:user:update" permsDelete="system:user:delete"
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
        <el-form-item label="设备ID" prop="code">
          <el-input v-model="dataForm.code" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="设备型号" prop="model">
          <popup-tree-input
              :data="popupTreeData" :props="popupTreeProps"
              :prop="dataForm.model==null?'顶级菜单':dataForm.model"
              :nodeKey="''+dataForm.parentId" :currentChangeHandle="handleTreeSelectChange">
          </popup-tree-input>
        </el-form-item>
        <el-form-item label="用户来源" prop="source">
          <el-input v-model="dataForm.source" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="dataForm.phone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="QQ" prop="qq">
          <el-input v-model="dataForm.qq" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" prop="userMode">
          <el-select v-model="dataForm.userMode" placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in userModes" :key="item.id"
                       :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="启用状态" prop="isEnable">
          <el-select v-model="dataForm.isEnable" placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in enableStatus" :key="item.id"
                       :label="item.name" :value="item.id">
            </el-option>
          </el-select>
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
        code: [
          {required: true, message: '请输入code', trigger: 'blur'}
        ],
        model: [
          {required: true, message: '请选择设备型号', trigger: 'blur'}
        ],
      },
      // 新增编辑界面数据
      dataForm: {
        id: 0,
        code: '',
        model: '',
        source: '1',
        userMode: '',
        isEnable: true,
        modelName: '',
        qq:'',
        phone:'',
        parentId:0
      },
      roles: [],
      // 用户状态
      userModes: [{id: 'normal', name: 'normal'}, {id: 'powerful', name: 'powerful'}],
      // 用户启用状态
      enableStatus: [{id: true, name: '启用'}, {id: false, name: '停用'}],
      // 机型树形数据
      tableTreeDdata: [],
      popupTreeData: [],
      popupTreeProps: {
        label: 'name',
        children: 'children'
      }
    }
  },
  methods: {
    // 获取型号树形数据
    findTreeData: function () {
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
      this.dataForm.parentId = data.id
      this.dataForm.model = data.name
    },
    // 获取分页数据
    findPage: function (data) {
      if (data !== null) {
        this.pageRequest = data.pageRequest
      }
      this.pageRequest.columnFilters = {username: '', ieml: ''}
      this.$api.user.findPage({
        'pageNum': this.pageRequest.pageNum,
        'pageSize': this.pageRequest.pageSize,
        'code': this.filters.name
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
        code: '',
        model: '',
        isEnable: true,
        source: '',
        qq:'',
        phone:'',
        parentId:0
      }
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
            if (this.dataForm.modelName) {
              this.dataForm.model = this.dataForm.modelName;
            }
            let params = Object.assign({}, this.dataForm)
            params.enable = params.isEnable;
            if (val === 'save') {
              this.$api.user.save(params).then((res) => {
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
              this.$api.user.edit(params).then((res) => {
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
        {prop: "code", label: "CODE", minWidth: 120},
        {prop: "model", label: "设备型号", minWidth: 120},
        {prop: "userMode", label: "用户状态", minWidth: 100},
        {prop: "enableStatus", label: "状态", minWidth: 70},
        {prop: "source", label: "用户来源", minWidth: 70},
        {prop: "phone", label: "手机号码", minWidth: 70},
        {prop: "qq", label: "QQ", minWidth: 70},
        {prop: "createTime", label: "创建时间", minWidth: 120, formatter: this.dateFormat}
      ]
      this.filterColumns = JSON.parse(JSON.stringify(this.columns));
    }
  },
  mounted() {
    this.initColumns();
    this.findTreeData()
  }
}
</script>

<style scoped>

</style>
