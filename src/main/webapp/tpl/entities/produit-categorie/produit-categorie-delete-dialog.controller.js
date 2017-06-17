(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProduitCategorieDeleteController',ProduitCategorieDeleteController);

    ProduitCategorieDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProduitCategorie'];

    function ProduitCategorieDeleteController($uibModalInstance, entity, ProduitCategorie) {
        var vm = this;

        vm.produitCategorie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProduitCategorie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
