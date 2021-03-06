(function () {
    'use strict';

    angular
            .module('app')
            .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$state', '$timeout', 'Auth'];

    function LoginController($rootScope, $state, $timeout, Auth) {
        var vm = this;

        vm.authenticationError = false;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;

        $timeout(function () {
            angular.element('#username').focus();
        });


        function login() {
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;

                $state.go('home');

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function () {
                vm.authenticationError = true;
            });
        }



        function requestResetPassword() {
            $uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }
    }
})();
