(function() {
    'use strict';

    angular
        .module('app')
        .controller('ReglementDeleteController',ReglementDeleteController);

    ReglementDeleteController.$inject = ['$uibModalInstance', 'entity', 'Reglement'];

    function ReglementDeleteController($uibModalInstance, entity, Reglement) {
        var vm = this;

        vm.reglement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Reglement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
