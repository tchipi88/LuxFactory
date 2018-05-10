(function() {
    'use strict';

    angular
        .module('app')
        .controller('MarqueDeleteController',MarqueDeleteController);

    MarqueDeleteController.$inject = ['$uibModalInstance', 'entity', 'Marque'];

    function MarqueDeleteController($uibModalInstance, entity, Marque) {
        var vm = this;

        vm.marque = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Marque.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
