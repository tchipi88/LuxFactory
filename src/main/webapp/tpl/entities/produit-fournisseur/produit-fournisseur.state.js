(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('produit-fournisseur', {
                    parent: 'entity',
                    url: '/produit-fournisseur?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseurs.html',
                            controller: 'ProduitFournisseurController',
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
                .state('produit-fournisseur-detail', {
                    parent: 'produit-fournisseur',
                    url: '/produit-fournisseur/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-detail.html',
                            controller: 'ProduitFournisseurDetailController',
                            controllerAs: 'vm'}
                    },
                    resolve: {
                        entity: ['$stateParams', 'ProduitFournisseur', function ($stateParams, ProduitFournisseur) {
                                return ProduitFournisseur.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'produit-fournisseur',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('produit-fournisseur-detail.edit', {
                    parent: 'produit-fournisseur-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-dialog.html',
                                controller: 'ProduitFournisseurDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['ProduitFournisseur', function (ProduitFournisseur) {
                                            return ProduitFournisseur.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('produit-fournisseur.new', {
                    parent: 'produit-fournisseur',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-dialog.html',
                                controller: 'ProduitFournisseurDialogController',
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
                                $state.go('produit-fournisseur', null, {reload: 'produit-fournisseur'});
                            }, function () {
                                $state.go('produit-fournisseur');
                            });
                        }]
                })
                .state('produit-fournisseur.edit', {
                    parent: 'produit-fournisseur',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-dialog.html',
                                controller: 'ProduitFournisseurDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['ProduitFournisseur', function (ProduitFournisseur) {
                                            return ProduitFournisseur.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('produit-fournisseur', null, {reload: 'produit-fournisseur'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('produit-fournisseur.delete', {
                    parent: 'produit-fournisseur',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/produit-fournisseur/produit-fournisseur-delete-dialog.html',
                                controller: 'ProduitFournisseurDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['ProduitFournisseur', function (ProduitFournisseur) {
                                            return ProduitFournisseur.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('produit-fournisseur', null, {reload: 'produit-fournisseur'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
