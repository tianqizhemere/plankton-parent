<template>
  <el-select v-bind:placeholder="$t('global.selectItem')" v-model="selectValue" filterable clearable :disabled="disabled">
    <el-option :label="item.name" v-for="item in options" :key="item.id" :value="item.id">{{ item.name }}</el-option>
  </el-select>
</template>

<script>
  import { get } from '../../util/ajax'

  export default {
    data() {
      return {
        options: [],
        selectValue: this.value
      }
    },

    props: ['url', 'value', 'idField', 'valueField', 'disabled'],

    watch: {
      // 父传子
      value(val) { this.selectValue = val },

      // 子传父
      selectValue(val, oldVal) {
        if (val != oldVal) { this.$emit('input', val) }
      }
    },

    mounted() { this.getOptions() },

    methods: {
      // 数据字典类型
      getOptions() {
        let self = this;
        this.options = [];
        get(this.url).then((res) => {
          let data = res.resultMap.list || res.resultMap.page.content;
          data.forEach((item, index, data) => {
            let option = {};
            let key = self.idField || 'code';
            let value = self.valueField || 'typeName';
            option['id'] = item[key];
            option['name'] = item[value];
            self.options.push(option)
          })
        })
      }
    }
  }
</script>