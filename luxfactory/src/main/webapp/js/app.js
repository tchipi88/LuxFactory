'use strict';


angular.module('app', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngSanitize',
     'ngCacheBuster',
    'ngTouch',
    'ngStorage',
    'ui.router',
    'ui.bootstrap',
    'ui.bootstrap.datetimepicker',
    'ui.utils',
    'ui.load',
    'ui.jq',
    'oc.lazyLoad',
    'ngFileUpload',
    'datatables']).run(run);

 run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
