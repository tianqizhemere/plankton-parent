<template>
	<div class="main-right-top">
		<h3><span></span>各单位一年内违章统计</h3>
		<div id="main-right-top"></div>
	</div>
</template>

<script>
	import echart from 'echarts'
	
	export default {
		data() {
			return {
				dataArr: [{
					name: this.$t('global.veryLow'),
					value: 18
				}, {
					name: this.$t('global.low'),
					value: 16
				}, {
					name: this.$t('global.secondary'),
					value: 17
				}, {
					name: this.$t('global.high'),
					value: 19
				}, {
					name: this.$t('global.veryHigh'),
					value: 15
				}]
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
			clearInterval(this.protectedInfoTimer)
		},

		methods: {
			test() {
				let self = this;
				self.renderProInfo()
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
				this.dataUpY = [resultObj.confidence_up, resultObj.integrity_up, resultObj.abailability_up];
				this.dataDownY = [resultObj.confidence_down, resultObj.integrity_down, resultObj.abailability_down];
				this.renderProInfo()
			},

			renderProInfo() {
				var mychart = this.$echarts.init(document.getElementById('main-right-top'), 'macarons');
				mychart.currentIndex = -1;
				let self = this;
				let pieOption = {
					tooltip: {},
					color: ['#2ec7c9', '#5ab1ef', '#58ddef', '#ffb980', '#d87a80'],
					calculable: false,
					legend: {
						show: true,
						orient: 'vertical',
						padding: 20,
						x: 'left',
						data: [{
								name: this.$t('global.veryHigh'),
								textStyle: {
									fontFamily: '微软雅黑, Arial',
									color: '#fff'
								}
							},
							{
								name: this.$t('global.high'),
								textStyle: {
									fontFamily: '微软雅黑, Arial',
									color: '#fff'
								}
							},
							{
								name: this.$t('global.secondary'),
								textStyle: {
									fontFamily: '微软雅黑, Arial',
									color: '#fff'
								}
							},
							{
								name: this.$t('global.low'),
								textStyle: {
									fontFamily: '微软雅黑, Arial',
									color: '#fff'
								}
							},
							{
								name: this.$t('global.veryLow'),
								textStyle: {
									fontFamily: '微软雅黑, Arial',
									color: '#fff'
								}
							}
						]
					},
					series: [{
						type: 'pie',
						radius: [55, 80],
						center: ['55%', '50%'],
						roseType: 'radius',
						data: this.dataArr,
						itemStyle: {
							normal: {
								label: {
									show: true,
									textStyle: {
										fontSize: 14,
										fontFamily: '微软雅黑, Arial'
									}
								},
								labelLine: {
									show: true,
									length: 8
								}
							}
						}
					}]
				};
				mychart.setOption(pieOption);
				setInterval(function() {
					var dataLen = pieOption.series[0].data.length;
					mychart.dispatchAction({
						type: 'downplay',
						seriesIndex: 0,
						dataIndex: mychart.currentIndex
					});
					mychart.currentIndex = (mychart.currentIndex + 1) % dataLen;
					mychart.dispatchAction({
						type: 'highlight',
						seriesIndex: 0,
						dataIndex: mychart.currentIndex
					})
				}, 5000)
			}
		}
	}
</script>