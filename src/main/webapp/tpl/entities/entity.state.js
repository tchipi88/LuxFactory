(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider','JQ_CONFIG', 'MODULE_CONFIG'];

    function stateConfig($stateProvider,JQ_CONFIG, MODULE_CONFIG) {
        $stateProvider.state('entity', {
            abstract: true,
            parent: 'app',
            template: '<ui-view/>'
        });
    }
    ;


   
})();
