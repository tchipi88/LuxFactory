(function() {
    'use strict';

    angular
        .module('app')
        .controller('ReleveEnginDeleteController',ReleveEnginDeleteController);

    ReleveEnginDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReleveEngin'];

    function ReleveEnginDeleteController($uibModalInstance, entity, ReleveEngin) {
        var vm = this;

        vm.releveEngin = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReleveEngin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
