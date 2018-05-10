(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('password', {
            parent: 'account',
            url: '/password',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'global.menu.account.password'
            },
            views: {
                'content@app': {
                    templateUrl: 'tpl/account/password/password.html',
                    controller: 'PasswordController',
                    controllerAs: 'vm'}}
        });
    }
})();
