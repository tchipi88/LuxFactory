(function() {
    'use strict';

    angular
        .module('app')
        .controller('MouvementStockOutDeleteController',MouvementStockOutDeleteController);

    MouvementStockOutDeleteController.$inject = ['$uibModalInstance', 'entity', 'MouvementStockOut'];

    function MouvementStockOutDeleteController($uibModalInstance, entity, MouvementStockOut) {
        var vm = this;

        vm.mouvementStockOut = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MouvementStockOut.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
