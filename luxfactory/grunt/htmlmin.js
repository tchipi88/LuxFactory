module.exports = {
	min: {
      files: [{
          expand: true,
          cwd: 'src/main/webapp/css/less/tpl/',
          src: ['*.html', '**/*.html'],
          dest: 'angular/tpl/',
          ext: '.html',
          extDot: 'first'
      }]
  }
}