const path = require('path')

module.exports = {
	outputDir: path.resolve(__dirname, "../"+"main/resources/static")
	,devServer: {
		proxy: {
			'/api': {
				target: 'http://localhost:8088'
				,ws: true
				,changeorign: true
			}
		}
//		proxy: "http://localhost:8081"
	}

}
