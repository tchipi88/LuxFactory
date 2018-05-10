(function() {
    'use strict';

    angular
        .module('app')
        .controller('ZonesLessDeleteController',ZonesLessDeleteController);

    ZonesLessDeleteController.$inject = ['$uibModalInstance', 'entity', 'ZonesLess'];

    function ZonesLessDeleteController($uibModalInstance, entity, ZonesLess) {
        var vm = this;

        vm.zonesLess = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ZonesLess.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
