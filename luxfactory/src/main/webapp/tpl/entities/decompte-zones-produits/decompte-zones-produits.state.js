(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('decompte-zones-produits', {
                parent: 'entity',
                        url: '/decompte-zones-produits?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/decompte-zones-produits/decompte-zones-produitss.html',
                                controller: 'DecompteZonesProduitsController',
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
               
               
                .state('decompte-zones-produits.new', {
                parent: 'decompte-zones-produits',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/decompte-zones-produits/decompte-zones-produits-dialog.html',
                                controller: 'DecompteZonesProduitsDialogController',
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
                        $state.go('decompte-zones-produits', null, {reload: 'decompte-zones-produits'});
                        }, function () {
                        $state.go('decompte-zones-produits');
                        });
                        }]
                })
                .state('decompte-zones-produits.edit', {
                parent: 'decompte-zones-produits',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/decompte-zones-produits/decompte-zones-produits-dialog.html',
                                controller: 'DecompteZonesProduitsDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['DecompteZonesProduits', function (DecompteZonesProduits) {
                                return DecompteZonesProduits.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('decompte-zones-produits', null, {reload: 'decompte-zones-produits'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('decompte-zones-produits.delete', {
                parent: 'decompte-zones-produits',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/decompte-zones-produits/decompte-zones-produits-delete-dialog.html',
                                controller: 'DecompteZonesProduitsDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['DecompteZonesProduits', function (DecompteZonesProduits) {
                                return DecompteZonesProduits.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('decompte-zones-produits', null, {reload: 'decompte-zones-produits'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
