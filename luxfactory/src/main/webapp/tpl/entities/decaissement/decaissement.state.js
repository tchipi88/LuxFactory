(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('decaissement', {
                    parent: 'entity',
                    url: '/decaissement?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/decaissement/decaissements.html',
                            controller: 'DecaissementController',
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
                .state('decaissement-detail', {
                    parent: 'decaissement',
                    url: '/decaissement/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/decaissement/decaissement-detail.html',
                            controller: 'DecaissementDetailController',
                            controllerAs: 'vm'}
                    },
                    resolve: {
                        entity: ['$stateParams', 'Decaissement', function ($stateParams, Decaissement) {
                                return Decaissement.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'decaissement',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('decaissement-detail.edit', {
                    parent: 'decaissement-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/decaissement/decaissement-dialog.html',
                                controller: 'DecaissementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Decaissement', function (Decaissement) {
                                            return Decaissement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('decaissement.new', {
                    parent: 'decaissement',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/decaissement/decaissement-dialog.html',
                                controller: 'DecaissementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            dateVersement: new Date(),
                                            modePaiement: 'ESPECES'
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('decaissement', null, {reload: 'decaissement'});
                            }, function () {
                                $state.go('decaissement');
                            });
                        }]
                })
                .state('decaissement.edit', {
                    parent: 'decaissement',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/decaissement/decaissement-dialog.html',
                                controller: 'DecaissementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Decaissement', function (Decaissement) {
                                            return Decaissement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('decaissement', null, {reload: 'decaissement'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('decaissement.delete', {
                    parent: 'decaissement',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/decaissement/decaissement-delete-dialog.html',
                                controller: 'DecaissementDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Decaissement', function (Decaissement) {
                                            return Decaissement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('decaissement', null, {reload: 'decaissement'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
