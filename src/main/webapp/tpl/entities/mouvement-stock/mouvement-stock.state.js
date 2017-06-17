(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('mouvement-stock', {
                parent: 'entity',
                        url: '/mouvement-stock?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/mouvement-stock/mouvement-stocks.html',
                                controller: 'MouvementStockController',
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
                .state('mouvement-stock-detail', {
                parent: 'mouvement-stock',
                        url: '/mouvement-stock/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/mouvement-stock/mouvement-stock-detail.html',
                                controller: 'MouvementStockDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'MouvementStock', function ($stateParams, MouvementStock) {
                        return MouvementStock.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'mouvement-stock',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('mouvement-stock-detail.edit', {
                parent: 'mouvement-stock-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock/mouvement-stock-dialog.html',
                                controller: 'MouvementStockDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['MouvementStock', function (MouvementStock) {
                                return MouvementStock.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('mouvement-stock.new', {
                parent: 'mouvement-stock',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock/mouvement-stock-dialog.html',
                                controller: 'MouvementStockDialogController',
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
                        $state.go('mouvement-stock', null, {reload: 'mouvement-stock'});
                        }, function () {
                        $state.go('mouvement-stock');
                        });
                        }]
                })
                .state('mouvement-stock.edit', {
                parent: 'mouvement-stock',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock/mouvement-stock-dialog.html',
                                controller: 'MouvementStockDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['MouvementStock', function (MouvementStock) {
                                return MouvementStock.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('mouvement-stock', null, {reload: 'mouvement-stock'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('mouvement-stock.delete', {
                parent: 'mouvement-stock',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/mouvement-stock/mouvement-stock-delete-dialog.html',
                                controller: 'MouvementStockDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['MouvementStock', function (MouvementStock) {
                                return MouvementStock.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('mouvement-stock', null, {reload: 'mouvement-stock'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
