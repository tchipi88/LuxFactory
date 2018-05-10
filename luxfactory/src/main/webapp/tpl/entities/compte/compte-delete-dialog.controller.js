(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteDeleteController',CompteDeleteController);

    CompteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Compte'];

    function CompteDeleteController($uibModalInstance, entity, Compte) {
        var vm = this;

        vm.compte = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Compte.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
