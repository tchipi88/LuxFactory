(function() {
    'use strict';

    angular
        .module('app')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['Principal', 'Auth'];

    function SettingsController (Principal, Auth) {
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                prenom: account.firstName,
                nom: account.lastName,
                login: account.login,
                id:account.id,
                
                fonction:account.fonction,
                departement:account.departement,
                salaire:account.salaire,
                adresse:account.adresse,
                telephone:account.telephone,
                dateNaissance:account.dateNaissance,
                lieuNaissance:account.lieuNaissance,
            };
        };

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
        });

        function save () {
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    vm.settingsAccount = copyAccount(account);
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }
    }
})();
