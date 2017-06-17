(function() {
    'use strict';

    angular
        .module('app')
        .controller('EncaissementDeleteController',EncaissementDeleteController);

    EncaissementDeleteController.$inject = ['$uibModalInstance', 'entity', 'Encaissement'];

    function EncaissementDeleteController($uibModalInstance, entity, Encaissement) {
        var vm = this;

        vm.encaissement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Encaissement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
