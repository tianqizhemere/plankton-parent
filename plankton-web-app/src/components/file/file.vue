<template>
  <div>
    <el-upload action="/api/system/attach/uploadFile" :data="{ dataType: dataType }" :on-success="uploadSuccess"
               :on-preview="filePreview" :on-remove="fileRemove" :before-remove="beforeRemove" multiple :limit="3"
               :on-exceed="fileExceed" :file-list="fileList">
      <i class="el-icon-plus">上传附件</i>
      <div slot="tip" class="el-upload__tip">请上传附件，且不超过10M</div>
    </el-upload>
  </div>
</template>

<script>
import axios from '../../http/axios'

export default {
  data() {
    return {
      fileList: []
    }
  },

  props: [
    'recordId',
    'dataType',
    'callBack'
  ],

  created() {
    let self = this;
    if (this.recordId != null) {
      var param = {
        recordId: self.recordId,
        dataType: self.dataType
      }
      axios({
        url: "/api/system/upload/getFileList",
        method: 'post',
        param
      }).then((res) => {
        self.fileList = res.data.fileList
      })
    } else {
      this.fileList = []
    }
  },

  methods: {
    fileRemove(file, fileList) {
      var attachIds = "";
      fileList.map((obj) => {
        if (obj.id) {
          attachIds = attachIds + obj.id + ","
        } else {
          attachIds = attachIds + obj.response.resultMap.list[0].id + ','
        }
      })
      this.$emit("setAttachIds", attachIds)
    },

    filePreview(file) {
      window.open(this.$baseUrl + file.url)
    },

    fileExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个附件，本次选择了 ${files.length} 个附件，共选择了 ${files.length + fileList.length} 个附件`)
    },

    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
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
      this.$emit("setAttachIds", attachIds)
    }
  }
}
</script>
