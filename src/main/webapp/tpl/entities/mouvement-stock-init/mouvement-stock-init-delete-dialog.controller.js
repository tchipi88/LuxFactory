(function() {
    'use strict';

    angular
        .module('app')
        .controller('MouvementStockInitDeleteController',MouvementStockInitDeleteController);

    MouvementStockInitDeleteController.$inject = ['$uibModalInstance', 'entity', 'MouvementStockInit'];

    function MouvementStockInitDeleteController($uibModalInstance, entity, MouvementStockInit) {
        var vm = this;

        vm.mouvementStockInit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MouvementStockInit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
