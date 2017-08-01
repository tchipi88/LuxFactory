(function() {
    'use strict';

    angular
        .module('app')
        .controller('MouvementStockInDeleteController',MouvementStockInDeleteController);

    MouvementStockInDeleteController.$inject = ['$uibModalInstance', 'entity', 'MouvementStockIn'];

    function MouvementStockInDeleteController($uibModalInstance, entity, MouvementStockIn) {
        var vm = this;

        vm.mouvementStockIn = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MouvementStockIn.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
