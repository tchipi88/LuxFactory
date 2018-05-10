(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('compte-analytique', {
                    parent: 'entity',
                    url: '/compte-analytique?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/compte-analytique/compte-analytiques.html',
                            controller: 'CompteAnalytiqueController',
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
                        search: null,
                        type: {
                            value: 'CLIENT',
                            squash: true
                        }
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
                .state('compte-analytique-detail', {
                    parent: 'compte-analytique',
                    url: '/compte-analytique/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/compte-analytique/compte-analytique-detail.html',
                            controller: 'CompteAnalytiqueDetailController',
                            controllerAs: 'vm'}
                    },
                    resolve: {
                        entity: ['$stateParams', 'CompteAnalytique', function ($stateParams, CompteAnalytique) {
                                return CompteAnalytique.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'compte-analytique',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('compte-analytique-detail.edit', {
                    parent: 'compte-analytique-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/compte-analytique/compte-analytique-dialog.html',
                                controller: 'CompteAnalytiqueDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['CompteAnalytique', function (CompteAnalytique) {
                                            return CompteAnalytique.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('compte-analytique.new', {
                    parent: 'compte-analytique',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/compte-analytique/compte-analytique-dialog.html',
                                controller: 'CompteAnalytiqueDialogController',
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
                                $state.go('compte-analytique', null, {reload: 'compte-analytique'});
                            }, function () {
                                $state.go('compte-analytique');
                            });
                        }]
                })
                .state('compte-analytique.edit', {
                    parent: 'compte-analytique',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/compte-analytique/compte-analytique-dialog.html',
                                controller: 'CompteAnalytiqueDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['CompteAnalytique', function (CompteAnalytique) {
                                            return CompteAnalytique.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('compte-analytique', null, {reload: 'compte-analytique'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('compte-analytique.delete', {
                    parent: 'compte-analytique',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/compte-analytique/compte-analytique-delete-dialog.html',
                                controller: 'CompteAnalytiqueDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['CompteAnalytique', function (CompteAnalytique) {
                                            return CompteAnalytique.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('compte-analytique', null, {reload: 'compte-analytique'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
