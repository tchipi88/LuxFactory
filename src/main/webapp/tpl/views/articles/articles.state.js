(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('articles', {
                parent: 'entity',
                        url: '/articles?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/views/articles/articles.html',
                                controller: 'ArticlesController',
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
               
               
                .state('articles.articles.new', {
                        parent: 'articles.articles',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/liste-articles/liste-articles-dialog.html',
                                controller: 'ListeArticlesDialogController',
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
                                $state.go('articles.articles', null, {reload: 'articles.articles'});
                                }, function () {
                                $state.go('articles.articles');
                                });
                        }]
                })
                .state('articles.articles.edit', {
                parent: 'articles.articles',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/liste-articles/liste-articles-dialog.html',
                                controller: 'ListeArticlesDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['ListeArticles', function (ListeArticles) {
                                return ListeArticles.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('articles.articles', null, {reload: 'articles.articles'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('articles.articles.delete', {
                parent: 'articles.articles',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/liste-articles/liste-articles-delete-dialog.html',
                                controller: 'ListeArticlesDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['ListeArticles', function (ListeArticles) {
                                return ListeArticles.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('articles.articles', null, {reload: 'articles.articles'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('articles.zoneStockage.new', {
                    parent: 'articles.zoneStockage',
                    url: '/entrepot_new',
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
                                $state.go('articles.zoneStockage', null, {reload: 'articles.zoneStockage'});
                            }, function () {
                                $state.go('articles.zoneStockage');
                            });
                        }]
                })
                .state('articles.zoneStockage.edit', {
                    parent: 'articles.zoneStockage',
                    url: '/{id}/entrepot_edit',
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
                                $state.go('articles.zoneStockage', null, {reload: 'articles.zoneStockage'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('articles.zoneStockage.delete', {
                    parent: 'articles.zoneStockage',
                    url: '/{id}/entrepot_delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot-produit/entrepot-delete-dialog.html',
                                controller: 'EntrepotProduitDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['EntrepotProduit', function (EntrepotProduit) {
                                            return EntrepotProduit.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('articles.zoneStockage', null, {reload: 'articles.zoneStockage'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('articles.activites.new', {
                parent: 'articles.activites',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/activites/activites-dialog.html',
                                controller: 'ActivitesDialogController',
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
                        $state.go('articles.activites', null, {reload: 'articles.activites'});
                        }, function () {
                        $state.go('articles.activites');
                        });
                        }]
                })
                .state('articles.activites.edit', {
                parent: 'articles.activites',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/activites/activites-dialog.html',
                                controller: 'ActivitesDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['Activites', function (Activites) {
                                return Activites.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('articles.activites', null, {reload: 'articles.activites'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('articles.activites.delete', {
                parent: 'articles.activites',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/activites/activites-delete-dialog.html',
                                controller: 'ActivitesDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['Activites', function (Activites) {
                                return Activites.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('articles.activites', null, {reload: 'articles.activites'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                //sous menu articles states
                .state('articles.articles',{
                        url: '/articles',
                        templateUrl: 'tpl/entities/liste-articles/liste-articles.html',
                        controller: 'ListeArticlesController',
                        controllerAs: 'vm'
                })
                .state('articles.zoneStockage',{
                        url: '/zoneStockage',
                        templateUrl: 'tpl/entities/entrepot-produit/entrepot-produits.html',
                        controller: 'EntrepotProduitController',
                        controllerAs: 'vm'
                })
                .state('articles.activites',{
                        url: '/activites',
                        templateUrl: 'tpl/entities/activites/activitess.html',
                        controller: 'ActivitesController',
                        controllerAs: 'vm'
                });
        }

})();
