(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('audits', {
            parent: 'admin',
            url: '/audits',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'audits.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'tpl/admin/audits/audits.html',
                    controller: 'AuditsController',
                    controllerAs: 'vm'}}
        });
    }
})();
