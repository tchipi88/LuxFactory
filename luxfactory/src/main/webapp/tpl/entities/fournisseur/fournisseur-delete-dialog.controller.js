(function() {
    'use strict';

    angular
        .module('app')
        .controller('FournisseurDeleteController',FournisseurDeleteController);

    FournisseurDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fournisseur'];

    function FournisseurDeleteController($uibModalInstance, entity, Fournisseur) {
        var vm = this;

        vm.fournisseur = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Fournisseur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
