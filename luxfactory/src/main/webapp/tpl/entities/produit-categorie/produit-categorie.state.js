(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('produit-categorie', {
                parent: 'entity',
                        url: '/produit-categorie?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/produit-categorie/produit-categories.html',
                                controller: 'ProduitCategorieController',
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
               
               
                .state('produit-categorie.new', {
                parent: 'produit-categorie',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/produit-categorie/produit-categorie-dialog.html',
                                controller: 'ProduitCategorieDialogController',
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
                        $state.go('produit-categorie', null, {reload: 'produit-categorie'});
                        }, function () {
                        $state.go('produit-categorie');
                        });
                        }]
                })
                .state('produit-categorie.edit', {
                parent: 'produit-categorie',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/produit-categorie/produit-categorie-dialog.html',
                                controller: 'ProduitCategorieDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['ProduitCategorie', function (ProduitCategorie) {
                                return ProduitCategorie.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('produit-categorie', null, {reload: 'produit-categorie'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('produit-categorie.delete', {
                parent: 'produit-categorie',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/produit-categorie/produit-categorie-delete-dialog.html',
                                controller: 'ProduitCategorieDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['ProduitCategorie', function (ProduitCategorie) {
                                return ProduitCategorie.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('produit-categorie', null, {reload: 'produit-categorie'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
