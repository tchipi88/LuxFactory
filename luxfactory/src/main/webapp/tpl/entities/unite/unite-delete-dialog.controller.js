(function() {
    'use strict';

    angular
        .module('app')
        .controller('UniteDeleteController',UniteDeleteController);

    UniteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Unite'];

    function UniteDeleteController($uibModalInstance, entity, Unite) {
        var vm = this;

        vm.unite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Unite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
