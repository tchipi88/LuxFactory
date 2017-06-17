'use strict';

/**
 * Config for the router
 */
angular.module('app')
        .run(
                ['$rootScope', '$state', '$stateParams',
                    function ($rootScope, $state, $stateParams) {
                        $rootScope.$state = $state;
                        $rootScope.$stateParams = $stateParams;
                    }
                ]
                )
        .config(
                ['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG',
                    function ($stateProvider, $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG) {
                        var layout = "tpl/app.html";

                        $urlRouterProvider
                                .otherwise('/page/404');

                        $stateProvider
                                .state('app', {
                                    abstract: true,
                                    templateUrl: layout,
                                    data: {
                                        authorities: []
                                    },
                                    controller: 'NavbarController',
                                    controllerAs: 'vm',
                                    resolve: {
                                        deps: ['$ocLazyLoad',
                                            function ($ocLazyLoad) {
                                                return $ocLazyLoad.load(['toaster', 'ui.select']);
                                            }],
                                        authorize: ['Auth',
                                            function (Auth) {
                                                return Auth.authorize();
                                            }
                                        ]

                                    }
                                })

                                .state('home', {
                                    parent: 'app',
                                    url: '/',
                                    views: {
                                        'content@app': {
                                            templateUrl: 'tpl/home.html'}}
                                })
                                .state('page', {
                                    url: '/page',
                                    template: '<div ui-view class="fade-in-right-big smooth"></div>'
                                })
                                .state('page.404', {
                                    url: '/404',
                                    templateUrl: 'tpl/error/page_404.html'
                                })
                                .state('page.login', {
                                    url: '/login',
                                    templateUrl: 'tpl/page_signin.html',
                                    controller: 'LoginController',
                                    controllerAs: 'vm'
                                })
                                ;

                    }
                ]
                );
