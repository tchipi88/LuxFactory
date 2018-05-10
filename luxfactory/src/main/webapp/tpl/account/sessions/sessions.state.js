(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('sessions', {
            parent: 'account',
            url: '/sessions',
            data: {
                authorities: ['ROLE_USER'],
            },
            views: {
                'content@app': {
                    templateUrl: 'tpl/account/sessions/sessions.html',
                    controller: 'SessionsController',
                    controllerAs: 'vm'}}

        });
    }
})();
