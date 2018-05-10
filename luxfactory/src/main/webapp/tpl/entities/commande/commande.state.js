(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('commande', {
                    parent: 'entity',
                    url: '/commande?page&sort&search&type',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/commande/commandes.html',
                            controller: 'CommandeController',
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
                            value: 'ACHAT',
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

                .state('commande.new', {
                    parent: 'commande',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/commande/commande-dialog.html',
                                controller: 'CommandeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            etat: 'DEVIS',
                                            type: $stateParams.type,
                                            dateEmission: new Date()
                                        };
                                    },
                                    commandeLignes: function () {
                                        return [];
                                    },
                                    reglements: function () {
                                        return [];
                                    }
                                }
                            }).result.then(function (result) {
                                $state.go('commande.edit', {id: result.id}, null);
                            }, function () {
                                $state.go('commande');
                            });
                        }]
                })
                .state('commande.edit', {
                    parent: 'commande',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/commande/commande-dialog.html',
                                controller: 'CommandeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Commande', function (Commande) {
                                            return Commande.get({id: $stateParams.id}).$promise;
                                        }],
                                    commandeLignes: ['$resource', function ($resource) {
                                            return  $resource('api/commande-ligness/' + $stateParams.id).query();
                                        }],
                                    reglements: ['$resource', function ($resource) {
                                            return  $resource('api/reglementss/' + $stateParams.id).query();
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('commande', null, {reload: 'commande'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('commande.delete', {
                    parent: 'commande',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/commande/commande-delete-dialog.html',
                                controller: 'CommandeDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Commande', function (Commande) {
                                            return Commande.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('commande', null, {reload: 'commande'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
