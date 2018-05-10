(function() {
    'use strict';

    angular
        .module('app')
        .controller('DecompteZonesProduitsDeleteController',DecompteZonesProduitsDeleteController);

    DecompteZonesProduitsDeleteController.$inject = ['$uibModalInstance', 'entity', 'DecompteZonesProduits'];

    function DecompteZonesProduitsDeleteController($uibModalInstance, entity, DecompteZonesProduits) {
        var vm = this;

        vm.decompteZonesProduits = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DecompteZonesProduits.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
