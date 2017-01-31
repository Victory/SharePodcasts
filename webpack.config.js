module.exports = {
    entry: __dirname + "/src/main/javascript/entry.js",
    output: {
        path: __dirname + "/src/main/resources/public/js",
        filename: "bundle.js"
    },
    module: {
        loaders: [
            {
                test: /.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
                query: {
                    presets: ['es2015']
                }
            },

        ],
    },

    devtool: "source-map"
};