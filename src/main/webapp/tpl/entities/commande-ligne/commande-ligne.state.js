(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('commande-ligne', {
                parent: 'entity',
                        url: '/commande-ligne?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/commande-ligne/commande-lignes.html',
                                controller: 'CommandeLigneController',
                                controllerAs: 'vm'  }
                        },
                        params: {
                        page: {
                        value: '1',
                                squash: true
                        },
                                sort: {
                                value: 'id,asc',
                                        squash: true
                                },
                                search: null
                        },
                        resolve: {
                        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                        page: PaginationUtil.parsePage($stateParams.page),
                                sort: $stateParams.sort,
                                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                                ascending: PaginationUtil.parseAscending($stateParams.sort),
                                search: $stateParams.search
                        };
                        }]
                        }
                })
                .state('commande-ligne-detail', {
                parent: 'commande-ligne',
                        url: '/commande-ligne/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/commande-ligne/commande-ligne-detail.html',
                                controller: 'CommandeLigneDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'CommandeLigne', function ($stateParams, CommandeLigne) {
                        return CommandeLigne.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'commande-ligne',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('commande-ligne-detail.edit', {
                parent: 'commande-ligne-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/commande-ligne/commande-ligne-dialog.html',
                                controller: 'CommandeLigneDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CommandeLigne', function (CommandeLigne) {
                                return CommandeLigne.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('commande-ligne.new', {
                parent: 'commande-ligne',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/commande-ligne/commande-ligne-dialog.html',
                                controller: 'CommandeLigneDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: function () {
                                return {

                                };
                                }
                                }
                        }).result.then(function () {
                        $state.go('commande-ligne', null, {reload: 'commande-ligne'});
                        }, function () {
                        $state.go('commande-ligne');
                        });
                        }]
                })
                .state('commande-ligne.edit', {
                parent: 'commande-ligne',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/commande-ligne/commande-ligne-dialog.html',
                                controller: 'CommandeLigneDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CommandeLigne', function (CommandeLigne) {
                                return CommandeLigne.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('commande-ligne', null, {reload: 'commande-ligne'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('commande-ligne.delete', {
                parent: 'commande-ligne',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/commande-ligne/commande-ligne-delete-dialog.html',
                                controller: 'CommandeLigneDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CommandeLigne', function (CommandeLigne) {
                                return CommandeLigne.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('commande-ligne', null, {reload: 'commande-ligne'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
