(function () {
    'use strict';
    angular.module('app').config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('acteurs', {
                    parent: 'entity',
                    url: '/acteurs?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/views/acteurs/acteurs.html',
                            controller: 'ActeurController',
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
                        locataire: {
                            value: false,
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
                //sous menu acteurs states
                .state('acteurs.clients',{
                        url: '/clients',
                        templateUrl: 'tpl/views/acteurs/acteurs-clients.html',
                        controller: 'ClientController',
                        controllerAs: 'vm'
                })
                .state('acteurs.fournisseurs',{
                        url: '/fournisseurs',
                        templateUrl: 'tpl/views/acteurs/acteurs-fournisseurs.html',
                        controller: 'FournisseurController',
                        controllerAs: 'vm'
                })
                .state('acteurs.clients.new', {
                    parent: 'acteurs.clients',
                    url: '/client_new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/client/client-dialog.html',
                                controller: 'ClientDialogController',
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
                                $state.go('acteurs.clients', null, {reload: 'acteurs.clients'});
                            }, function () {
                                $state.go('acteurs.clients');
                            });
                        }]
                })
                .state('acteurs.clients.edit', {
                    parent: 'acteurs.clients',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/client/client-dialog.html',
                                controller: 'ClientDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Client', function (Client) {
                                            return Client.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('acteurs.clients', null, {reload: 'acteurs.clients'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('acteurs.clients.delete', {
                    parent: 'acteurs.clients',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/client/client-delete-dialog.html',
                                controller: 'ClientDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Client', function (Client) {
                                            return Client.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('acteurs.clients', null, {reload: 'acteurs.clients'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('acteurs.fournisseurs.new', {
                    parent: 'acteurs.fournisseurs',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/fournisseur/fournisseur-dialog.html',
                                controller: 'FournisseurDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {

                                        };
                                    },
                                    produitFournisseurs: function () {
                                        return [];
                                    }
                                }
                            }).result.then(function () {
                                $state.go('acteurs.fournisseurs', null, {reload: 'acteurs.fournisseurs'});
                            }, function () {
                                $state.go('acteurs.fournisseurs');
                            });
                        }]
                })
                .state('acteurs.fournisseurs.edit', {
                    parent: 'acteurs.fournisseurs',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/fournisseur/fournisseur-dialog.html',
                                controller: 'FournisseurDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Fournisseur', function (Fournisseur) {
                                            return Fournisseur.get({id: $stateParams.id}).$promise;
                                        }]
                                    //     ,
                                    // produitFournisseurs: ['$resource', function ($resource) {
                                    //         return  $resource('api/produit-fournisseurss/' + $stateParams.id).query();
                                    //     }]
                                }
                            }).result.then(function () {
                                $state.go('acteurs.fournisseurs', null, {reload: 'acteurs.fournisseurs'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('acteurs.fournisseurs.delete', {
                    parent: 'acteurs.fournisseurs',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/fournisseur/fournisseur-delete-dialog.html',
                                controller: 'FournisseurDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Fournisseur', function (Fournisseur) {
                                            return Fournisseur.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('acteurs.fournisseurs', null, {reload: 'acteurs.fournisseurs'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
