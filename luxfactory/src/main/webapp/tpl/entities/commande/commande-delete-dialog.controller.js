(function() {
    'use strict';

    angular
        .module('app')
        .controller('CommandeDeleteController',CommandeDeleteController);

    CommandeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Commande'];

    function CommandeDeleteController($uibModalInstance, entity, Commande) {
        var vm = this;

        vm.commande = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Commande.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
