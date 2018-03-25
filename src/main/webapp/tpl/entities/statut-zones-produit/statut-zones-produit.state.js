(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('statut-zones-produit', {
                parent: 'entity',
                        url: '/statut-zones-produit?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/statut-zones-produit/statut-zones-produits.html',
                                controller: 'StatutZonesProduitController',
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
               
               
                .state('statut-zones-produit.new', {
                parent: 'statut-zones-produit',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/statut-zones-produit/statut-zones-produit-dialog.html',
                                controller: 'StatutZonesProduitDialogController',
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
                        $state.go('statut-zones-produit', null, {reload: 'statut-zones-produit'});
                        }, function () {
                        $state.go('statut-zones-produit');
                        });
                        }]
                })
                .state('statut-zones-produit.edit', {
                parent: 'statut-zones-produit',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/statut-zones-produit/statut-zones-produit-dialog.html',
                                controller: 'StatutZonesProduitDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['StatutZonesProduit', function (StatutZonesProduit) {
                                return StatutZonesProduit.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('statut-zones-produit', null, {reload: 'statut-zones-produit'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('statut-zones-produit.delete', {
                parent: 'statut-zones-produit',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/statut-zones-produit/statut-zones-produit-delete-dialog.html',
                                controller: 'StatutZonesProduitDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['StatutZonesProduit', function (StatutZonesProduit) {
                                return StatutZonesProduit.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('statut-zones-produit', null, {reload: 'statut-zones-produit'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
