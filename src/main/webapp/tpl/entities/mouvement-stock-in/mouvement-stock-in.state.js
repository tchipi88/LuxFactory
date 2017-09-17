(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('mouvement-stock-in', {
                    parent: 'entity',
                    url: '/mouvement-stock-in?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/mouvement-stock-in/mouvement-stock-ins.html',
                            controller: 'MouvementStockInController',
                            controllerAs: 'vm'}
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


                .state('mouvement-stock-in.new', {
                    parent: 'mouvement-stock-in',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/mouvement-stock-in/mouvement-stock-in-dialog.html',
                                controller: 'MouvementStockInDialogController',
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
                                $state.go('mouvement-stock-in', null, {reload: 'mouvement-stock-in'});
                            }, function () {
                                $state.go('mouvement-stock-in');
                            });
                        }]
                })
                .state('mouvement-stock-in.edit', {
                    parent: 'mouvement-stock-in',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/mouvement-stock-in/mouvement-stock-in-dialog.html',
                                controller: 'MouvementStockInDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['MouvementStockIn', function (MouvementStockIn) {
                                            return MouvementStockIn.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('mouvement-stock-in', null, {reload: 'mouvement-stock-in'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('mouvement-stock-in.delete', {
                    parent: 'mouvement-stock-in',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/mouvement-stock-in/mouvement-stock-in-delete-dialog.html',
                                controller: 'MouvementStockInDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['MouvementStockIn', function (MouvementStockIn) {
                                            return MouvementStockIn.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('mouvement-stock-in', null, {reload: 'mouvement-stock-in'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
