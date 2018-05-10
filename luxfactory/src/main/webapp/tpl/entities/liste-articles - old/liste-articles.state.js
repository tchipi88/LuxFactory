(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('liste-articles', {
                parent: 'entity',
                        url: '/liste-articles?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/liste-articles/liste-articles.html',
                                controller: 'ListeArticlesController',
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
               
               
                .state('liste-articles.articles.new', {
                        parent: 'liste-articles.articles',
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
                                $state.go('liste-articles.articles', null, {reload: 'liste-articles.articles'});
                                }, function () {
                                $state.go('liste-articles.articles');
                                });
                        }]
                })
                .state('liste-articles.articles.edit', {
                parent: 'liste-articles.articles',
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
                        $state.go('liste-articles', null, {reload: 'liste-articles'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('liste-articles.articles.delete', {
                parent: 'liste-articles.articles',
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
                        $state.go('liste-articles', null, {reload: 'liste-articles'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('liste-articles.entrepot_new', {
                    parent: 'liste-articles',
                    url: '/entrepot_new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot/entrepot-dialog.html',
                                controller: 'EntrepotDialogController',
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
                                $state.go('liste-articles.zoneStockage', null, {reload: 'liste-articles.zoneStockage'});
                            }, function () {
                                $state.go('liste-articles.zoneStockage');
                            });
                        }]
                })
                .state('liste-articles.entrepot_edit', {
                    parent: 'liste-articles',
                    url: '/{id}/entrepot_edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot/entrepot-dialog.html',
                                controller: 'EntrepotDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Entrepot', function (Entrepot) {
                                            return Entrepot.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('liste-articles.zoneStockage', null, {reload: 'liste-articles.zoneStockage'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('liste-articles.entrepot_delete', {
                    parent: 'liste-articles',
                    url: '/{id}/entrepot_delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/entrepot/entrepot-delete-dialog.html',
                                controller: 'EntrepotDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Entrepot', function (Entrepot) {
                                            return Entrepot.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('liste-articles.zoneStockage', null, {reload: 'liste-articles.zoneStockage'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
        }

})();
