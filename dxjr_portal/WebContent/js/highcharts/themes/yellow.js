/**
 * Skies theme for Highcharts JS
 * @author Torstein Hønsi
 */

Highcharts.theme = {
	colors: ["#f5af3f", "#42A07B", "#9B5E4A", "#72727F", "#1F949A", "#82914E", "#86777F", "#42A07B"],
	chart: {
		className: 'skies',
		borderWidth: 1,
		plotShadow: false,
		//plotBackgroundImage: 'http://www.highcharts.com/demo/gfx/skies.jpg',
		plotBackgroundColor: {
			linearGradient: [255, 255,255, 255, 255],
			stops: [
				[0, 'rgba(255, 255, 255, 255)'],
				[1, 'rgba(255, 255, 255, 255)']
			]
		},
		plotBorderWidth: 3
	},
	title: {
		style: {
			color: '#3E576F',
			font: '16px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
		}
	},
	subtitle: {
		style: {
			color: '#6D869F',
			font: '12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
		}
	},
	xAxis: {
		gridLineWidth: 0,
		labels: {
			style: {
				color: '#000306'
			}
		},
		title: {
			style: {
				color: '#000000',
				font: '12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
			}
		}
	},
	yAxis: {
		
		
		tickWidth: 2,
		labels: {
			style: {
				color: '#000306'
				
			}
		},
		title: {
			style: {
				color: '#000306',
				font: '12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
			}
		}
	}
};

// Apply the theme
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
