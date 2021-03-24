<template>
  <el-table :bordered="true" :dataSource="projects" :rowKey="record => record.id" :showHeader="false"
            :pagination="{defaultPageSize:4,hideOnSinglePage:true}">
    <el-table-column dataIndex="id" key="id">
      <template slot-scope="text, record">
        <div class="project-avatar-wrapper">
          <el-avatar class="project-avatar">{{ record.avatar }}</el-avatar>
        </div>
        <div class="project-detail">
          <div class="project-name">
            {{ record.name }}
          </div>
          <div class="project-desc">
            <p>{{ record.description }}</p>
          </div>
        </div>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: 'RunningTask',
  props: ['projects']
}
</script>

<style scoped>

</style>
