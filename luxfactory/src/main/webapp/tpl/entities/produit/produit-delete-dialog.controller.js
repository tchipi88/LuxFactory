(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProduitDeleteController',ProduitDeleteController);

    ProduitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Produit'];

    function ProduitDeleteController($uibModalInstance, entity, Produit) {
        var vm = this;

        vm.produit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Produit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
