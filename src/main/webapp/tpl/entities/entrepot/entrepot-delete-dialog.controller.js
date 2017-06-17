(function() {
    'use strict';

    angular
        .module('app')
        .controller('EntrepotDeleteController',EntrepotDeleteController);

    EntrepotDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entrepot'];

    function EntrepotDeleteController($uibModalInstance, entity, Entrepot) {
        var vm = this;

        vm.entrepot = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entrepot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
