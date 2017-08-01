(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('mouvement-stock-out', {
                parent: 'entity',
                        url: '/mouvement-stock-out?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/mouvement-stock-out/mouvement-stock-outs.html',
                                controller: 'MouvementStockOutController',
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
               
               
                .state('mouvement-stock-out.new', {
                parent: 'mouvement-stock-out',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock-out/mouvement-stock-out-dialog.html',
                                controller: 'MouvementStockOutDialogController',
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
                        $state.go('mouvement-stock-out', null, {reload: 'mouvement-stock-out'});
                        }, function () {
                        $state.go('mouvement-stock-out');
                        });
                        }]
                })
                .state('mouvement-stock-out.edit', {
                parent: 'mouvement-stock-out',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock-out/mouvement-stock-out-dialog.html',
                                controller: 'MouvementStockOutDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['MouvementStockOut', function (MouvementStockOut) {
                                return MouvementStockOut.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('mouvement-stock-out', null, {reload: 'mouvement-stock-out'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('mouvement-stock-out.delete', {
                parent: 'mouvement-stock-out',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock-out/mouvement-stock-out-delete-dialog.html',
                                controller: 'MouvementStockOutDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['MouvementStockOut', function (MouvementStockOut) {
                                return MouvementStockOut.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('mouvement-stock-out', null, {reload: 'mouvement-stock-out'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
