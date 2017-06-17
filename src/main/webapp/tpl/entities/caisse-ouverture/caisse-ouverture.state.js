(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('caisse-ouverture', {
                parent: 'entity',
                        url: '/caisse-ouverture?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/caisse-ouverture/caisse-ouvertures.html',
                                controller: 'CaisseOuvertureController',
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
                .state('caisse-ouverture-detail', {
                parent: 'caisse-ouverture',
                        url: '/caisse-ouverture/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/caisse-ouverture/caisse-ouverture-detail.html',
                                controller: 'CaisseOuvertureDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'CaisseOuverture', function ($stateParams, CaisseOuverture) {
                        return CaisseOuverture.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'caisse-ouverture',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('caisse-ouverture-detail.edit', {
                parent: 'caisse-ouverture-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-ouverture/caisse-ouverture-dialog.html',
                                controller: 'CaisseOuvertureDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CaisseOuverture', function (CaisseOuverture) {
                                return CaisseOuverture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('caisse-ouverture.new', {
                parent: 'caisse-ouverture',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-ouverture/caisse-ouverture-dialog.html',
                                controller: 'CaisseOuvertureDialogController',
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
                        $state.go('caisse-ouverture', null, {reload: 'caisse-ouverture'});
                        }, function () {
                        $state.go('caisse-ouverture');
                        });
                        }]
                })
                .state('caisse-ouverture.edit', {
                parent: 'caisse-ouverture',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-ouverture/caisse-ouverture-dialog.html',
                                controller: 'CaisseOuvertureDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CaisseOuverture', function (CaisseOuverture) {
                                return CaisseOuverture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-ouverture', null, {reload: 'caisse-ouverture'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('caisse-ouverture.delete', {
                parent: 'caisse-ouverture',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-ouverture/caisse-ouverture-delete-dialog.html',
                                controller: 'CaisseOuvertureDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CaisseOuverture', function (CaisseOuverture) {
                                return CaisseOuverture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-ouverture', null, {reload: 'caisse-ouverture'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
