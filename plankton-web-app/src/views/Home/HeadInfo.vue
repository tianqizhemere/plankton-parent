<template>
  <div class="head-info" :class="center && 'center'">
    <span>{{ title }}</span>
    <p><a>{{ content }}</a></p>
  </div>
</template>

<script>
export default {
  name: 'HeadInfo',
  props: {
    title: {
      default: ''
    },
    content: {
      default: ''
    },
    bordered: {
      default: false
    },
    center: {
      default: true
    }
  }
}
</script>

<style scoped>
.head-info {
  position: relative;
  text-align: left;
  padding: 0 32px 0 0;
  min-width: 125px;
}

.head-info.center {
  text-align: center;
  padding: 0 32px;
}

.head-info span {
  color: rgba(0, 0, 0, 0.45);
  display: inline-block;
  font-size: .95rem;
  line-height: 32px;
  margin-bottom: 4px;
}

.head-info p {
  line-height: 32px;
  margin: 0;
}

.head-info p a {
  font-weight: 600;
  font-size: 1rem;
}

</style>
