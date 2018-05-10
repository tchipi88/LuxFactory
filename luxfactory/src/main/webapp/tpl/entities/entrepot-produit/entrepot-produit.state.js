(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('entrepot-produit', {
                parent: 'entity',
                        url: '/entrepot-produit?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/entrepot-produit/entrepot-produits.html',
                                controller: 'EntrepotProduitController',
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
                .state('entrepot-produit-detail', {
                parent: 'entrepot-produit',
                        url: '/entrepot-produit/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/entrepot-produit/entrepot-produit-detail.html',
                                controller: 'EntrepotProduitDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'EntrepotProduit', function ($stateParams, EntrepotProduit) {
                        return EntrepotProduit.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'entrepot-produit',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('entrepot-produit-detail.edit', {
                parent: 'entrepot-produit-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/entrepot-produit/entrepot-produit-dialog.html',
                                controller: 'EntrepotProduitDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['EntrepotProduit', function (EntrepotProduit) {
                                return EntrepotProduit.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('entrepot-produit.new', {
                parent: 'entrepot-produit',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/entrepot-produit/entrepot-produit-dialog.html',
                                controller: 'EntrepotProduitDialogController',
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
                        $state.go('entrepot-produit', null, {reload: 'entrepot-produit'});
                        }, function () {
                        $state.go('entrepot-produit');
                        });
                        }]
                })
                .state('entrepot-produit.edit', {
                parent: 'entrepot-produit',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/entrepot-produit/entrepot-produit-dialog.html',
                                controller: 'EntrepotProduitDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['EntrepotProduit', function (EntrepotProduit) {
                                return EntrepotProduit.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('entrepot-produit', null, {reload: 'entrepot-produit'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('entrepot-produit.delete', {
                parent: 'entrepot-produit',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/entrepot-produit/entrepot-produit-delete-dialog.html',
                                controller: 'EntrepotProduitDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['EntrepotProduit', function (EntrepotProduit) {
                                return EntrepotProduit.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('entrepot-produit', null, {reload: 'entrepot-produit'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
