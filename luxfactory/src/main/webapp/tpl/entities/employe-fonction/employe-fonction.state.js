(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('employe-fonction', {
                parent: 'entity',
                        url: '/employe-fonction?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/employe-fonction/employe-fonctions.html',
                                controller: 'EmployeFonctionController',
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
               
               
                .state('employe-fonction.new', {
                parent: 'employe-fonction',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/employe-fonction/employe-fonction-dialog.html',
                                controller: 'EmployeFonctionDialogController',
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
                        $state.go('employe-fonction', null, {reload: 'employe-fonction'});
                        }, function () {
                        $state.go('employe-fonction');
                        });
                        }]
                })
                .state('employe-fonction.edit', {
                parent: 'employe-fonction',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/employe-fonction/employe-fonction-dialog.html',
                                controller: 'EmployeFonctionDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['EmployeFonction', function (EmployeFonction) {
                                return EmployeFonction.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('employe-fonction', null, {reload: 'employe-fonction'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('employe-fonction.delete', {
                parent: 'employe-fonction',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/employe-fonction/employe-fonction-delete-dialog.html',
                                controller: 'EmployeFonctionDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['EmployeFonction', function (EmployeFonction) {
                                return EmployeFonction.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('employe-fonction', null, {reload: 'employe-fonction'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
