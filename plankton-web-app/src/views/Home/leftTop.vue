<template>
	<div class="main-left-top">
		<h3><span></span>各企业事故风险控制情况统计</h3>
		<div id="main-left-top"></div>
	</div>
</template>

<script>
	import echart from 'echarts'
	
	export default {
		data() {
			return {
				// protectedInfoTimer: null,
				dataX: [
					this.$t('global.veryLow'), 
					this.$t('global.low'), 
					this.$t('global.secondary'), 
					this.$t('global.high'), 
					this.$t('global.veryHigh')
				],
				dataLower: [550, 450, 700, 600, 350]
			}
		},

		mounted() {
			let self = this;
			self.test();
			return;
			setTimeout(function() {
				let param = { "method": "" };
				self.socketApi.sendSock(param, self.dataCb)
			}, 500)
		},

		destroyed() {
			// clearInterval(this.protectedInfoTimer)
		},

		methods: {
			test() {
				let self = this;
				self.renderProInfo();
				// self.protectedInfoTimer = setInterval(function() {
				// 	self.dataLower = [
				// 		self.dataLower[0] + 1, 
				// 		self.dataLower[1] + 5, 
				// 		self.dataLower[2] + 8, 
				// 		self.dataLower[3] + 6,
				// 		self.dataLower[4] + 3, 
				// 		self.dataLower[5] + 6, 
				// 		self.dataLower[6] + 5, 
				// 		self.dataLower[7] + 8, 
				// 		self.dataLower[8] + 6, 
				// 		self.dataLower[9] + 5
				// 	];
				// 	self.renderProInfo()
				// }, 5000)
			},

			dataCb(dataObj) {
				if (null == dataObj) {
					return
				}
				let method = dataObj.method;
				if (method != '') {
					return
				}
				if ("fail" == dataObj.result) {
					return
				}
				let resultObj = dataObj.rspdata;
				return;
				this.dataX = ["机密性", "完整性", "可用性"];
				this.dataLower = [resultObj.confidence_lower, resultObj.integrity_lower, resultObj.abailability_lower];
				this.renderProInfo()
			},

			renderProInfo() {
				var mychart = this.$echarts.init(document.getElementById('main-left-top'));
				let self = this;
				mychart.setOption({
					tooltip: {},
					legend: {
						show: false,
						orient: 'horizontal',
						x: 'center',
						data: [{
							name: '低危',
							textStyle: {
								fontFamily: '微软雅黑, Arial',
								color: '#fff'
							}
						}, {
							name: '中危',
							textStyle: {
								fontFamily: '微软雅黑, Arial',
								color: '#fff'
							}
						}, {
							name: '高危',
							textStyle: {
								fontFamily: '微软雅黑, Arial',
								color: '#fff'
							}
						}]
					},
					grid: {
						top: '35',
						right: '95',
						bottom: '25',
						left: '45',
						containLabel: false
					},
					xAxis: {
						name: this.$t('global.level'),
						nameTextStyle: {
							fontFamily: '微软雅黑, Arial'
						},
						splitLine: {
							show: true,
							lineStyle: {
								color: 'rgba(204,204,204,.2)',
								width: 1,
								type: 'dotted'
							}
						},
						axisLabel: {
							textStyle: {
								fontFamily: '微软雅黑, Arial',
								color: '#fff',
								fontSize: 10
							}
						},
						axisLine: {
							lineStyle: {
								color: 'rgba(0,255,255,.5)'
							}
						},
						data: self.dataX
					},
					yAxis: {
						name: '数量',
						nameTextStyle: {
							fontFamily: '微软雅黑, Arial'
						},
						axisLabel: {
							show: true,
							textStyle: {
								fontFamily: '微软雅黑, Arial',
								color: '#fff',
								fontSize: 10
							}
						},
						splitLine: {
							show: true,
							lineStyle: {
								color: 'rgba(204,204,204,0.2)',
								width: 1,
								type: 'dotted'
							}
						},
						axisLine: {
							lineStyle: {
								color: 'rgba(0,255,255,.5)',
								width: 1,
								type: 'solid'
							}
						}
					},
					color: ['#2ec7c9', '#5ab1ef', '#58ddef', '#ffb980', '#d87a80'],
					series: [{
						name: '分',
						smooth: true,
						barMaxWidth: 15,
						type: 'bar',
						data: self.dataLower,
						itemStyle: {
							normal: {
								label: {
									show: true,
									position: 'top',
									color: '#2ec7c9'
								},
								color: function(params) {
									var colorList = ['#2ec7c9', '#5ab1ef', '#58ddef', '#ffb980', '#d87a80'];
									return colorList[params.dataIndex]
								}
							},
							emphasis: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}]
				})
			}
		}
	}
</script>