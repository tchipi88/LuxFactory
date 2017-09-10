(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProcesverbalDeleteController',ProcesverbalDeleteController);

    ProcesverbalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Procesverbal'];

    function ProcesverbalDeleteController($uibModalInstance, entity, Procesverbal) {
        var vm = this;

        vm.procesverbal = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Procesverbal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
