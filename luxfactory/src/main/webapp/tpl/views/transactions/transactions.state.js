(function () {
    'use strict';
    angular.module('app').config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('transactions', {
                    parent: 'entity',
                    url: '/transactions?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/views/transactions/transactions.html',
                            //controller: 'TransactionsController',
                            //controllerAs: 'vm'
                        }
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
                .state('transactions.commande',{
                        url: '/commande?type',
                        templateUrl: 'tpl/entities/commande/commandes.html',
                        controller: 'CommandeController',
                        controllerAs: 'vm',
                        params: {
                            type: {
                                value: 'VENTE',
                                squash: true
                            }
                        }
                })
                .state('transactions.facture',{
                        url: '/facture',
                        templateUrl: 'tpl/entities/facture/factures.html',
                        controller: 'FactureController',
                        controllerAs: 'vm'
                })
                .state('transactions.procesverbal',{
                        url: '/procesverbal',
                        templateUrl: 'tpl/entities/procesverbal/procesverbals.html',
                        controller: 'ProcesverbalController',
                        controllerAs: 'vm'
                })
                .state('transactions.devis',{
                        url: '/devis',
                        templateUrl: 'tpl/views/transactions/devis.html',
                        controller: 'CommandeController',
                        controllerAs: 'vm'
                })
                .state('transactions.comptes',{
                        url: '/comptes',
                        templateUrl: 'tpl/entities/compte-analytique/compte-analytiques.html',
                        controller: 'CompteAnalytiqueController',
                        controllerAs: 'vm'
                })
                .state('transactions.commande.new', {
                    parent: 'transactions.commande',
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
                                $state.go('transactions.commande.edit', {id: result.id}, null);
                            }, function () {
                                $state.go('transactions.commande');
                            });
                        }]
                })
                .state('transactions.commande.edit', {
                    parent: 'transactions.commande',
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
                                $state.go('transactions.commande', null, {reload: 'transactions.commande'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('transactions.commande.delete', {
                    parent: 'transactions.commande',
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
                                $state.go('transactions.commande', null, {reload: 'transactions.commande'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('transactions.facture.new', {
                        parent: 'transactions.facture',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/facture/facture-dialog.html',
                                controller: 'FactureDialogController',
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
                        $state.go('transactions.facture', null, {reload: 'transactions.facture'});
                        }, function () {
                        $state.go('transactions.facture');
                        });
                        }]
                })
                .state('transactions.procesverbal.new', {
                    parent: 'transactions.procesverbal',
                    url: '/new',
                    data: {
                    authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                    templateUrl: 'tpl/entities/procesverbal/procesverbal-dialog.html',
                            controller: 'ProcesverbalDialogController',
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
                    $state.go('transactions.procesverbal', null, {reload: 'transactions.procesverbal'});
                    }, function () {
                    $state.go('transactions.procesverbal');
                    });
                    }]
                })
                // .state('acteurs.fournisseurs',{
                //         url: '/fournisseurs',
                //         templateUrl: 'tpl/views/acteurs/acteurs-fournisseurs.html',
                //         controller: 'FournisseurController',
                //         controllerAs: 'vm'
                // })
                // .state('acteurs.clients.new', {
                //     parent: 'acteurs.clients',
                //     url: '/client_new',
                //     data: {
                //         authorities: ['ROLE_USER']
                //     },
                //     onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                //             $uibModal.open({
                //                 templateUrl: 'tpl/entities/client/client-dialog.html',
                //                 controller: 'ClientDialogController',
                //                 controllerAs: 'vm',
                //                 backdrop: 'static',
                //                 size: 'lg',
                //                 resolve: {
                //                     entity: function () {
                //                         return {

                //                         };
                //                     }
                //                 }
                //             }).result.then(function () {
                //                 $state.go('acteurs.clients', null, {reload: 'acteurs.clients'});
                //             }, function () {
                //                 $state.go('acteurs.clients');
                //             });
                //         }]
                // })
                // .state('acteurs.clients.edit', {
                //     parent: 'acteurs.clients',
                //     url: '/{id}/edit',
                //     data: {
                //         authorities: ['ROLE_USER']
                //     },
                //     onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                //             $uibModal.open({
                //                 templateUrl: 'tpl/entities/client/client-dialog.html',
                //                 controller: 'ClientDialogController',
                //                 controllerAs: 'vm',
                //                 backdrop: 'static',
                //                 size: 'lg',
                //                 resolve: {
                //                     entity: ['Client', function (Client) {
                //                             return Client.get({id: $stateParams.id}).$promise;
                //                         }]
                //                 }
                //             }).result.then(function () {
                //                 $state.go('acteurs.clients', null, {reload: 'acteurs.clients'});
                //             }, function () {
                //                 $state.go('^');
                //             });
                //         }]
                // })
                // .state('acteurs.clients.delete', {
                //     parent: 'acteurs.clients',
                //     url: '/{id}/delete',
                //     data: {
                //         authorities: ['ROLE_USER']
                //     },
                //     onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                //             $uibModal.open({
                //                 templateUrl: 'tpl/entities/client/client-delete-dialog.html',
                //                 controller: 'ClientDeleteController',
                //                 controllerAs: 'vm',
                //                 size: 'md',
                //                 resolve: {
                //                     entity: ['Client', function (Client) {
                //                             return Client.get({id: $stateParams.id}).$promise;
                //                         }]
                //                 }
                //             }).result.then(function () {
                //                 $state.go('acteurs.clients', null, {reload: 'acteurs.clients'});
                //             }, function () {
                //                 $state.go('^');
                //             });
                //         }]
                // })
                // .state('acteurs.fournisseurs.new', {
                //     parent: 'acteurs.fournisseurs',
                //     url: '/new',
                //     data: {
                //         authorities: ['ROLE_USER']
                //     },
                //     onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                //             $uibModal.open({
                //                 templateUrl: 'tpl/entities/fournisseur/fournisseur-dialog.html',
                //                 controller: 'FournisseurDialogController',
                //                 controllerAs: 'vm',
                //                 backdrop: 'static',
                //                 size: 'lg',
                //                 resolve: {
                //                     entity: function () {
                //                         return {

                //                         };
                //                     },
                //                     produitFournisseurs: function () {
                //                         return [];
                //                     }
                //                 }
                //             }).result.then(function () {
                //                 $state.go('acteurs.fournisseurs', null, {reload: 'acteurs.fournisseurs'});
                //             }, function () {
                //                 $state.go('acteurs.fournisseurs');
                //             });
                //         }]
                // })
                // .state('acteurs.fournisseurs.edit', {
                //     parent: 'acteurs.fournisseurs',
                //     url: '/{id}/edit',
                //     data: {
                //         authorities: ['ROLE_USER']
                //     },
                //     onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                //             $uibModal.open({
                //                 templateUrl: 'tpl/entities/fournisseur/fournisseur-dialog.html',
                //                 controller: 'FournisseurDialogController',
                //                 controllerAs: 'vm',
                //                 backdrop: 'static',
                //                 size: 'lg',
                //                 resolve: {
                //                     entity: ['Fournisseur', function (Fournisseur) {
                //                             return Fournisseur.get({id: $stateParams.id}).$promise;
                //                         }]
                //                     //     ,
                //                     // produitFournisseurs: ['$resource', function ($resource) {
                //                     //         return  $resource('api/produit-fournisseurss/' + $stateParams.id).query();
                //                     //     }]
                //                 }
                //             }).result.then(function () {
                //                 $state.go('acteurs.fournisseurs', null, {reload: 'acteurs.fournisseurs'});
                //             }, function () {
                //                 $state.go('^');
                //             });
                //         }]
                // })
                // .state('acteurs.fournisseurs.delete', {
                //     parent: 'acteurs.fournisseurs',
                //     url: '/{id}/delete',
                //     data: {
                //         authorities: ['ROLE_USER']
                //     },
                //     onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                //             $uibModal.open({
                //                 templateUrl: 'tpl/entities/fournisseur/fournisseur-delete-dialog.html',
                //                 controller: 'FournisseurDeleteController',
                //                 controllerAs: 'vm',
                //                 size: 'md',
                //                 resolve: {
                //                     entity: ['Fournisseur', function (Fournisseur) {
                //                             return Fournisseur.get({id: $stateParams.id}).$promise;
                //                         }]
                //                 }
                //             }).result.then(function () {
                //                 $state.go('acteurs.fournisseurs', null, {reload: 'acteurs.fournisseurs'});
                //             }, function () {
                //                 $state.go('^');
                //             });
                //         }]
                // })
                ;
    }

})();
