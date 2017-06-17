module.exports = {
	less: {
      files: {
        'src/main/webapp/css/app.css': [
          'src/main/webapp/css/less/app.less'
        ],
        'src/main/webapp/css/md.css': [
          'src/main/webapp/css/less/md.less'
        ],
        'src/main/webapp/css/app.rtl.css': [
          'src/main/webapp/css/less/app.rtl.less'
        ]
      },
      options: {
        compile: true
      }
  },
  html: {
      files: {
          'html/css/app.min.css': [
              'src/main/webapp/css/less/css/font.css',
              'src/main/webapp/css/less/css/app.css'
          ]
      },
      options: {
          compress: true
      }
  }
}
