(function() {
    'use strict';

    angular
        .module('app')
        .controller('EntrepotProduitDeleteController',EntrepotProduitDeleteController);

    EntrepotProduitDeleteController.$inject = ['$uibModalInstance', 'entity', 'EntrepotProduit'];

    function EntrepotProduitDeleteController($uibModalInstance, entity, EntrepotProduit) {
        var vm = this;

        vm.entrepotProduit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EntrepotProduit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
