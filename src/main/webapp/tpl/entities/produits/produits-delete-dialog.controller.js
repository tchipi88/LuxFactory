(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProduitsDeleteController',ProduitsDeleteController);

    ProduitsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Produits'];

    function ProduitsDeleteController($uibModalInstance, entity, Produits) {
        var vm = this;

        vm.produits = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Produits.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
