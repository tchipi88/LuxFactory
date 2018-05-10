(function() {
    'use strict';

    angular
        .module('app')
        .controller('ZoneProduitDeleteController',ZoneProduitDeleteController);

    ZoneProduitDeleteController.$inject = ['$uibModalInstance', 'entity', 'ZoneProduit'];

    function ZoneProduitDeleteController($uibModalInstance, entity, ZoneProduit) {
        var vm = this;

        vm.zoneProduit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ZoneProduit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
