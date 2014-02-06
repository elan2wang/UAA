/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-21
 * 
 * this script is used by log_board.html
 */ 
var getUniqueAddrCount = "/1/logs/addrs";
var getRequestCount = "/1/logs/requests";

$(function () {
	$(document).ready(function() {
		Highcharts.setOptions({
			global: {
				useUTC: false
			}
		});

		$('#api_count').highcharts({
			chart: {
				type: 'spline',
				animation: Highcharts.svg, // don't animate in old IE
				marginRight: 10,
				events: {
					load: function() {
						var series = this.series[0];
						var shiftCount = 20,
							alreadyCount = series.data.length;
						
						var loadData = function() {
							$.ajax({
								url: getRequestCount,
								type: "get",
								data: {"interval": 5},
								dataType: "JSON",
								success: function(result) {
									var x = (new Date()).getTime(), // current time
									y = result.data.count;
									series.addPoint([x, y], true, (++alreadyCount > shiftCount));
								}
							});
						};

						loadData();
						
						// set up the updating of the chart each 5 seconds
						setInterval(loadData, 5*1000);
					}
				}
			},
			title: {
				text: '所有API请求数/5秒内'
			},
			xAxis: {
				type: 'datetime',
				dateTimeLabelFormats: { month: '%Y-%m-%d' },
				tickPixelInterval: 100
			},
			yAxis: {
				title: {
					text: '次数'
				},
				plotLines: [{
					value: 0,
					width: 1,
					color: '#808080'
				}]
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+
					Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
					Highcharts.numberFormat(this.y, 0);
				}
			},
			legend: {
				enabled: false
			},
			exporting: {
				enabled: false
			},
			series: [{
				name: 'Request Count',
				data: []
			}]
		});

		$('#user_count').highcharts({
			chart: {
				type: 'spline',
				animation: Highcharts.svg, // don't animate in old IE
				marginRight: 10,
				events: {
					load: function() {
						var series = this.series[0];
						var shiftCount = 20,
							alreadyCount = series.data.length;
						
						var loadData = function() {
							$.ajax({
								url: getUniqueAddrCount,
								type: "get",
								data: {"interval": 5},
								dataType: "JSON",
								success: function(result) {
									var x = (new Date()).getTime(), // current time
									y = result.data.count;
									series.addPoint([x, y], true, (++alreadyCount > shiftCount));
								}
							});
						};

						loadData();
						
						// set up the updating of the chart each 5 seconds
						setInterval(loadData, 5*1000);
					}
				}
			},
			title: {
				text: '独立IP访问量/5秒内'
			},
			xAxis: {
				type: 'datetime',
				tickPixelInterval: 100
			},
			yAxis: {
				title: {
					text: '个数'
				},
				plotLines: [{
					value: 0,
					width: 1,
					color: '#808080'
				}]
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+
					Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
					Highcharts.numberFormat(this.y, 0);
				}
			},
			legend: {
				enabled: false
			},
			exporting: {
				enabled: false
			},
			series: [{
				name: 'Unique IP Count',
				data: []
			}]
		});
	});

});