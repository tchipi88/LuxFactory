(function() {
    'use strict';

    angular
        .module('app')
        .controller('StatutZonesProduitDeleteController',StatutZonesProduitDeleteController);

    StatutZonesProduitDeleteController.$inject = ['$uibModalInstance', 'entity', 'StatutZonesProduit'];

    function StatutZonesProduitDeleteController($uibModalInstance, entity, StatutZonesProduit) {
        var vm = this;

        vm.statutZonesProduit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StatutZonesProduit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
