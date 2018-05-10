(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('ecriture-compte-analytique', {
                    parent: 'entity',
                    url: '/ecriture-compte-analytique?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/ecriture-compte-analytique/ecriture-compte-analytiques.html',
                            controller: 'EcritureCompteAnalytiqueController',
                            controllerAs: 'vm'}
                    },
                    params: {
                        page: {
                            value: '1',
                            squash: true
                        },
                        sort: {
                            value: 'id,desc',
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
                .state('ecriture-compte-analytique-detail', {
                    parent: 'ecriture-compte-analytique',
                    url: '/ecriture-compte-analytique/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/ecriture-compte-analytique/ecriture-compte-analytique-detail.html',
                            controller: 'EcritureCompteAnalytiqueDetailController',
                            controllerAs: 'vm'}
                    },
                    resolve: {
                        entity: ['$stateParams', 'EcritureCompteAnalytique', function ($stateParams, EcritureCompteAnalytique) {
                                return EcritureCompteAnalytique.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'ecriture-compte-analytique',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('ecriture-compte-analytique-detail.edit', {
                    parent: 'ecriture-compte-analytique-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/ecriture-compte-analytique/ecriture-compte-analytique-dialog.html',
                                controller: 'EcritureCompteAnalytiqueDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['EcritureCompteAnalytique', function (EcritureCompteAnalytique) {
                                            return EcritureCompteAnalytique.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('ecriture-compte-analytique.new', {
                    parent: 'ecriture-compte-analytique',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/ecriture-compte-analytique/ecriture-compte-analytique-dialog.html',
                                controller: 'EcritureCompteAnalytiqueDialogController',
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
                                $state.go('ecriture-compte-analytique', null, {reload: 'ecriture-compte-analytique'});
                            }, function () {
                                $state.go('ecriture-compte-analytique');
                            });
                        }]
                })
                .state('ecriture-compte-analytique.edit', {
                    parent: 'ecriture-compte-analytique',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/ecriture-compte-analytique/ecriture-compte-analytique-dialog.html',
                                controller: 'EcritureCompteAnalytiqueDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['EcritureCompteAnalytique', function (EcritureCompteAnalytique) {
                                            return EcritureCompteAnalytique.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('ecriture-compte-analytique', null, {reload: 'ecriture-compte-analytique'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('ecriture-compte-analytique.delete', {
                    parent: 'ecriture-compte-analytique',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/ecriture-compte-analytique/ecriture-compte-analytique-delete-dialog.html',
                                controller: 'EcritureCompteAnalytiqueDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['EcritureCompteAnalytique', function (EcritureCompteAnalytique) {
                                            return EcritureCompteAnalytique.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('ecriture-compte-analytique', null, {reload: 'ecriture-compte-analytique'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
