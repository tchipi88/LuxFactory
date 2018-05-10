(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('jhi-health', {
            parent: 'admin',
            url: '/health',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'health.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'tpl/admin/health/health.html',
                    controller: 'JhiHealthCheckController',
                    controllerAs: 'vm'}}

        });
    }
})();
