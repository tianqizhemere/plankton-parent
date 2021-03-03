<template>
	<transition-group name="fade">
		<el-row key="home-main" class="home-main">
			<el-col :span="7" class="main-left">
				<left-top class="list"></left-top>
				<left-middle class="list"></left-middle>
				<left-bottom class="list"></left-bottom>
			</el-col>

			<el-col :span="10" class="main-center">
				<center-top class="list"></center-top>
				<center-middle class="list"></center-middle>
				<el-col :span="12">
					<center-bottom-left class="list"></center-bottom-left>
				</el-col>
				<el-col :span="12">
					<center-bottom-right class="list"></center-bottom-right>
				</el-col>
			</el-col>

			<el-col :span="7" class="main-right">
				<right-top class="list"></right-top>
				<right-middle class="list"></right-middle>
				<right-bottom class="list"></right-bottom>
			</el-col>
		</el-row>
	</transition-group>
</template>

<script>
	import leftTop from './leftTop'
	import leftMiddle from './leftMiddle'
	import leftBottom from './leftBottom'
	import centerTop from './centerTop'
	import centerMiddle from './centerMiddle'
	import centerBottomLeft from './centerBottomLeft'
	import centerBottomRight from './centerBottomRight'
	import rightTop from './rightTop'
	import rightMiddle from './rightMiddle'
	import rightBottom from './rightBottom'

	export default {
		components: {
			leftTop,
			leftMiddle,
			leftBottom,
			centerTop,
			centerMiddle,
			centerBottomLeft,
			centerBottomRight,
			rightTop,
			rightMiddle,
			rightBottom
		},

		methods: {
			initPage() {
				const loading = this.$loading({
					lock: true,
					text: 'Loading',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				})

				setTimeout(() => {
					loading.close();
				}, 500)
			}
		}
	}
</script>
