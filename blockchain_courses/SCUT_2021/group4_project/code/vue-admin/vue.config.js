module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: '7022', // 对应自己的接口
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api': '',
                },
            },
        },
    },
    publicPath: './',
}
