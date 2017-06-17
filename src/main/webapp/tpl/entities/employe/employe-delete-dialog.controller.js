(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeDeleteController',EmployeDeleteController);

    EmployeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Employe'];

    function EmployeDeleteController($uibModalInstance, entity, Employe) {
        var vm = this;

        vm.employe = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Employe.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
