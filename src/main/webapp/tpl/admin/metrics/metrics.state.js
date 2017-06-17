(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('jhi-metrics', {
            parent: 'app',
            url: '/metrics',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'metrics.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'tpl/admin/metrics/metrics.html',
                    controller: 'JhiMetricsMonitoringController',
                    controllerAs: 'vm'}}

        });
    }
})();
