(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('logs', {
            parent: 'admin',
            url: '/logs',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'logs.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'tpl/admin/logs/logs.html',
                    controller: 'LogsController',
                    controllerAs: 'vm'}}

        });
    }
})();
