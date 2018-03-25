(function() {
    'use strict';

    angular
        .module('app')
        .controller('ZonesMoreDeleteController',ZonesMoreDeleteController);

    ZonesMoreDeleteController.$inject = ['$uibModalInstance', 'entity', 'ZonesMore'];

    function ZonesMoreDeleteController($uibModalInstance, entity, ZonesMore) {
        var vm = this;

        vm.zonesMore = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ZonesMore.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
