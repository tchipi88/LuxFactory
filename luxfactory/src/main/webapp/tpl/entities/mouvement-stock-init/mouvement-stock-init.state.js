(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('mouvement-stock-init', {
                parent: 'entity',
                        url: '/mouvement-stock-init?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/mouvement-stock-init/mouvement-stock-inits.html',
                                controller: 'MouvementStockInitController',
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
               
               
                .state('mouvement-stock-init.new', {
                parent: 'mouvement-stock-init',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock-init/mouvement-stock-init-dialog.html',
                                controller: 'MouvementStockInitDialogController',
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
                        $state.go('mouvement-stock-init', null, {reload: 'mouvement-stock-init'});
                        }, function () {
                        $state.go('mouvement-stock-init');
                        });
                        }]
                })
                .state('mouvement-stock-init.edit', {
                parent: 'mouvement-stock-init',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock-init/mouvement-stock-init-dialog.html',
                                controller: 'MouvementStockInitDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['MouvementStockInit', function (MouvementStockInit) {
                                return MouvementStockInit.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('mouvement-stock-init', null, {reload: 'mouvement-stock-init'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('mouvement-stock-init.delete', {
                parent: 'mouvement-stock-init',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock-init/mouvement-stock-init-delete-dialog.html',
                                controller: 'MouvementStockInitDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['MouvementStockInit', function (MouvementStockInit) {
                                return MouvementStockInit.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('mouvement-stock-init', null, {reload: 'mouvement-stock-init'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
