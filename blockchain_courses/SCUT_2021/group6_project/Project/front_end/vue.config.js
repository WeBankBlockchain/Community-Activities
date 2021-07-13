const path = require('path')
function resolve(dir) {
	return path.join(__dirname, dir)
}

module.exports = {
	publicPath: './',
	//打包文件输出路径，即打包到哪里
	outputDir: 'dist',
	// 静态资源地址
	assetsDir: 'static',
	// eslint-loader 是否在保存的时候检查
	lintOnSave: false,
	// 生产环境是否生成 sourceMap 文件
	productionSourceMap: false,
	filenameHashing: true, //文件hash

	// chainWebpack 这个库提供了一个 webpack 原始配置的上层抽象，使其可以定义具名的 loader 规则
	// 和具名插件，可以通过其提供的一些方法链式调用，在cli-service中就使用了这个插件
	chainWebpack: (config) => {
		config.resolve.alias.set('@asset', resolve('src/assets')),
			config.resolve.alias.set('@components', resolve('src/components'))
	},

	/* 
     configureWebpack是调整webpack配置最简单的一种方式，可以新增也可以覆盖cli中的配置。
     可以是一个对象：被 webpack-merge 合并到webpack 的设置中去
     也可以是一个函数：如果你需要基于环境有条件地配置行为，就可以进行一些逻辑处理，可以直接修改或
 新增配置，(该函数会在环境变量被设置之后懒执行)。该方法的第一个参数会收到已经解析好的配置。
     在函数内，你可以直接修改配置，或者返回一个将会被合并的对象。
   */
	configureWebpack: {
		externals: {
			BMap: 'BMap',
		},
	},

    devServer: {
        port: 8080, // 端口

    },
}
