(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('entrepot', {
                    parent: 'entity',
                    url: '/entrepot?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/entrepot/entrepots.html',
                            controller: 'EntrepotController',
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
                .state('entrepot-detail', {
                    parent: 'entrepot',
                    url: '/entrepot/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/entrepot/entrepot-detail.html',
                            controller: 'EntrepotDetailController',
                            controllerAs: 'vm'}
                    },
                    resolve: {
                        entity: ['$stateParams', 'Entrepot', function ($stateParams, Entrepot) {
                                return Entrepot.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'entrepot',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('entrepot-detail.edit', {
                    parent: 'entrepot-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot/entrepot-dialog.html',
                                controller: 'EntrepotDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Entrepot', function (Entrepot) {
                                            return Entrepot.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('entrepot.new', {
                    parent: 'entrepot',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot/entrepot-dialog.html',
                                controller: 'EntrepotDialogController',
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
                                $state.go('entrepot', null, {reload: 'entrepot'});
                            }, function () {
                                $state.go('entrepot');
                            });
                        }]
                })
                .state('entrepot.edit', {
                    parent: 'entrepot',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot/entrepot-dialog.html',
                                controller: 'EntrepotDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Entrepot', function (Entrepot) {
                                            return Entrepot.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('entrepot', null, {reload: 'entrepot'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('entrepot.delete', {
                    parent: 'entrepot',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot/entrepot-delete-dialog.html',
                                controller: 'EntrepotDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Entrepot', function (Entrepot) {
                                            return Entrepot.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('entrepot', null, {reload: 'entrepot'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
