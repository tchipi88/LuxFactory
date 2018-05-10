(function() {
    'use strict';

    angular
        .module('app')
        .controller('MouvementStockDeleteController',MouvementStockDeleteController);

    MouvementStockDeleteController.$inject = ['$uibModalInstance', 'entity', 'MouvementStock'];

    function MouvementStockDeleteController($uibModalInstance, entity, MouvementStock) {
        var vm = this;

        vm.mouvementStock = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MouvementStock.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
