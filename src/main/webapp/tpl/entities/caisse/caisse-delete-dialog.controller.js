(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseDeleteController',CaisseDeleteController);

    CaisseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Caisse'];

    function CaisseDeleteController($uibModalInstance, entity, Caisse) {
        var vm = this;

        vm.caisse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Caisse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
