(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeFonctionDeleteController',EmployeFonctionDeleteController);

    EmployeFonctionDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmployeFonction'];

    function EmployeFonctionDeleteController($uibModalInstance, entity, EmployeFonction) {
        var vm = this;

        vm.employeFonction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmployeFonction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
