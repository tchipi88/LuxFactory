(function() {
    'use strict';

    angular
        .module('app')
        .controller('EnginDeleteController',EnginDeleteController);

    EnginDeleteController.$inject = ['$uibModalInstance', 'entity', 'Engin'];

    function EnginDeleteController($uibModalInstance, entity, Engin) {
        var vm = this;

        vm.engin = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Engin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
